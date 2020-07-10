package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ioc.datasupport.user.dto.UserInfo;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取用户数据
     *
     * @return 用户数据
     */
    List<UserInfo> getUsers();


}
