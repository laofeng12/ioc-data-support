package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.datalake.domain.DlRescataResource;
import com.baomidou.mybatisplus.extension.service.IService;
import org.ljdp.component.exception.APIException;

import java.util.List;

/**
 * <p>
 * 资源目录宽表 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
public interface DlRescataResourceService extends IService<DlRescataResource> {

    List<DlRescataResource> getUserResource(Long dbId) throws APIException;

}
