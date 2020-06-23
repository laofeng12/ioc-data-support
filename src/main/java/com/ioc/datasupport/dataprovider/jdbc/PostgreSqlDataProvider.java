package com.ioc.datasupport.dataprovider.jdbc;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.dataprovider.annotation.ProviderName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;


@ProviderName(name = "postgreSql")
public class PostgreSqlDataProvider extends JdbcDataProvider {

    @Override
    public String getDriver() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getConnectUrl() {
        // jdbc:postgresql://19.104.59.17:25308/ioc_resourcedirect
        StringBuilder sb = new StringBuilder();
        String ip = datasource.getHostIp();
        String port = String.valueOf(datasource.getPort());
        String dataBaseName = datasource.getDatabaseName();
        String schema = StringUtils.isNotBlank(datasource.getSchemaName()) ? datasource.getSchemaName() : "public";

        sb.append("jdbc:postgresql://");
        sb.append(ip);
        sb.append(":");
        sb.append(port);
        sb.append("/");
        sb.append(dataBaseName);
        //?currentSchema=
        sb.append("?searchpath=");
        sb.append(schema);

        return sb.toString();
    }

    @Override
    protected String getValidationQuery() {
        return "select 'Hello World' as hello";
    }

    @Override
    public String getQueryTableDataSql(String columns, String tableName, String where, Page page) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        if (StringUtils.isNotBlank(columns)) {
            String noSpaceColumns = columns.replace(" ", "");
            String[] columnArray = noSpaceColumns.split(",");
            sb.append(StringUtils.join(columnArray, ","));
        } else {
            sb.append("*");
        }
        sb.append(" from ");
        sb.append(tableName);
        if (StringUtils.isNotBlank(where)) {
            sb.append(" where ");
            sb.append(where);
        }
        if (page != null && page.getSize() != 0) {
            sb.append(" limit ");
            sb.append(page.getSize());
            sb.append(" offset ");
            sb.append(page.getCurrent() - 1);
        }

        return sb.toString();
    }
}
