package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.datalake.domain.DlRescataStrucPermi;
import com.ioc.datasupport.datalake.mapper.DlRescataStrucPermiMapper;
import com.ioc.datasupport.datalake.param.DlRescataStrucPermiParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源目录字段权限表 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-15
 */
@Service
public class DlRescataStrucPermiServiceImpl extends ServiceImpl<DlRescataStrucPermiMapper, DlRescataStrucPermi> implements DlRescataStrucPermiService {

    @Override
    public List<DlRescataStrucPermi> findByStructureIdInAndOwnerAccount(List<Long> structureIds, String ownerAccount) {
        DlRescataStrucPermiParam param =  new DlRescataStrucPermiParam();
        param.setIn_structureId(structureIds);
        param.setEq_ownerAccount(ownerAccount);

        return this.list(param.genQueryWrapper());
    }
}
