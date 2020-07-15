package com.ioc.datasupport.warehouse.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源目录结构表
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DL_RESCATA_COLUMN")
public class DlRescataColumn extends Model<DlRescataColumn> {

    private static final long serialVersionUID = 1L;

    /**
     * 资源目录结构表ID
     */
    @TableId("STRUCTURE_ID")
    private Long structureId;

    /**
     * 资源目录宽表ID
     */
    @TableField("RESOURCE_ID")
    private Long resourceId;

    /**
     * 字段名称
     */
    @TableField("COLUMN_NAME")
    private String columnName;

    /**
     * 字段注释
     */
    @TableField("COLUMN_COMMENT")
    private String columnComment;

    /**
     * 字段公开属性（dl.column.open.scope）（1对外公开、2对内公开、3不公开）
     */
    @TableField("COLUMN_OPEN_SCOPE")
    private Integer columnOpenScope;

    /**
     * 字段定义
     */
    @TableField("COLUMN_DEFINITION")
    private String columnDefinition;

    /**
     * 数据类型（dl.column.datatype）（1短字符、2较长字符、3长字符、4日期型、5整数型、6小数型）
     */
    @TableField("DATA_TYPE")
    private Integer dataType;

    /**
     * 字段长度
     */
    @TableField("COLUMN_LENGTH")
    private Long columnLength;

    /**
     * 小数位数
     */
    @TableField("DECIMAL_LENGTH")
    private Integer decimalLength;

    /**
     * 计量单位
     */
    @TableField("MEASUREMENT_UNIT")
    private String measurementUnit;

    /**
     * 数据格式
     */
    @TableField("DATA_FORMAT")
    private String dataFormat;

    /**
     * 质量规则
     */
    @TableField("QUALITY_RULE")
    private String qualityRule;

    /**
     * 是否必填
     */
    @TableField("IS_REQUIRE")
    private Integer isRequire;

    /**
     * 是否加密
     */
    @TableField("IS_ENCRYPT")
    private Integer isEncrypt;

    /**
     * 是否脱敏
     */
    @TableField("IS_DESENSITIZATION")
    private Integer isDesensitization;

    /**
     * 脱敏规则ID
     */
    @TableField("INSENSITIVES_RULE_ID")
    private Long insensitivesRuleId;

    /**
     * 是否主键（1是、0否）
     */
    @TableField("IS_PRIMARY_KEY")
    private Integer isPrimaryKey;

    /**
     * 脱敏规则
     */
    @TableField("DESENSITIZATION_RULE")
    private String desensitizationRule;

    /**
     * 是否用于列表
     */
    @TableField("IS_LIST")
    private Integer isList;

    /**
     * 是否用于查询
     */
    @TableField("IS_QUERY")
    private Integer isQuery;

    /**
     * 是否用于导出
     */
    @TableField("IS_EXPORT")
    private Integer isExport;

    /**
     * 是否用于查看
     */
    @TableField("IS_SHOW")
    private Integer isShow;

    /**
     * 填写方式
     */
    @TableField("WRITE_TYPE")
    private String writeType;

    /**
     * 代码集
     */
    @TableField("CODE_SET")
    private String codeSet;

    /**
     * 默认值
     */
    @TableField("DEFAULT_VALUE")
    private String defaultValue;

    /**
     * 信息项说明
     */
    @TableField("INFO_DESCRIPTION")
    private String infoDescription;

    /**
     * 状态（public.YN）（1有效、0无效）
     */
    @TableField("IS_DELETED")
    private Integer isDeleted;

    /**
     * 签名值
     */
    @TableField("SIGN")
    private String sign;

    /**
     * 脱敏开始与结束位置
     */
    @TableField("INSENSITIVES_START_END")
    private String insensitivesStartEnd;

    /**
     * 上一版本资源目录结构表ID
     */
    @TableField("OLD_STRUCTURE_ID")
    private Long oldStructureId;

    /**
     * 字段Code
     */
    @TableField("COLUMN_CODE")
    private Long columnCode;

    /**
     * 脱敏说明
     */
    @TableField("INSENSITIVES_RULE_DESCRIPTION")
    private String insensitivesRuleDescription;

    /**
     * 加密说明
     */
    @TableField("ENCRYPT_DESCRIPTION")
    private String encryptDescription;


    @Override
    protected Serializable pkVal() {
        return this.structureId;
    }

}
