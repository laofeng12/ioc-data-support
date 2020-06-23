package com.ioc.datasupport.dataprovider.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("数据表字段配置")
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
public class ColumnInfo implements Serializable {

    /*@ApiModelProperty("表字段ID,为空新增")
    @NotNull
    private Long columnId;*/

    @ApiModelProperty(value = "字段列名",required = true)
    @Length(min=0, max=256)
    @NotBlank
    private String columnSource;

    /*@ApiModelProperty(value = "是否更新字段：数值型ID主键或日期型",required = true)
    @Value("false")
    private Boolean isUpdateColumn;*/

    @ApiModelProperty(value = "字段类型",required = true)
    @Length(min=0, max=32)
    @NotBlank
    private String columnType;

    @ApiModelProperty(value = "字段位置",required = false)
    @Max(99999999L)
    private Integer columnIndex;

    @ApiModelProperty(value = "字段长度",required = true)
    @Max(99999999L)
    @NotNull
    private Integer columnPrecision;

    @ApiModelProperty(value = "小数位数",required = true)
    @Max(99L)
    @NotNull
    private Integer columnScale;

    /*@ApiModelProperty("默认值")
    @Length(min=0, max=256)
    private String defaultValue;*/

    @ApiModelProperty(value = "是否主键（0否，1是）",required = true)
    private Boolean isPrimaryKey;

    @ApiModelProperty(value = "字段可否为空（0否，1是）",required = true)
    private Boolean nullable;

    @ApiModelProperty("备注")
    @Length(min=0, max=512)
    private String columnComment;

    /*@ApiModelProperty(value = "排序",required = true)
    @Max(99999999L)
    private Integer sort;*/

    @ApiModelProperty("所属数据表名")
    private String belongTableName;
}
