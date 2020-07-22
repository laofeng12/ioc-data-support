package com.ioc.datasupport.datalake.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 脱敏规则
 * </p>
 *
 * @author lsw
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DL_INSENSITIVES_RULE")
public class DlInsensitivesRule extends Model<DlInsensitivesRule> {

    private static final long serialVersionUID = -1983614098317252646L;

    /**
     * 脱敏规则ID
     */
    @TableId("INSENSITIVES_RULE_ID")
    private Long insensitivesRuleId;

    /**
     * 规则类型(dl.insensitives.rule.type )（1.字符集;2.规则库）
     */
    @TableField("RULE_TYPE")
    private Integer ruleType;

    /**
     * 规则名称
     */
    @TableField("RULE_NAME")
    private String ruleName;

    /**
     * 规则描述
     */
    @TableField("RULE_DESC")
    private String ruleDesc;

    /**
     * 规则类路径
     */
    @TableField("RULE_CLASS_PATH")
    private String ruleClassPath;

    /**
     * 规则方法名
     */
    @TableField("RULE_METHOD_NAME")
    private String ruleMethodName;

    /**
     * 左/中/右类型
     */
    @TableField("PARAM_TYPE")
    private String paramType;

    /**
     * 正则表达式
     */
    @TableField("REG_EXP")
    private String regExp;

    /**
     * 创建者账号
     */
    @TableField("CREATE_ACCOUNT")
    private String createAccount;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 创建者全名
     */
    @TableField("CREATE_FULLNAME")
    private String createFullname;

    /**
     * 修改者账号
     */
    @TableField("UPDATE_ACCOUNT")
    private String updateAccount;

    /**
     * 修改时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 修改者全名
     */
    @TableField("UPDATE_FULLNAME")
    private String updateFullname;

    /**
     * 状态(public.state)(1有效，0无效)
     */
    @TableField("STATE")
    private Integer state;

    /**
     * 是否删除
     */
    @TableField("IS_DELETE")
    private Integer isDelete;


    @Override
    protected Serializable pkVal() {
        return this.insensitivesRuleId;
    }

}
