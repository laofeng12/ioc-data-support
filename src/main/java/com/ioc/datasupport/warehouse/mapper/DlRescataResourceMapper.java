package com.ioc.datasupport.warehouse.mapper;

import com.ioc.datasupport.warehouse.domain.DlRescataResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 资源目录宽表 Mapper 接口
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
public interface DlRescataResourceMapper extends BaseMapper<DlRescataResource> {

    /**
     * 获取用户能够查看的资源列表
     *
     * @param createDetpId 部门ID
     * @param repositoryType 仓库类型
     * @param currentUserAccount 当前用户账号
     * @return 资源列表
     */
    List<DlRescataResource> getUserResources(String createDetpId, Integer repositoryType, String currentUserAccount);

}
