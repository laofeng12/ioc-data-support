package com.ioc.datasupport.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.ljdp.component.result.BasicApiResponse;
import org.ljdp.ui.bootstrap.TablePage;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author: lsw
 * @Date: 2020/6/8 9:46
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MbpTablePageImpl<T> extends BasicApiResponse implements TablePage<T> {

    private long total;
    private List<T> rows;
    private long totalPage;
    private long size;
    private long number;

    public MbpTablePageImpl() {
    }

    public MbpTablePageImpl(Page<T> page) {
        this.total = page.getTotalElements();
        this.rows = page.getContent();
        this.totalPage = page.getTotalPages();
        this.size = page.getSize();
        this.number = page.getNumber();

        this.setCode(PublicConstant.CODE_SUCCESS);
        this.setMessage("成功");
    }

    public MbpTablePageImpl(IPage<T> page) {
        this.total = page.getTotal();
        this.rows = page.getRecords();
        this.totalPage = page.getPages();
        this.size = page.getSize();
        this.number = page.getCurrent();

        this.setCode(PublicConstant.CODE_SUCCESS);
        this.setMessage("成功");
    }

    public MbpTablePageImpl(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;

        this.setCode(PublicConstant.CODE_SUCCESS);
        this.setMessage("成功");
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public long getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public long getNumber() {
        return this.number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
