package com.ioc.datasupport.warehouse.param;

import com.ioc.datasupport.common.CommonParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author: lsw
 * @Date: 2020/6/19 9:22
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("仓库数据")
@Data
public class DlRescataDatabaseParam<T> extends CommonParam<T> {

    @ApiModelProperty("databaseId in")
    private List<Long> in_databaseId;

}
