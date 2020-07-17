package com.ioc.datasupport.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: lsw
 * @Date: 2020/7/16 9:28
 */
@ApiModel("查询数据参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryDataDTO {

    @ApiModelProperty("数据库ID")
    @NotNull
    @Max(10)
    @Min(1)
    private Long dbId;

    @ApiModelProperty("sql语句")
    @NotBlank
    private String sql;

    @ApiModelProperty("表名")
    @NotBlank
    private String tableName;

    @ApiModelProperty("表ID")
    @NotNull
    private Long tableId;
}
