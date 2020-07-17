package com.ioc.datasupport.datalake.param;

import com.ioc.datasupport.common.CommonParam;
import com.ioc.datasupport.datalake.domain.DlInsensitivesRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: lsw
 * @Date: 2020/7/17 9:35
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("脱敏规则")
@Data
public class DlInsensitivesRuleParam extends CommonParam<DlInsensitivesRule> {

    @ApiModelProperty("规则ID")
    private List<Long> in_insensitivesRuleId;
}
