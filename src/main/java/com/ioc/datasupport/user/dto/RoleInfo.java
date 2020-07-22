package com.ioc.datasupport.user.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ioc.datasupport.user.domain.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lsw
 * @Date: 2020/7/22 10:54
 */
@ApiModel("角色信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色CODE(暂用于数据权限控制)")
    private String alias;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("备注")
    private String memo;

    public RoleInfo(SysRole sysRole) {
        this.roleId = sysRole.getRoleid();
        this.alias = sysRole.getAlias();
        this.roleName = sysRole.getRolename();
        this.memo = sysRole.getMemo();
    }
}
