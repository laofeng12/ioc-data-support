package com.ioc.datasupport.warehouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
    private Long dbId;

    @ApiModelProperty("sql语句")
    private String sql;

    @ApiModelProperty("表名")
    private String tableName;
}
