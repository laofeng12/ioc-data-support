package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ioc.datasupport.user.dto.RoleInfo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    List<RoleInfo> getRoleInfos();

}
