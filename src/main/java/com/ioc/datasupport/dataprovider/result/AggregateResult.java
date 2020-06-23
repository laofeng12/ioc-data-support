package com.ioc.datasupport.dataprovider.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author: lsw
 * @Date: 2019/9/18 14:50
 */
@ApiModel("查询结果信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregateResult {

    @ApiModelProperty("列信息")
    private List<ColumnIndex> columnList;

    @ApiModelProperty("数据")
    private String[][] data;

}
