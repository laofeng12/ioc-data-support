package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.datalake.domain.DlRescataStrucPermi;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源目录字段权限表 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-15
 */
public interface DlRescataStrucPermiService extends IService<DlRescataStrucPermi> {

    List<DlRescataStrucPermi> findByStructureIdInAndOwnerAccount(List<Long> structureIds, String ownerAccount);

}
