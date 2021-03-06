package com.ioc.datasupport.common;

import org.ljdp.component.result.APIConstants;

/**
 * @author: lsw
 * @Date: 2020/6/19 10:32
 */
public class PublicConstant extends APIConstants {

    public static final String URL_V1 = "dsp/v1/";

    /**
     * 认证token关键字
     */
    public static final String TOKEN = "authority-token";

    /**
     * 认证agent关键字
     */
    public static final String AGENT = "User-Agent";

    /**
     * AUTHORIZATION
     */
    public static final String AUTHORIZATION = "Authorization";

    /**
     * 无权限
     */
    public static int PERMISSIONS_NO = -30000;

    /**
     * 无权限提示信息
     */
    public static String PERMISSIONS_NO_MSG = "该用户无权限操作";

    /**
     * 显示
     */
    public static final Long SHOW = 1L;
    /**
     * 不显示
     */
    public static final Long DISSHOW = 0L;

    /**
     * 是
     */
    public static final Long YES = 1L;
    /**
     * 否
     */
    public static final Long NO = 0L;

    /**
     * Oracle
     */
    public static final int DB_TYPE_INT_ORACLE = 0;

    /**
     * MySql高版本
     */
    public static final int DB_TYPE_INT_MYSQL_HIGH = 1;

    /**
     * Mysql低版本
     */
    public static final int DB_TYPE_INT_MYSQL_LOW = 2;

    /**
     * PostgreSql
     */
    public static final int DB_TYPE_INT_POSTGRE = 3;

    /**
     * 普通hive
     */
    public static final int DB_TYPE_INT_HIVE = 4;

    /**
     * sql_server
     */
    public static final int DB_TYPE_INT_SQL_SERVER = 5;

    /**
     * 华为FusionInsight版本hive
     */
    public static final int DB_TYPE_INT_HIVE_HUAWEI = 6;

    /**
     * 查询结果最大行数
     */
    public static final int SQL_MAX_ROWS = 1_000_000;

    // 数据湖字段展示类型（字段权限）
    /**
     * 明文
     */
    public static final Integer DL_COLUMN_SHOW_TYPE_PUBLIC = 0;

    /**
     * 加密
     */
    public static final Integer DL_COLUMN_SHOW_TYPE_ENCRY = 1;

    /**
     * 脱敏
     */
    public static final Integer DL_COLUMN_SHOW_TYPE_DESEN = 2;

    /**
     * 空
     */
    public static final Integer DL_COLUMN_SHOW_TYPE_EMPTY = 3;

    /**
     * 空值
     */
    public static final String DL_COLUMN_EMPTY_VAL = "";

    public static final String INSENSITIVES_RULE_CLASS_PATH = "com.ioc.datasupport.util.InsensitivesRule";

    public static final Integer INSENSITIVES_RULE_CHAR_SET = 1;
    public static final Integer INSENSITIVES_RULE_STORAGE = 2;
    public static final String INSENSITIVES_RULE_STORAGE_FRONT = "左";
    public static final String INSENSITIVES_RULE_STORAGE_MIDDLE = "中";
    public static final String INSENSITIVES_RULE_STORAGE_LATER = "右";
}
