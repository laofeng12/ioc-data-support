package com.ioc.datasupport.dataprovider;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.dataprovider.dto.DatasourceInfo;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.dataprovider.param.AggConfig;
import com.ioc.datasupport.dataprovider.param.CompositeConfig;
import com.ioc.datasupport.dataprovider.param.ConfigComponent;
import com.ioc.datasupport.dataprovider.param.DimensionConfig;
import com.ioc.datasupport.dataprovider.result.AggregateResult;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.SqlProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lsw
 */
public abstract class DataProvider {

    protected DatasourceInfo datasource;

    public DatasourceInfo getDatasource() {
        return this.datasource;
    }

    public void setDatasource(DatasourceInfo datasource) {
        this.datasource = datasource;
    }

    /** 空字符串标识（原值#NULL） */
    public static final String NULL_STRING = "";

    public static ConfigComponent separateNull(ConfigComponent configComponent) {
        if (configComponent instanceof DimensionConfig) {
            DimensionConfig cc = (DimensionConfig) configComponent;
            boolean needFilter = ("=".equals(cc.getFilterType()) || "≠".equals(cc.getFilterType())) && cc.getValues().size() > 1 &&
                    cc.getValues().stream().anyMatch(DataProvider.NULL_STRING::equals);
            if (needFilter) {
                CompositeConfig compositeConfig = new CompositeConfig();
                compositeConfig.setType("=".equals(cc.getFilterType()) ? "OR" : "AND");
                cc.setValues(cc.getValues().stream().filter(s -> !DataProvider.NULL_STRING.equals(s)).collect(Collectors.toList()));
                compositeConfig.getConfigComponents().add(cc);
                DimensionConfig nullCc = new DimensionConfig();
                nullCc.setColumn(cc.getColumn());
                nullCc.setFilterType(cc.getFilterType());
                nullCc.setValues(new ArrayList<>());
                nullCc.getValues().add(DataProvider.NULL_STRING);
                compositeConfig.getConfigComponents().add(nullCc);

                return compositeConfig;
            }
        }

        return configComponent;
    }

    /**
     * 获取表信息
     *
     * @param tableNameLike 表名查询关键字
     * @return              表信息集合
     * @throws Exception    异常
     */
    public abstract List<TableInfo> getTableList(String tableNameLike) throws Exception;

    /**
     * 获取表的列信息
     *
     * @param tableName  表名
     * @return           列集合信息
     * @throws Exception 异常
     */
    public abstract List<ColumnInfo> getColumnList(String tableName) throws Exception;

    /**
     * 查询表数据
     *
     * @param columns    列名集合
     * @param tableName  表名
     * @param page       分页
     * @param where      where条件
     * @return           查询结果（数据+列名）
     * @throws Exception 异常
     */
    public abstract AggregateResult queryTableData(List<String> columns, String tableName, String where, Page page) throws Exception;
}
