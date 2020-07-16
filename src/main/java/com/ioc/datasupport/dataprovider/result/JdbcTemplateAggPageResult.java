package com.ioc.datasupport.dataprovider.result;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: lsw
 * @Date: 2020/7/15 17:49
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("查询结果信息-分页")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcTemplateAggPageResult extends JdbcTemplateAggResult {

    @ApiModelProperty
    private long total;

    @ApiModelProperty
    private long totalPage;

    @ApiModelProperty
    private long size;

    @ApiModelProperty
    private long number;

    public JdbcTemplateAggPageResult(JdbcTemplateAggResult result, Page page) {
        this.setColumnList(result.getColumnList());
        this.setData(result.getData());
        long total = result.getData() == null ? 0L : result.getData().size();
        page.setTotal(total);
        this.total = page.getTotal();
        this.totalPage = page.getPages();
        this.size = page.getSize();
        this.number = page.getCurrent();
    }
}
