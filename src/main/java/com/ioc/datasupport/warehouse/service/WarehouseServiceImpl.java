package com.ioc.datasupport.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.common.MbpTablePageImpl;
import com.ioc.datasupport.component.DataDictionaryComponent;
import com.ioc.datasupport.component.UserComponent;
import com.ioc.datasupport.datalake.service.DlRescataColumnService;
import com.ioc.datasupport.datalake.service.DlRescataDatabaseService;
import com.ioc.datasupport.datalake.service.DlRescataResourceService;
import com.ioc.datasupport.datalake.service.DlRescataStrucPermiService;
import com.ioc.datasupport.dataprovider.DataProvider;
import com.ioc.datasupport.dataprovider.DataProviderManager;
import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.dataprovider.dto.DatasourceInfo;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.dataprovider.result.*;
import com.ioc.datasupport.util.ValidateUtil;
import com.ioc.datasupport.datalake.domain.DlRescataColumn;
import com.ioc.datasupport.datalake.domain.DlRescataDatabase;
import com.ioc.datasupport.datalake.domain.DlRescataResource;
import com.ioc.datasupport.datalake.domain.DlRescataStrucPermi;
import com.ioc.datasupport.warehouse.dto.ColumnPermInfo;
import com.ioc.datasupport.warehouse.dto.QueryDataDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        ValidateUtil.notEmpty(userColumns, "列信息为空");
        List<Long> structureIds = userColumns.stream().map(DlRescataColumn::getStructureId).collect(Collectors.toList());

        // 获取该用户对应列信息的权限
        List<DlRescataStrucPermi> permis = dlRescataStrucPermiService.findByStructureIdInAndOwnerAccount(structureIds, userComponent.getUserInfo().getUserAccount());
        Map<Long, DlRescataStrucPermi> permiMap = permis.stream().collect(Collectors.toMap(DlRescataStrucPermi::getStructureId, item -> item));

        // 组合成新的实体，返回
        List<ColumnPermInfo> columnPermInfos = userColumns.stream().map(ColumnPermInfo::new).collect(Collectors.toList());
        for (ColumnPermInfo columnPermInfo : columnPermInfos) {
            DlRescataStrucPermi permi = permiMap.get(columnPermInfo.getColumnId());
            columnPermInfo.setIsDecryption(permi.getIsDecryption());
            columnPermInfo.setIsSensitived(permi.getIsSensitived());

            // 设置字段类型
            String columnType = dataDictionaryComponent.getSysCodeVal(DataDictionaryComponent.DL_COLUMN_DATA_TYPE, String.valueOf(columnPermInfo.getColumnTypeCode()));
            columnPermInfo.setColumnType(columnType);
        }

        // 获取脱敏规则


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

        // 对数据加密脱敏
        List<ColumnPermInfo> userTableColumn = this.getUserTableColumn(queryDataDTO.getTableId());

        return result;
    }

    /**
     * 处理过滤及脱敏
     *
     * @param result
     * @param userTableColumn
     */
    private void handlePermiData(JdbcTemplateAggResult result, List<ColumnPermInfo> userTableColumn) {
        if (result == null || CollectionUtils.isEmpty(result.getData())) {
            return;
        }

        Map<String, ColumnPermInfo> permInfoMap = userTableColumn.stream().collect(Collectors.toMap(ColumnPermInfo::getColumnSource, item -> item));
        for (Map<String, Object> lineMap : result.getData()) {
            for (Map.Entry<String, Object> entry : lineMap.entrySet()) {
                String col = entry.getKey();
                Object val = entry.getValue();
                ColumnPermInfo permInfo = permInfoMap.get(col);
                if (permInfo == null) {
                    continue;
                }

                Integer isDecryption = permInfo.getIsDecryption();
                Integer isSensitived = permInfo.getIsSensitived();
            }
        }
    }
}
