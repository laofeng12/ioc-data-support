package com.ioc.datasupport.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ROLE")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = -7761186078406835133L;

    /**
     * 角色id
     */
    @TableId("ROLEID")
    private Long roleid;

    /**
     * 系统编码
     */
    @TableField("SYSTEMID")
    private Long systemid;

    /**
     * 角色CODE(暂用于数据权限控制)
     */
    @TableField("ALIAS")
    private String alias;

    /**
     * 角色名称
     */
    @TableField("ROLENAME")
    private String rolename;

    /**
     * 备注
     */
    @TableField("MEMO")
    private String memo;

    /**
     * 是否允许删除
     */
    @TableField("ALLOWDEL")
    private Integer allowdel;

    /**
     * 是否允许编辑
     */
    @TableField("ALLOWEDIT")
    private Integer allowedit;

    /**
     * 是否启用
     */
    @TableField("ENABLED")
    private Integer enabled;

    /**
     * 是否删除
     */
    @TableField("DELETED")
    private Integer deleted;

    /**
     * 创建人id
     */
    @TableField("CREATORID")
    private Long creatorid;

    /**
     * 创建时间
     */
    @TableField("CREATETIME")
    private Date createtime;

    /**
     * 更新人id
     */
    @TableField("UPDATEID")
    private Long updateid;

    /**
     * 更新时间
     */
    @TableField("UPDATETIME")
    private Date updatetime;

    /**
     * 角色类型(1内置角色2普通角色)
     */
    @TableField("ROLE_TYPE")
    private Integer roleType;

    /**
     * 是否内置角色(0否1是)
     */
    @TableField("IS_BUILD_IN")
    private Integer isBuildIn;


    @Override
    protected Serializable pkVal() {
        return this.roleid;
    }

}
