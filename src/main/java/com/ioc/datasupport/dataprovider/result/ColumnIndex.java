package com.ioc.datasupport.dataprovider.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lsw
 * @Date: 2019/9/18 14:49
 */
@ApiModel("列信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnIndex {
    @ApiModelProperty("序号")
    private int index;

    @ApiModelProperty("列名")
    private String name;

    @ApiModelProperty("类备注")
    private String comment;

    @ApiModelProperty("字段类型")
    private String columnType;
}
