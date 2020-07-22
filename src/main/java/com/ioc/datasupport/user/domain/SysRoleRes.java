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
 * 角色资源权限表
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("SYS_ROLE_RES")
public class SysRoleRes extends Model<SysRoleRes> {

    private static final long serialVersionUID = -1694521651483270943L;

    /**
     * 角色资源权限id
     */
    @TableId("ROLERESID")
    private Long roleresid;

    /**
     * 角色id
     */
    @TableField("ROLEID")
    private Long roleid;

    /**
     * 资源id
     */
    @TableField("RESID")
    private Long resid;

    /**
     * 系统编码
     */
    @TableField("SYSTEMID")
    private Long systemid;


    @Override
    protected Serializable pkVal() {
        return this.roleresid;
    }

}
