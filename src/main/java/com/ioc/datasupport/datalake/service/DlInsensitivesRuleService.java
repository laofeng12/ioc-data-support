package com.ioc.datasupport.datalake.service;

import com.ioc.datasupport.datalake.domain.DlInsensitivesRule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 脱敏规则 服务类
 * </p>
 *
 * @author lsw
 * @since 2020-07-17
 */
public interface DlInsensitivesRuleService extends IService<DlInsensitivesRule> {

    List<DlInsensitivesRule> findByInsensitivesRuleIds(List<Long> insensitivesRuleIds);
}
