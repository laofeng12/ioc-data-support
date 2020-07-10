package com.ioc.datasupport.user.dto;

import com.ioc.datasupport.user.domain.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lsw
 * @Date: 2020/7/10 10:27
 */
@ApiModel("用户信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    public UserInfo(SysUser sysUser) {
        this.userId = sysUser.getUserid();
        this.userName = sysUser.getFullname();
        this.account = sysUser.getAccount();
        this.password = sysUser.getPassword();
    }
}
