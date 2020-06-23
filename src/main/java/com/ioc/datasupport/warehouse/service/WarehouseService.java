package com.ioc.datasupport.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.common.MbpTablePageImpl;
import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.dataprovider.result.AggregatePageResult;
import com.ioc.datasupport.warehouse.domain.DlRescataDatabase;

import java.util.List;

/**
 * @author: lsw
 * @Date: 2020/6/19 10:14
 */
public interface WarehouseService {

    /**
     * 获取物理表集合
     *
     * @param dbId 数据库ID
     * @return     表集合
     * @throws Exception 异常
     */
    List<TableInfo> getTableList(Long dbId) throws Exception;

    /**
     * 获取物理表（支持分页）
     *
     * @param dbId 数据库ID
     * @param page 分页
     * @return     分页结果
     * @throws Exception 异常
     */
    MbpTablePageImpl<TableInfo> getTablePage(Long dbId, Page<TableInfo> page) throws Exception;

    List<ColumnInfo> getTableColumn(Long dbId, String tableName) throws Exception;

    AggregatePageResult getTableData(Long dbId, String tableName, Page page) throws Exception;
}
