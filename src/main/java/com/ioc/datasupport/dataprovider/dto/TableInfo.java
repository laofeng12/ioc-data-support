package com.ioc.datasupport.dataprovider.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

@ApiModel("数据表配置")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class TableInfo implements Serializable {

    @ApiModelProperty("表名")
    @Length(min=0, max=64)
    private String tableSource;

    @ApiModelProperty("表备注")
    @Length(min=0, max=128)
    private String tableComment;

    @ApiModelProperty("字段配置")
    private List<ColumnInfo> columns;
}
