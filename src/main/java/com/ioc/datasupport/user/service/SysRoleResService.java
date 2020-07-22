package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysRoleRes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ioc.datasupport.user.dto.RoleResInfo;

import java.util.List;

/**
 * <p>
 * 角色资源权限表 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
public interface SysRoleResService extends IService<SysRoleRes> {

    /**
     * 角色资源权限信息
     *
     * @return 角色资源权限信息
     */
    List<RoleResInfo> getRoleResInfos();

}
