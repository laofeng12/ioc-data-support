package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysOrg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ioc.datasupport.user.dto.OrgInfo;

import java.util.List;

/**
 * <p>
 * 组织机构管理 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
public interface SysOrgService extends IService<SysOrg> {

    /**
     * 获取组织机构信息
     *
     * @return 组织机构信息
     */
    List<OrgInfo> getOrgs();

}
