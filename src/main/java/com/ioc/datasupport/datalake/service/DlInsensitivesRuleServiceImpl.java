package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.datalake.domain.DlInsensitivesRule;
import com.ioc.datasupport.datalake.mapper.DlInsensitivesRuleMapper;
import com.ioc.datasupport.datalake.param.DlInsensitivesRuleParam;
import com.ioc.datasupport.datalake.service.DlInsensitivesRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 脱敏规则 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-17
 */
@Service
public class DlInsensitivesRuleServiceImpl extends ServiceImpl<DlInsensitivesRuleMapper, DlInsensitivesRule> implements DlInsensitivesRuleService {

    @Override
    public List<DlInsensitivesRule> findByInsensitivesRuleIds(List<Long> insensitivesRuleIds) {
        DlInsensitivesRuleParam param = new DlInsensitivesRuleParam();
        param.setIn_insensitivesRuleId(insensitivesRuleIds);

        return this.list(param.genQueryWrapper());
    }
}
