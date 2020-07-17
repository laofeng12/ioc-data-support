package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.component.UserComponent;
import com.ioc.datasupport.datalake.domain.DlRescataResource;
import com.ioc.datasupport.datalake.mapper.DlRescataResourceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ljdp.component.exception.APIException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资源目录宽表 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Service
public class DlRescataResourceServiceImpl extends ServiceImpl<DlRescataResourceMapper, DlRescataResource> implements DlRescataResourceService {

    @Resource
    private DlRescataResourceMapper dlRescataResourceMapper;

    @Resource
    private DlRescataDatabaseService dlRescataDatabaseService;

    @Resource
    private UserComponent userComponent;

    @Override
    public List<DlRescataResource> getUserResource(Long dbId) throws APIException {
        // 根据dbId获取repositoryType
        Integer repositoryType = dlRescataDatabaseService.getRepositoryType(dbId);
        String userAccount = userComponent.getUserInfo().getUserAccount();

        return dlRescataResourceMapper.getUserResources("", repositoryType, userAccount);
    }
}
