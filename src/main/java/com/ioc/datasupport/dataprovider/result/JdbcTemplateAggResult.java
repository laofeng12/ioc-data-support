package com.ioc.datasupport.dataprovider.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author: lsw
 * @Date: 2019/6/15 13:59
 */
@ApiModel("查询结果信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcTemplateAggResult {

    @ApiModelProperty("列信息")
    private List<ColumnIndex> columnList;

    @ApiModelProperty("数据")
    private List<Map<String, Object>> data;
}
