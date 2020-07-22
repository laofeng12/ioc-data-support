package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysRes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ioc.datasupport.user.dto.ResInfo;

import java.util.List;

/**
 * <p>
 * 菜单资源表 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
public interface SysResService extends IService<SysRes> {

    /**
     * 获取资源信息
     *
     * @return 资源信息
     */
    List<ResInfo> getResInfos();

}
