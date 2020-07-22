package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ioc.datasupport.user.dto.RoleInfo;
import com.ioc.datasupport.user.dto.UserRoleInfo;

import java.util.List;

/**
 * <p>
 * 用户角色关系 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    List<UserRoleInfo> getUserRoleInfos();

}
