package com.ioc.datasupport.warehouse.param;

import com.ioc.datasupport.common.CommonParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: lsw
 * @Date: 2020/7/15 14:35
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("列权限")
@Data
public class DlRescataStrucPermiParam<T> extends CommonParam<T> {

    @ApiModelProperty("in 结构ID")
    private List<Long> in_structureId;

    @ApiModelProperty("eq 创建账号")
    private String eq_ownerAccount;
}
