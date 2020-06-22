package com.ioc.datasupport.warehouse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.common.MbpTablePageImpl;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
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
     */
    List<TableInfo> getTableList(Long dbId) throws Exception;

    /**
     * 获取物理表（支持分页）
     *
     * @param dbId 数据库ID
     * @param page 分页
     */
    MbpTablePageImpl<TableInfo> getTablePage(Long dbId, Page<TableInfo> page) throws Exception;

}
