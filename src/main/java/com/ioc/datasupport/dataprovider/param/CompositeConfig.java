package com.ioc.datasupport.dataprovider.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@ApiModel("复合配置")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompositeConfig extends ConfigComponent {

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("集合")
    private ArrayList<ConfigComponent> configComponents = new ArrayList<>();
}
