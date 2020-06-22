package com.ioc.datasupport.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.common.MbpTablePageImpl;
import com.ioc.datasupport.dataprovider.DataProvider;
import com.ioc.datasupport.dataprovider.DataProviderManager;
import com.ioc.datasupport.dataprovider.dto.DatasourceInfo;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.util.ValidateUtil;
import com.ioc.datasupport.warehouse.domain.DlRescataDatabase;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
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
    private DlRescataDatabaseService dlRescataDatabaseService;

    @Resource
    private WarehouseService warehouseService;

    @Override
    @Cacheable(key = "#dbId")
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
}
