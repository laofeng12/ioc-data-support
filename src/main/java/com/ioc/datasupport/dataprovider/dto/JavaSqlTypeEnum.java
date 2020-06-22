package com.ioc.datasupport.dataprovider.dto;

import lombok.Getter;

import java.sql.Types;

/**
 * @author: lsw
 * @Date: 2019/8/19 16:54
 */
@Getter
public enum JavaSqlTypeEnum implements CodeEnum {
    /** CHAR */
    CHAR(Types.CHAR, "CHAR"),

    /** NCHAR */
    NCHAR(Types.NCHAR, "NCHAR"),

    /** VARCHAR */
    VARCHAR(Types.VARCHAR, "VARCHAR"),

    /** LONGVARCHAR */
    LONGVARCHAR(Types.LONGVARCHAR, "LONGVARCHAR"),

    /** NVARCHAR */
    NVARCHAR(Types.NVARCHAR, "NVARCHAR"),

    /** LONGNVARCHAR */
    LONGNVARCHAR(Types.LONGNVARCHAR, "LONGNVARCHAR"),

    /** CLOB */
    CLOB(Types.CLOB, "CLOB"),

    /** NCLOB */
    NCLOB(Types.NCLOB, "NCLOB"),

    /** SMALLINT */
    SMALLINT(Types.SMALLINT, "SMALLINT"),

    /** TINYINT */
    TINYINT(Types.TINYINT, "TINYINT"),

    /** INTEGER */
    INTEGER(Types.INTEGER, "INTEGER"),

    /** BIGINT */
    BIGINT(Types.BIGINT, "BIGINT"),

    /** NUMERIC */
    NUMERIC(Types.NUMERIC, "NUMERIC"),

    /** DECIMAL */
    DECIMAL(Types.DECIMAL, "DECIMAL"),

    /** FLOAT */
    FLOAT(Types.FLOAT, "FLOAT"),

    /** REAL */
    REAL(Types.REAL, "REAL"),

    /** DOUBLE */
    DOUBLE(Types.DOUBLE, "DOUBLE"),

    /** TIME */
    TIME(Types.TIME, "TIME"),

    /** DATE */
    DATE(Types.DATE, "DATE"),

    /** TIMESTAMP */
    TIMESTAMP(Types.TIMESTAMP, "TIMESTAMP"),

    /** BINARY */
    BINARY(Types.BINARY, "BINARY"),

    /** VARBINARY */
    VARBINARY(Types.VARBINARY, "VARBINARY"),

    /** BLOB */
    BLOB(Types.BLOB, "BLOB"),

    /** LONGVARBINARY */
    LONGVARBINARY(Types.LONGVARBINARY, "LONGVARBINARY"),

    /** BOOLEAN */
    BOOLEAN(Types.BOOLEAN, "BOOLEAN"),

    /** BIT */
    BIT(Types.BIT, "BIT"),

    /** NULL */
    NULL(Types.NULL, "NULL"),

    /** TIME_WITH_TIMEZONE */
    TIME_WITH_TIMEZONE(Types.TIME_WITH_TIMEZONE, "TIME_WITH_TIMEZONE"),

    /** TIMESTAMP_WITH_TIMEZONE */
    TIMESTAMP_WITH_TIMEZONE(Types.TIMESTAMP_WITH_TIMEZONE, "TIMESTAMP_WITH_TIMEZONE");

    /** 编码 */
    private Integer code;
    /** 名称 */
    private String name;

    JavaSqlTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Object getCode() {
        return this.code;
    }

    @Override
    public Object getName() {
        return this.name;
    }
}
