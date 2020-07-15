package com.ioc.datasupport.dataprovider.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lsw
 */
@ApiModel("聚合数据查询参数")
@Data
public class AggConfig {

    @ApiModelProperty("行维度（图：横轴）")
    private List<DimensionConfig> rows;

    @ApiModelProperty("列维度（图：纵轴）")
    private List<DimensionConfig> columns;

    @ApiModelProperty("过滤组件")
    private List<DimensionConfig> filters;

    @ApiModelProperty("指标")
    private List<ValueConfig> values;

    @ApiModelProperty("是否行汇总")
    private Boolean rowSum;

    @ApiModelProperty("是否列汇总")
    private Boolean colSum;

    @ApiModelProperty("备选字段，给前端的临时字段")
    private  String bft;

    @ApiModelProperty("表名")
    private String tableName;

    @ApiModelProperty("数据库类型")
    private Integer dbType;

    @ApiModelProperty("数据湖分库类别（1汇集库、2中心库、3基础库、4主题库、5专题库）")
    private Integer repositoryType;

    @ApiModelProperty("页码")
    private Long page;

    @ApiModelProperty("每页显示数量")
    private Long size;

    public AggConfig(){
        this.setRowSum(false);
        this.setColSum(false);
    }

    public AggConfig(List<DimensionConfig> rows, List<DimensionConfig> columns, List<ValueConfig> values){
        this.setRows(rows);
        this.setColumns(columns);
        this.setValues(values);
        this.setRowSum(false);
        this.setColSum(false);
    }
}
