package com.ioc.datasupport.dataprovider.jdbc;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.hash.Hashing;
import com.ioc.datasupport.common.ExceptionConstants;
import com.ioc.datasupport.common.PublicConstant;
import com.ioc.datasupport.dataprovider.DataProvider;
import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.dataprovider.dto.JavaSqlTypeEnum;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.dataprovider.result.AggregateResult;
import com.ioc.datasupport.dataprovider.result.ColumnIndex;
import com.ioc.datasupport.dataprovider.result.JdbcTemplateAggPageResult;
import com.ioc.datasupport.dataprovider.result.JdbcTemplateAggResult;
import com.ioc.datasupport.util.EnumUtil;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.ljdp.component.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import javax.validation.constraints.NotBlank;
import java.net.ConnectException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


/**
 * 通用sql提供工具
 * @author lsw
 */
public class JdbcDataProvider extends DataProvider {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcDataProvider.class);

    private boolean pooled = false;

    private static final ConcurrentMap<String, DataSource> DATASOURCE_MAP = new ConcurrentHashMap<>(8);

    protected static final ConcurrentMap<String, JdbcTemplate> JDBC_TEMPLATE_MAP = new ConcurrentHashMap<>(8);

    @Override
    public List<TableInfo> getTableList(String tableNameLike) throws Exception {
        tableNameLike = StringUtils.isNotBlank(tableNameLike) ? tableNameLike : null;
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            //暂且只返回类型：TABLE（全部表类型。典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。）
            ResultSet tableRet = metaData.getTables(connection.getCatalog(), connection.getSchema(), tableNameLike, new String[]{"TABLE"});
            List<TableInfo> tableInfoList = new LinkedList<>();
            while (tableRet.next()) {
                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableSource(tableRet.getString("TABLE_NAME"));
                tableInfo.setTableComment(tableRet.getString("REMARKS"));
                tableInfoList.add(tableInfo);
            }
            return tableInfoList;
        } catch (Exception e) {
            LOG.error("\n[getTableList]数据库连接或查询异常,连接信息:{}", this.datasource, e);
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR, ExceptionConstants.EXCEPTION_ERROR_MSG + "数据库连接或查询异常:" + e.getMessage());
        }
    }

    @Override
    public List<ColumnInfo> getColumnList(String tableName) throws Exception {
        List<ColumnInfo> list;
        Map<Integer, ColumnInfo> map = new HashMap<>();
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            //精准匹配表，获取主键信息
            ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(), tableName);
            String primaryKeyName = null;
            if (primaryKeys.next()) {
                primaryKeyName = primaryKeys.getString("COLUMN_NAME");
            }

            //精准匹配表，获取表字段信息q
            ResultSet colRet = metaData.getColumns(connection.getCatalog(), connection.getSchema(), tableName, null);
            while (colRet.next()) {
                //字段名
                String columnName = colRet.getString("COLUMN_NAME");
                //字段注释
                String remarks = colRet.getString("REMARKS");
                //对应typesSql的值
                int javaTypesSql = colRet.getInt("DATA_TYPE");
                //字段长度
                int precision = colRet.getInt("COLUMN_SIZE");
                //精度
                int scale = colRet.getInt("DECIMAL_DIGITS");
                //是否为空：0就表示Not Null，1表示可以是Null
                int nullable = colRet.getInt("NULLABLE");
                //字段索引定位：从1开始
                int position = colRet.getInt("ORDINAL_POSITION");
                //默认值 oracle获取会报错
//                String defaultValue = colRet.getString("COLUMN_DEF");
                //所属数据表名
                String belongTableName = colRet.getString("TABLE_NAME");
                JavaSqlTypeEnum typeEnum = EnumUtil.getByCode(javaTypesSql, JavaSqlTypeEnum.class);
                String columnTypeName = String.valueOf(typeEnum.getName());

                ColumnInfo column = new ColumnInfo();
                column.setColumnIndex(position);
                column.setColumnSource(columnName);
                column.setColumnType(StringUtils.upperCase(columnTypeName));
                column.setColumnPrecision(precision);
                column.setColumnScale(scale);
                column.setNullable(nullable == 1);
                column.setColumnComment(remarks);
//                column.setDefaultValue(defaultValue);
                column.setBelongTableName(belongTableName);
                column.setIsPrimaryKey(java.util.Objects.equals(primaryKeyName, columnName));
                map.put(position, column);
            }
            list = new LinkedList<>(map.values());
        } catch (Exception e) {
            LOG.error("\n[getColumnList]数据库连接或查询异常:" + e.getMessage(), e);
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR, ExceptionConstants.EXCEPTION_ERROR_MSG + "数据库连接或查询异常:" + e.getMessage());
        }

        return list;
    }

    @Override
    public AggregateResult queryTableData(List<String> columns, String tableName, String where, Page page) throws Exception {
        columns = CollectionUtils.isEmpty(columns) ? Collections.emptyList() : columns;
        String exec = this.getQueryTableDataSql(Joiner.on(",").skipNulls().join(columns), tableName, where, page);
        List<String[]> list = new LinkedList<>();
        boolean isOracle = this.getDatasource().getDatabaseType() == PublicConstant.DB_TYPE_INT_ORACLE;

        LOG.debug(exec);
        try (
            Connection connection = getConnection();
            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(exec)
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    int colType = metaData.getColumnType(j + 1);
                    if (colType == Types.DATE) {
                        Date date = rs.getDate(j + 1);
                        row[j] = (date == null ? null : date.toString());
                    } else {
                        row[j] = rs.getString(j + 1);
                        //去除ORACLE数据库TIMESTAMP的秒数据
                        if (isOracle && java.util.Objects.equals(colType, Types.TIMESTAMP) && StringUtils.isNotBlank(row[j])) {
                            row[j] = row[j].replace(".0", "");
                        }
                    }
                }
                list.add(row);
            }
        } catch (Exception e) {
            LOG.error("\n[queryTableData]数据库连接或查询异常:" + e.getMessage(),e);
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR, "数据库连接或查询异常:"+e.getMessage());
        }

        //获取表的字段信息
        List<ColumnInfo> columnList = this.getColumnList(tableName);
        Map<@NotBlank String, ColumnInfo> columnInfoMap = columnList.stream().collect(Collectors.toMap(ColumnInfo::getColumnSource, (col) -> col));
        //根据columns，过滤，排序
        if (!CollectionUtils.isEmpty(columns)) {
            List<ColumnInfo> columnTempList = new ArrayList<>(columnList.size());
            for (String column : columns) {
                ColumnInfo columnInfo = columnInfoMap.get(column);
                if (columnInfo == null) {
                    continue;
                }

                columnTempList.add(columnInfo);
            }

            columnList = columnTempList;
        }

        List<ColumnIndex> colList = new ArrayList<>(columnList.size());
        for (ColumnInfo info : columnList) {
            ColumnIndex colIndex = new ColumnIndex();
            colIndex.setIndex(info.getColumnIndex());
            colIndex.setName(info.getColumnSource());
            colIndex.setComment(info.getColumnComment());
            colIndex.setColumnType(info.getColumnType());
            colList.add(colIndex);
        }

        //完整结果（字段信息+表数据）
        AggregateResult result = new AggregateResult();
        result.setData(list.toArray(new String[][]{}));
        result.setColumnList(colList);

        return result;
    }

    @Override
    public JdbcTemplateAggResult queryBySql(String sql) throws Exception {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        // 设置最大行数
        jdbcTemplate.setMaxRows(PublicConstant.SQL_MAX_ROWS);
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        JdbcTemplateAggResult result = new JdbcTemplateAggResult();
        result.setData(data);

        return result;
    }

    /*-------------------获取连接代码-------------------*/

    /**
     * 获取数据库连接
     *
     * @return Connection
     * @throws Exception 异常
     */
    public Connection getConnection() throws Exception {
        try {
            Connection conn;
            if (pooled) {
                //标识每一个连接
                String dataSourceUUID = JSONObject.toJSON(datasource).toString();
                String key = Hashing.md5().newHasher().putString(dataSourceUUID, Charsets.UTF_8).hash().toString();
                DataSource ds = DATASOURCE_MAP.get(key);
                if (ds == null) {
                    synchronized (key.intern()) {
                        ds = DATASOURCE_MAP.get(key);
                        if (ds == null) {
                            Map<String, String> conf = new HashMap<>(16);
                            conf.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, this.getDriver());
                            conf.put(DruidDataSourceFactory.PROP_URL, this.getConnectUrl());
                            conf.put(DruidDataSourceFactory.PROP_USERNAME, datasource.getUsername());
                            if (StringUtils.isNotBlank(datasource.getPassword())) {
                                conf.put(DruidDataSourceFactory.PROP_PASSWORD, datasource.getPassword());
                            }
                            conf.put(DruidDataSourceFactory.PROP_INITIALSIZE, "2");
                            //其他优化参数
                            //最大连接池数量
                            conf.put(DruidDataSourceFactory.PROP_MAXACTIVE, "10");
                            //最小连接池数量
                            conf.put(DruidDataSourceFactory.PROP_MINIDLE, "2");
                            //获取连接时最大等待时间，单位毫秒
                            conf.put(DruidDataSourceFactory.PROP_MAXWAIT, "20000");
                            //是否缓存preparedStatement，也就是PSCache
                            conf.put(DruidDataSourceFactory.PROP_POOLPREPAREDSTATEMENTS, "false");
                            //要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true
                            conf.put(DruidDataSourceFactory.PROP_MAXOPENPREPAREDSTATEMENTS, "20");
                            //用来检测连接是否有效的sql
                            conf.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, this.getValidationQuery());
                            //接是否有效的sql的最大等待时间
                            conf.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY_TIMEOUT, "1");
                            //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
                            conf.put(DruidDataSourceFactory.PROP_TESTONBORROW, "false");
                            //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
                            conf.put(DruidDataSourceFactory.PROP_TESTONRETURN, "false");
                            //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
                            conf.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, "true");
                            //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
                            //监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
                            conf.put(DruidDataSourceFactory.PROP_FILTERS, "stat");
                            //oralce获取注释
                            if (Objects.equal(PublicConstant.DB_TYPE_INT_ORACLE, datasource.getDatabaseType())) {
                                conf.put("remarksReporting", "true");
                            }
                            // mysql 获取表注释 的配置
                            if (Objects.equal(PublicConstant.DB_TYPE_INT_MYSQL_LOW, datasource.getDatabaseType())
                                    || Objects.equal(PublicConstant.DB_TYPE_INT_MYSQL_HIGH, datasource.getDatabaseType())) {
                                conf.put("useInformationSchema", "true");
                            }

                            DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(conf);
                            dataSource.setBreakAfterAcquireFailure(true);
                            dataSource.setConnectionErrorRetryAttempts(2);
                            DATASOURCE_MAP.put(key, dataSource);
                            ds = DATASOURCE_MAP.get(key);
                        }
                    }
                }
                try {
                    conn = ds.getConnection();
                } catch (SQLException e) {
                    DATASOURCE_MAP.remove(key);
                    LOG.error("\n[getConnection]数据库连接失败:" + e.getMessage(), e);
                    LOG.error("\n[getConnection]数据库连接失败,dataSource:{}", JSONObject.toJSON(datasource));
                    throw e;
                }
                return conn;
            } else {
                Class.forName(this.getDriver());
                Properties props = new Properties();
                props.setProperty("user", datasource.getUsername());
                if (StringUtils.isNotBlank(datasource.getPassword())) {
                    props.setProperty("password", datasource.getPassword());
                }
                //oralce获取注释
                if (Objects.equal(PublicConstant.DB_TYPE_INT_ORACLE, datasource.getDatabaseType())) {
                    props.put("remarksReporting", "true");
                }
                // mysql 获取表注释 的配置
                if (Objects.equal(PublicConstant.DB_TYPE_INT_MYSQL_LOW, datasource.getDatabaseType())
                        || Objects.equal(PublicConstant.DB_TYPE_INT_MYSQL_HIGH, datasource.getDatabaseType())) {
                    props.put("useInformationSchema", "true");
                }
                DriverManager.setLoginTimeout(20);
                return DriverManager.getConnection(this.getConnectUrl(), props);
            }
        }catch (ConnectException e){
            LOG.error("\n[getConnection]数据库连接超时,dataSource:{}", JSONObject.toJSON(datasource),e);
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR,"数据库连接超时!");
        } catch (Exception e){
            LOG.error("\n[getConnection]数据库操作异常,dataSource:{}", JSONObject.toJSON(datasource),e);
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR,"数据库操作异常,原因:"+e.getMessage());
        }
    }

    /*-----------------获取JdbcTemplate------------------*/

    /**
     * 获取jdbcTemplate
     * @return
     * @throws Exception
     */
    private JdbcTemplate getJdbcTemplate() throws Exception {
        String password = this.datasource.getPassword();

        String key = Hashing.md5().newHasher().putString(JSONObject.toJSON(this.datasource).toString(), Charsets.UTF_8).hash().toString();
        JdbcTemplate jdbcTemplate = JDBC_TEMPLATE_MAP.get(key);
        if (jdbcTemplate != null) {
            return jdbcTemplate;
        }

        DataSource ds = DATASOURCE_MAP.get(key);
        if (ds == null) {
            synchronized (key.intern()) {
                ds = DATASOURCE_MAP.get(key);
                if (ds == null) {
                    Map<String, String> conf = new HashedMap<>(16);
                    //驱动
                    conf.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, this.getDriver());
                    //连接URL
                    conf.put(DruidDataSourceFactory.PROP_URL, this.getConnectUrl());
                    //用户名
                    conf.put(DruidDataSourceFactory.PROP_USERNAME, datasource.getUsername());
                    if (org.apache.commons.lang.StringUtils.isNotBlank(password)) {
                        //密码
                        conf.put(DruidDataSourceFactory.PROP_PASSWORD, password);
                    }
                    //初始大小
                    conf.put(DruidDataSourceFactory.PROP_INITIALSIZE, "3");
                    //其他优化参数
                    //最大连接池数量
                    conf.put(DruidDataSourceFactory.PROP_MAXACTIVE, "100");
                    //最小连接池数量
                    conf.put(DruidDataSourceFactory.PROP_MINIDLE, "1");
                    //获取连接时最大等待时间，单位毫秒
                    conf.put(DruidDataSourceFactory.PROP_MAXWAIT, "10000");
                    //是否缓存preparedStatement，也就是PSCache
                    conf.put(DruidDataSourceFactory.PROP_POOLPREPAREDSTATEMENTS, "false");
                    //要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true
                    conf.put(DruidDataSourceFactory.PROP_MAXOPENPREPAREDSTATEMENTS, "20");
                    //用来检测连接是否有效的sql
                    conf.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, this.getValidationQuery());
                    //接是否有效的sql的最大等待时间
                    conf.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY_TIMEOUT, "1");
                    //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
                    conf.put(DruidDataSourceFactory.PROP_TESTONBORROW, "false");
                    //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
                    conf.put(DruidDataSourceFactory.PROP_TESTONRETURN, "false");
                    //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
                    conf.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, "true");
                    //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
                    //监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
                    conf.put(DruidDataSourceFactory.PROP_FILTERS, "stat");
                    //oralce获取注释
                    conf.put("remarksReporting","true");

                    DruidDataSource druidDS = (DruidDataSource) DruidDataSourceFactory.createDataSource(conf);
                    //连接失败后断开
                    druidDS.setBreakAfterAcquireFailure(true);
                    //连接失败重试次数
                    druidDS.setConnectionErrorRetryAttempts(2);
                    DATASOURCE_MAP.put(key, druidDS);
                    ds = DATASOURCE_MAP.get(key);
                }

            }
        }
        jdbcTemplate = new JdbcTemplate(ds);
        JDBC_TEMPLATE_MAP.put(key, jdbcTemplate);

        return jdbcTemplate;
    }

    /*-----------------需要被子类重载的方法------------------*/

    /**
     * 获取驱动
     *
     * @return 驱动String
     */
    public String getDriver() {
        // 不同数据库驱动不同
        return "";
    }

    /**
     * 获取连接用URL
     *
     * @return jdbc连接
     */
    public String getConnectUrl() {
        return null;
    }

    /**
     * 获取ValidationQuery的sql
     *
     * @return sql
     */
    protected String getValidationQuery() {
        //不同数据库检测语句不同
        return "";
    }

    /**
     * 获取查询表数据sql
     *
     * @param columns   列名
     * @param tableName 表名
     * @param where     条件
     * @return          sql语句
     */
    protected String getQueryTableDataSql(String columns, String tableName, String where, Page page) {
        return "";
    }

    /*----辅助函数----*/

    /**
     * 填充分页sql到原sql
     *
     * @param dbType 数据库类型
     * @param page   页码
     * @param size   页大小
     */
    protected String fillPageSql(String orgSql, String dimColsStr, Integer dbType, Long page, Long size) {
        StringBuilder sb = new StringBuilder();
        if (dbType == null || page == null || size == null) {
            return orgSql;
        }

        Long index = page * size;
        switch (dbType) {
            case PublicConstant.DB_TYPE_INT_ORACLE:
                // select * from (select ROWNUM as rowno, t.* from (select * from ds_data_source) t) l where l.rowno between 2 and 3
                sb.append(" select ");
                sb.append(dimColsStr);
                sb.append(" from (select ROWNUM as rowno, t_n.* from ( ");
                sb.append(orgSql);
                sb.append(" ) t_n) t_n2 where t_n2.rowno between ");
                sb.append(index + 1);
                sb.append(" and ");
                sb.append(index + size);
                // oracle的分页要嵌套好几层
                return sb.toString();
            case PublicConstant.DB_TYPE_INT_MYSQL_HIGH:
            case PublicConstant.DB_TYPE_INT_MYSQL_LOW:
                sb.append(" limit ");
                sb.append(index);
                sb.append(",");
                sb.append(size);
                sb.append(" ");
                return orgSql + sb.toString();
            case PublicConstant.DB_TYPE_INT_POSTGRE:
                sb.append(" limit ");
                sb.append(size);
                sb.append(" offset ");
                sb.append(index);
                sb.append(" ");
                return orgSql + sb.toString();
            case PublicConstant.DB_TYPE_INT_HIVE:
                //select * from (select row_number() over (order by create_time desc) as rownum,u.* from user u) mm where mm.rownum between 10 and 15;
                sb.append("select ");
                sb.append(dimColsStr);
                sb.append(" from (select row_number() over() as rownum,t_n.* from( ");
                sb.append(orgSql);
                sb.append(") t_n) t_n1 where t_n1.rownum between ");
                sb.append(index);
                sb.append(" and ");
                sb.append(size);
                sb.append(" ");
                return sb.toString();
            default:
                break;
        }

        return orgSql;
    }
}
