package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysRole;
import com.ioc.datasupport.user.domain.SysUserRole;
import com.ioc.datasupport.user.dto.RoleInfo;
import com.ioc.datasupport.user.mapper.SysRoleMapper;
import com.ioc.datasupport.user.service.SysRoleService;
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
 * 角色表 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    @Cacheable(value = "roleInfos")
    public List<RoleInfo> getRoleInfos() {
        List<SysRole> sysRoles = this.list();
        if (CollectionUtils.isEmpty(sysRoles)) {
            return Collections.emptyList();
        }

        return sysRoles.stream().map(RoleInfo::new).collect(Collectors.toList());
    }

}
