package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysOrg;
import com.ioc.datasupport.user.dto.OrgInfo;
import com.ioc.datasupport.user.mapper.SysOrgMapper;
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
 * 组织机构管理 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

    @Override
    @Cacheable(value = "orgs", sync = true)
    public List<OrgInfo> getOrgs() {
        List<SysOrg> list = this.list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        return list.stream().map(OrgInfo::new).collect(Collectors.toList());
    }
}
