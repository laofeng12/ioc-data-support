package com.ioc.datasupport.warehouse.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.common.MbpTablePageImpl;
import com.ioc.datasupport.common.PublicConstant;
import com.ioc.datasupport.component.DataDictionaryComponent;
import com.ioc.datasupport.component.UserComponent;
import com.ioc.datasupport.datalake.domain.*;
import com.ioc.datasupport.datalake.service.*;
import com.ioc.datasupport.dataprovider.DataProvider;
import com.ioc.datasupport.dataprovider.DataProviderManager;
import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.dataprovider.dto.DatasourceInfo;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.dataprovider.result.*;
import com.ioc.datasupport.util.AesUtil;
import com.ioc.datasupport.util.InsensitivesUtil;
import com.ioc.datasupport.util.ValidateUtil;
import com.ioc.datasupport.warehouse.dto.ColumnPermInfo;
import com.ioc.datasupport.warehouse.dto.ColumnPermUseInfo;
import com.ioc.datasupport.warehouse.dto.QueryDataDTO;
import org.checkerframework.checker.units.qual.C;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: lsw
 * @Date: 2020/6/19 10:15
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "caffeineCacheManager")
public class WarehouseServiceImpl implements WarehouseService {

    @Resource
    private UserComponent userComponent;

    @Resource
    private DlRescataDatabaseService dlRescataDatabaseService;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private DlRescataResourceService dlRescataResourceService;

    @Resource
    private DlRescataColumnService dlRescataColumnService;

    @Resource
    private DlRescataStrucPermiService dlRescataStrucPermiService;

    @Resource
    private DataDictionaryComponent dataDictionaryComponent;

    @Resource
    private DlInsensitivesRuleService dlInsensitivesRuleService;

    @Override
    @Cacheable(key = "#dbId", sync = true)
    public List<TableInfo> getTableList(@NotNull Long dbId) throws Exception {
        DlRescataDatabase dbInfo = dlRescataDatabaseService.getRescataDataBaseById(dbId);
        ValidateUtil.notNull(dbInfo, "获取仓库信息失败");
        DataProvider dataProvider = DataProviderManager.getDataProvider(new DatasourceInfo(dbInfo));
        return dataProvider.getTableList("");
    }

    @Override
    public MbpTablePageImpl<TableInfo> getTablePage(@NotNull Long dbId, Page<TableInfo> page) throws Exception {
        List<TableInfo> tableList = warehouseService.getTableList(dbId);
        int total = tableList.size();

        tableList = tableList.stream()
                .skip(page.getSize() * (page.getCurrent()))
                .limit(page.getSize()).collect(Collectors.toList());

        page.setRecords(tableList);
        page.setTotal(total);

        return new MbpTablePageImpl<>(page);
    }

    @Override
    public List<ColumnInfo> getTableColumn(Long dbId, String tableName) throws Exception {
        DlRescataDatabase dbInfo = dlRescataDatabaseService.getRescataDataBaseById(dbId);
        ValidateUtil.notNull(dbInfo, "获取仓库信息失败");
        DataProvider dataProvider = DataProviderManager.getDataProvider(new DatasourceInfo(dbInfo));

        return dataProvider.getColumnList(tableName);
    }

    @Override
    public AggregatePageResult getTableData(Long dbId, String tableName, Page page) throws Exception {
        DlRescataDatabase dbInfo = dlRescataDatabaseService.getRescataDataBaseById(dbId);
        ValidateUtil.notNull(dbInfo, "获取仓库信息失败");
        DataProvider dataProvider = DataProviderManager.getDataProvider(new DatasourceInfo(dbInfo));

        AggregateResult aggregateResult = dataProvider.queryTableData(Collections.emptyList(), tableName, "", page);
        return new AggregatePageResult(aggregateResult, page);
    }

    @Override
    public List<TableInfo> getUserTableList(Long dbId) throws Exception {
        List<DlRescataResource> userResource = dlRescataResourceService.getUserResource(dbId);
        if (CollectionUtils.isEmpty(userResource)) {
            return Collections.emptyList();
        }

        return userResource.stream().map(TableInfo::new).collect(Collectors.toList());
    }

    @Override
    public List<ColumnPermInfo> getUserTableColumn(Long tableId) throws Exception {
        // 获取该表的列信息
        List<DlRescataColumn> userColumns = dlRescataColumnService.getUserColumns(tableId);
        if (CollectionUtils.isEmpty(userColumns)) {
            return Collections.emptyList();
        }

        List<Long> structureIds = userColumns.stream().map(DlRescataColumn::getStructureId).collect(Collectors.toList());
        // 获取该用户对应列信息的权限
        List<DlRescataStrucPermi> permis = dlRescataStrucPermiService.findByStructureIdInAndOwnerAccount(structureIds, userComponent.getUserInfo().getUserAccount());
        Map<Long, DlRescataStrucPermi> permiMap = permis.stream().collect(Collectors.toMap(DlRescataStrucPermi::getStructureId, item -> item));

        // 组合成新的实体，返回
        List<ColumnPermInfo> columnPermInfos = userColumns.stream().map(ColumnPermInfo::new).collect(Collectors.toList());
        for (ColumnPermInfo columnPermInfo : columnPermInfos) {
            // 设置字段类型
            String columnType = dataDictionaryComponent.getSysCodeVal(DataDictionaryComponent.DL_COLUMN_DATA_TYPE, String.valueOf(columnPermInfo.getColumnTypeCode()));
            columnPermInfo.setColumnType(columnType);

            DlRescataStrucPermi permi = permiMap.get(columnPermInfo.getColumnId());
            if (permi == null) {
                // 权限表里没查到记录，则用户无该字段的权限，隐藏
                columnPermInfo.setShowType(PublicConstant.DL_COLUMN_SHOW_TYPE_EMPTY);
                continue;
            }

            if (permi.getIsDecryption() == PublicConstant.YES.intValue()
                    && permi.getIsSensitived() == PublicConstant.YES.intValue()) {
                // 不用加密、不用脱敏，则是明文
                columnPermInfo.setShowType(PublicConstant.DL_COLUMN_SHOW_TYPE_PUBLIC);
                continue;
            }

            if (permi.getIsDecryption() == PublicConstant.YES.intValue()) {
                // 单纯解密，则是脱敏
                columnPermInfo.setShowType(PublicConstant.DL_COLUMN_SHOW_TYPE_DESEN);
                continue;
            }

            // 加密
            columnPermInfo.setShowType(PublicConstant.DL_COLUMN_SHOW_TYPE_ENCRY);
        }

        return columnPermInfos;
    }

