package com.ioc.datasupport.dataprovider.dto;

import com.ioc.datasupport.datalake.domain.DlRescataResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@ApiModel("数据表配置")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo implements Serializable {

    @ApiModelProperty("表ID")
    private Long tableId;

    @ApiModelProperty("表名")
    @Length(min=0, max=64)
    private String tableSource;

    @ApiModelProperty("表备注")
    @Length(min=0, max=128)
    private String tableComment;

    @ApiModelProperty("字段配置")
    private List<ColumnInfo> columns;

    public TableInfo (DlRescataResource dlRescataResource) {
        this.tableId = dlRescataResource.getResourceId();
        this.tableSource = dlRescataResource.getResourceTableName();
        this.tableComment = dlRescataResource.getResourceName();
        columns = Collections.emptyList();
    }
}
