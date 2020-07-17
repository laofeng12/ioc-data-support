package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.datalake.domain.DlRescataColumn;
import com.ioc.datasupport.datalake.mapper.DlRescataColumnMapper;
import com.ioc.datasupport.datalake.param.DlRescataColumnParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源目录结构表 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Service
public class DlRescataColumnServiceImpl extends ServiceImpl<DlRescataColumnMapper, DlRescataColumn> implements DlRescataColumnService {

    @Override
    public List<DlRescataColumn> getUserColumns(Long tableId) {
        DlRescataColumnParam param = new DlRescataColumnParam();
        param.setEq_resourceId(tableId);

        return this.list(param.genQueryWrapper());
    }
}