    @Override
    public JdbcTemplateAggResult queryData(QueryDataDTO queryDataDTO) throws Exception {
        DlRescataDatabase dbInfo = dlRescataDatabaseService.getRescataDataBaseById(queryDataDTO.getDbId());
        ValidateUtil.notNull(dbInfo, "获取仓库信息失败");
        DataProvider dataProvider = DataProviderManager.getDataProvider(new DatasourceInfo(dbInfo));

        // 查询数据
        JdbcTemplateAggResult result = dataProvider.queryBySql(queryDataDTO.getSql());

        // 设置列信息
        List<ColumnInfo> columnList = dataProvider.getColumnList(queryDataDTO.getTableName());
        List<ColumnIndex> columnIndex = columnList.stream().map(ColumnIndex::new).collect(Collectors.toList());
        result.setColumnList(columnIndex);

        // 获取字段权限
        List<ColumnPermInfo> userTableColumn = this.getUserTableColumn(queryDataDTO.getTableId());
        // 获取字段权限-带脱敏规则
        List<ColumnPermUseInfo> columnPermUseInfo = this.getColumnPermUseInfo(userTableColumn);

        // 对数据加密脱敏置空等操作
        handlePermiData(result, columnPermUseInfo);

        return result;
    }

    /*-----------------------辅助函数-----------------------*/

    /**
     * 获取具体要用的权限信息
     *
     * @param columnPermInfos
     * @return
     */
    private List<ColumnPermUseInfo> getColumnPermUseInfo(List<ColumnPermInfo> columnPermInfos) {
        // 获取脱敏规则
        List<Long> inseRuleIds = columnPermInfos.stream().map(ColumnPermInfo::getInsensitivesRuleId).collect(Collectors.toList());
        List<DlInsensitivesRule> inseRules = dlInsensitivesRuleService.findByInsensitivesRuleIds(inseRuleIds);
        Map<Long, DlInsensitivesRule> inseRuleMap = inseRules.stream().collect(Collectors.toMap(DlInsensitivesRule::getInsensitivesRuleId, item -> item));

        List<ColumnPermUseInfo> useInfos = new LinkedList<>();
        for (ColumnPermInfo permInfo : columnPermInfos) {
            ColumnPermUseInfo usePermInfo = new ColumnPermUseInfo();
            // 复制已有属性
            BeanUtil.copyProperties(permInfo, usePermInfo);
            useInfos.add(usePermInfo);

            // 设置脱敏规则
            DlInsensitivesRule dlInseRule = inseRuleMap.get(permInfo.getInsensitivesRuleId());
            usePermInfo.setInsensitivesRule(dlInseRule);
        }

        return useInfos;
    }

    /**
     * 处理过滤及脱敏
     *
     * @param result
     * @param columnPermUseInfo
     */
    private void handlePermiData(JdbcTemplateAggResult result, List<ColumnPermUseInfo> columnPermUseInfo) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            return;
        }

        Map<String, ColumnPermUseInfo> permInfoMap = columnPermUseInfo.stream().collect(Collectors.toMap(ColumnPermUseInfo::getColumnSource, item -> item));
        for (Map<String, Object> lineMap : result.getData()) {
            for (Map.Entry<String, Object> entry : lineMap.entrySet()) {
                String col = entry.getKey();
                Object val = entry.getValue();
                ColumnPermUseInfo permInfo = permInfoMap.get(col);
                if (permInfo == null) {
                    continue;
                }

                if (PublicConstant.DL_COLUMN_SHOW_TYPE_PUBLIC.equals(permInfo.getShowType())) {
                    // 明文
                    continue;
                }

                if (PublicConstant.DL_COLUMN_SHOW_TYPE_EMPTY.equals(permInfo.getShowType())) {
                    // 空
                    entry.setValue(PublicConstant.DL_COLUMN_EMPTY_VAL);
                    continue;
                }

                if (PublicConstant.DL_COLUMN_SHOW_TYPE_ENCRY.equals(permInfo.getShowType())) {
                    // 加密
                    entry.setValue(AesUtil.encrypt(String.valueOf(val)));
                    continue;
                }

                // 脱敏 TODO
                String insensitivesStartEnd = permInfo.getInsensitivesStartEnd();
                DlInsensitivesRule insensitivesRule = permInfo.getInsensitivesRule();
                Object valObj = InsensitivesUtil.insensitiveColumn(insensitivesRule, String.valueOf(val));
                entry.setValue(valObj);
            }
        }
    }
}
