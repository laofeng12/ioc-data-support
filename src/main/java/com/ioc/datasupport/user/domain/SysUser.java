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
 * 系统用户表
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId("USERID")
    private Long userid;

    /**
     * 名称（OA-username） 
     */
    @TableField("FULLNAME")
    private String fullname;

    /**
     * 帐号类型(SYS.AccountType)
     */
    @TableField("ACCOUNTTYPE")
    private Integer accounttype;

    /**
     * 登录账号(OA-cn)
     */
    @TableField("ACCOUNT")
    private String account;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 是否过期
     */
    @TableField("ISEXPIRED")
    private Integer isexpired;

    /**
     * 是否锁定
     */
    @TableField("ISLOCK")
    private Integer islock;

    /**
     * 创建时间
     */
    @TableField("CREATETIME")
    private Date createtime;

    /**
     * 状态
     */
    @TableField("STATUS")
    private Integer status;

    /**
     * 邮箱(OA-email)
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 手机号码(OA-mobile)
     */
    @TableField("MOBILE")
    private String mobile;

    /**
     * 电话
     */
    @TableField("PHONE")
    private String phone;

    /**
     * 性别
     */
    @TableField("SEX")
    private String sex;

    /**
     * 头像
     */
    @TableField("PICTURE")
    private String picture;

    /**
     * 来源类型
     */
    @TableField("FROMTYPE")
    private Integer fromtype;

    /**
     * OA-单位组织机构代码(zzjgdm)
     */
    @TableField("ORGCODE")
    private String orgcode;

    /**
     * OA-统一认证平台中机构编码(deptCode)
     */
    @TableField("DEPTCODE")
    private String deptcode;

    /**
     * OA-用户(uid)
     */
    @TableField("OARELATIONID")
    private String oarelationid;

    /**
     * OA-部门ID(deptid)
     */
    @TableField("DEPTID")
    private String deptid;

    /**
     * OA-机构名(orgname)
     */
    @TableField("ORGNAME")
    private String orgname;

    /**
     * OA-所在单位科室(level0)
     */
    @TableField("LEVEL0")
    private String level0;

    /**
     * OA-所在单位(level1)
     */
    @TableField("LEVEL1")
    private String level1;

    /**
     * OA-用户更新时间(updatetime)
     */
    @TableField("UPDATETIME")
    private Date updatetime;

    /**
     * OA-组织机构ID(orgid)
     */
    @TableField("ORGID")
    private String orgid;

    @TableField("IS_RESET_PWD")
    private Integer isResetPwd;

    @TableField("LOCK_EXPIRE_TIME")
    private Date lockExpireTime;

    @TableField("IDENTITY_KEY")
    private String identityKey;

    /**
     * 是否是内置用户（0否、1是）
     */
    @TableField("IS_BUILD_IN")
    private Integer isBuildIn;


    @Override
    protected Serializable pkVal() {
        return this.userid;
    }

}
