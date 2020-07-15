package com.ioc.datasupport.warehouse.dto;

import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.warehouse.domain.DlRescataColumn;
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

    @ApiModelProperty("是否不用加密")
    private Integer isDecryption;

    @ApiModelProperty("是否不用脱敏")
    private Integer isSensitived;

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
        // 字段类型 TODO dataType （1短字符、2较长字符、3长字符、4日期型、5整数型、6小数型）

    }
}
