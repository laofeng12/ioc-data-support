package com.ioc.datasupport.warehouse.dto;

import com.ioc.datasupport.datalake.domain.DlInsensitivesRule;
import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.datalake.domain.DlRescataColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Objects;

/**
 * @author: lsw
 * @Date: 2020/7/15 14:57
 */
@ApiModel("列权限信息")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ColumnPermInfo extends ColumnInfo {

    @ApiModelProperty("列ID")
    private Long columnId;

    @ApiModelProperty("展示类型：0:明文、1:加密、2:脱敏、3:空")
    private Integer showType;

    @ApiModelProperty("字段类型code")
    private Long columnTypeCode;

    @ApiModelProperty("脱敏规则ID")
    private Long insensitivesRuleId;

    @ApiModelProperty("脱敏开始与结束位置")
    private String insensitivesStartEnd;

    public ColumnPermInfo(DlRescataColumn dlRescataColumn) {
        // 列ID
        this.columnId = dlRescataColumn.getStructureId();
        // 列名
        this.setColumnSource(dlRescataColumn.getColumnDefinition());
        // 列注释
        this.setColumnComment(dlRescataColumn.getColumnComment());
        // 是否主键
        this.setIsPrimaryKey(Objects.equals(dlRescataColumn.getIsPrimaryKey(), 1));
        // 列长度
        this.setColumnPrecision(dlRescataColumn.getColumnLength());
        // 列小数位
        this.setColumnScale(dlRescataColumn.getDecimalLength());
        // 字段类型code
        this.columnTypeCode = dlRescataColumn.getColumnCode();
        // 脱敏规则ID
        this.insensitivesRuleId = dlRescataColumn.getInsensitivesRuleId();
        // 脱敏起终位置
        this.insensitivesStartEnd = dlRescataColumn.getInsensitivesStartEnd();
    }
}
