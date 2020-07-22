package com.ioc.datasupport.warehouse.dto;

import com.ioc.datasupport.datalake.domain.DlInsensitivesRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author: lsw
 * @Date: 2020/7/17 10:24
 */
@ApiModel("列权限信息-带脱敏规则")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ColumnPermUseInfo extends ColumnPermInfo {

    @ApiModelProperty("脱敏规则")
    private DlInsensitivesRule insensitivesRule;

}
