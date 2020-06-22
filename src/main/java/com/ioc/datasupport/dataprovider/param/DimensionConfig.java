package com.ioc.datasupport.dataprovider.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lsw
 */
@ApiModel("纬度配置")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensionConfig extends ConfigComponent implements Serializable {

    @ApiModelProperty("字段名")
    private String column;

    @ApiModelProperty("字段类型：number、string 、date、time")
    private String columnType;

    @ApiModelProperty("排序")
    private String sortType;

    @ApiModelProperty("字段别名")
    private String alias;

    /*----过滤设置----*/

    @ApiModelProperty("过滤类型：=、eq、≠、ne、包含、不包含、>、<、≥、≤、(a,b]、[a,b)、(a,b)、[a,b]")
    private String filterType;

    @ApiModelProperty("过滤值")
    private List<String> values;

    /*----数据湖特有----*/

    @ApiModelProperty("数据湖字段ID")
    private Long dlColumnId;


}
