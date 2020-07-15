package com.ioc.datasupport.warehouse.service;

import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.warehouse.domain.DlRescataColumn;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源目录结构表 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
public interface DlRescataColumnService extends IService<DlRescataColumn> {

    List<ColumnInfo> getUserColumns(Long tableId);

}
