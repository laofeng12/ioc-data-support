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
 * 用户角色关系
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_USER_ROLE")
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 7322412367628439422L;

    /**
     * 用户角色关系id
     */
    @TableId("USERROLEID")
    private Long userroleid;

    /**
     * 角色ID
     */
    @TableField("ROLEID")
    private Long roleid;

    /**
     * 用户id
     */
    @TableField("USERID")
    private Long userid;

    /**
     * 是否删除
     */
    @TableField("ISDELETE")
    private Integer isdelete;


    @Override
    protected Serializable pkVal() {
        return this.userroleid;
    }

}
