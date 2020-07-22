package com.ioc.datasupport.user.domain;

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
 * 菜单资源表
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_RES")
public class SysRes extends Model<SysRes> {

    private static final long serialVersionUID = -3582130548004030049L;

    /**
     * 资源ID
     */
    @TableId("RESID")
    private Long resid;

    /**
     * 资源名称
     */
    @TableField("RESNAME")
    private String resname;

    /**
     * 资源别名
     */
    @TableField("ALIAS")
    private String alias;

    @TableField("SN")
    private Long sn;

    /**
     * 图标
     */
    @TableField("ICON")
    private String icon;

    /**
     * 父资源id
     */
    @TableField("PARENTID")
    private Long parentid;

    /**
     * 默认URL
     */
    @TableField("DEFAULTURL")
    private String defaulturl;

    /**
     * 可否展开
     */
    @TableField("ISFOLDER")
    private Integer isfolder;

    /**
     * 是否默认菜单
     */
    @TableField("ISDISPLAYINMENU")
    private Integer isdisplayinmenu;

    /**
     * 是否打开
     */
    @TableField("ISOPEN")
    private Integer isopen;

    /**
     * 系统编码
     */
    @TableField("SYSTEMID")
    private Long systemid;

    /**
     * 完整路径
     */
    @TableField("PATH")
    private String path;

    /**
     * 排序
     */
    @TableField("SORT")
    private Integer sort;

    /**
     * 是否删除
     */
    @TableField("DELETED")
    private Integer deleted;

    @TableField("MODULE_ID")
    private Long moduleId;


    @Override
    protected Serializable pkVal() {
        return this.resid;
    }

}
