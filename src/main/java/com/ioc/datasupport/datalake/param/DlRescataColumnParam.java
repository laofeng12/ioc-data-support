package com.ioc.datasupport.datalake.param;

import com.ioc.datasupport.common.CommonParam;
import com.ioc.datasupport.datalake.domain.DlRescataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: lsw
 * @Date: 2020/7/15 14:15
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("列信息")
@Data
public class DlRescataColumnParam extends CommonParam<DlRescataColumn> {

    @ApiModelProperty("资源ID")
    private Long eq_resourceId;

}
