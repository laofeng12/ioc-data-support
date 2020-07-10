package com.ioc.datasupport.warehouse.service;

import com.ioc.datasupport.warehouse.domain.DlRescataResource;
import com.ioc.datasupport.warehouse.mapper.DlRescataResourceMapper;
import com.ioc.datasupport.warehouse.service.DlRescataResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public List<DlRescataResource> getUserResource(Long dbId) {
        return dlRescataResourceMapper.getUserResources("", dbId, "");
    }
}
