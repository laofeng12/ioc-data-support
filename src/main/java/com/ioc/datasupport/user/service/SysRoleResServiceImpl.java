package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysRoleRes;
import com.ioc.datasupport.user.dto.RoleResInfo;
import com.ioc.datasupport.user.mapper.SysRoleResMapper;
import com.ioc.datasupport.user.service.SysRoleResService;
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
 * 角色资源权限表 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class SysRoleResServiceImpl extends ServiceImpl<SysRoleResMapper, SysRoleRes> implements SysRoleResService {

    @Override
    @Cacheable(value = "roleResInfos")
    public List<RoleResInfo> getRoleResInfos() {
        List<SysRoleRes> sysRoleResList = this.list();
        if (CollectionUtils.isEmpty(sysRoleResList)) {
            return Collections.emptyList();
        }

        return sysRoleResList.stream().map(RoleResInfo::new).collect(Collectors.toList());
    }
}
