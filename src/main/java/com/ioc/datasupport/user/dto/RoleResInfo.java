package com.ioc.datasupport.user.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ioc.datasupport.user.domain.SysRoleRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lsw
 * @Date: 2020/7/22 11:29
 */
@ApiModel("角色资源权限信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResInfo {

    @ApiModelProperty("角色资源权限id")
    private Long roleResId;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("资源id")
    private Long resId;

    public RoleResInfo(SysRoleRes sysRoleRes) {
        this.roleResId = sysRoleRes.getRoleresid();
        this.roleId = sysRoleRes.getRoleid();
        this.resId = sysRoleRes.getResid();
    }

}
