package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.datalake.domain.DlRescataColumn;
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

    List<DlRescataColumn> getUserColumns(Long tableId);

}
