package com.ioc.datasupport.user.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ioc.datasupport.user.domain.SysUserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lsw
 * @Date: 2020/7/22 11:42
 */
@ApiModel("用户角色关系信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleInfo {

    @ApiModelProperty("用户角色关系id")
    private Long userRoleId;

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户id")
    private Long userId;

    public UserRoleInfo(SysUserRole sysUserRole) {
        this.userRoleId = sysUserRole.getUserroleid();
        this.roleId = sysUserRole.getRoleid();
        this.userId = sysUserRole.getUserid();
    }
}
