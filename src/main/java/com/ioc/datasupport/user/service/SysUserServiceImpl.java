package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysUser;
import com.ioc.datasupport.user.dto.UserInfo;
import com.ioc.datasupport.user.mapper.SysUserMapper;
import com.ioc.datasupport.user.param.SysUserParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysOrgService sysOrgService;

    @Override
    public List<UserInfo> getUsers() {
        List<SysUser> sysUserList = this.list();
        if (CollectionUtils.isEmpty(sysUserList)) {
            return Collections.emptyList();
        }

        return sysUserList.stream().map(UserInfo::new).collect(Collectors.toList());
    }
}
