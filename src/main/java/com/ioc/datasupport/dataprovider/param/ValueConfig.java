package com.ioc.datasupport.dataprovider.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("指标配置")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValueConfig {
    @ApiModelProperty("列名")
    private String column;

    @ApiModelProperty("聚合类型：sum、avg、max、min、distinct、count")
    private String aggType;

    @ApiModelProperty("排序")
    private String sortType;

    @ApiModelProperty("字段别名")
    private String alias;

    /*----数据湖特有----*/

    @ApiModelProperty("数据湖字段ID")
    private Long dlColumnId;
}

