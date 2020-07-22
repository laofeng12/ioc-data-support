package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysUserRole;
import com.ioc.datasupport.user.dto.RoleInfo;
import com.ioc.datasupport.user.dto.UserRoleInfo;
import com.ioc.datasupport.user.mapper.SysUserRoleMapper;
import com.ioc.datasupport.user.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关系 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    @Cacheable(value = "userRoleInfos")
    public List<UserRoleInfo> getUserRoleInfos() {
        List<SysUserRole> sysUserRoles = this.list();
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return Collections.emptyList();
        }

        return sysUserRoles.stream().map(UserRoleInfo::new).collect(Collectors.toList());
    }
}
