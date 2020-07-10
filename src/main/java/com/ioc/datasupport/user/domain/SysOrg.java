package com.ioc.datasupport.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组织机构管理
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ORG")
public class SysOrg extends Model<SysOrg> {

    private static final long serialVersionUID = 1L;

    /**
     * 组织ID
     */
    @TableField("ORGID")
    private String orgid;

    @TableField("DEMID")
    private Long demid;

    /**
     * 组织名称
     */
    @TableField("ORGNAME")
    private String orgname;

    /**
     * 组织描述
     */
    @TableField("ORGDESC")
    private String orgdesc;

    /**
     * 上级组织
     */
    @TableField("ORGSUPID")
    private String orgsupid;

    /**
     * 路径
     */
    @TableField("PATH")
    private String path;

    @TableField("DEPTH")
    private Long depth;

    /**
     * 组织类型
     */
    @TableField("ORGTYPE")
    private String orgtype;

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

    @TableField("SN")
    private Long sn;

    /**
     * 来源
     */
    @TableField("FROMTYPE")
    private Integer fromtype;

    /**
     * 路径全名
     */
    @TableField("ORGPATHNAME")
    private String orgpathname;

    /**
     * OA-机构编码
     */
    @TableField("DEPTCODE")
    private String deptcode;

    /**
     * OA-组织机构代码 
     */
    @TableField("ORGCODE")
    private String orgcode;

    /**
     * OA-统一社会信息代码
     */
    @TableField("USCCODE")
    private String usccode;

    @TableField("ORG_NAME_ALIAS")
    private String orgNameAlias;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
