package com.ioc.datasupport.user.api;


import com.ioc.datasupport.common.PublicConstant;
import com.ioc.datasupport.user.dto.OrgInfo;
import com.ioc.datasupport.user.dto.UserInfo;
import com.ioc.datasupport.user.service.SysOrgService;
import com.ioc.datasupport.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ljdp.component.result.DataApiResponse;
import org.ljdp.secure.annotation.Security;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统用户表
 * 用来做用户数据等对接
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Api(tags = "用户")
@RestController
@RequestMapping(PublicConstant.URL_V1  + "user")
public class SysUserAction {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysOrgService sysOrgService;

    /**
     * 获取用户信息，给其他平台同步用（由于OA系统的存在，所以可能同步频率较高）
     * 后期可能整合组织结构，一起同步？
     */
    @ApiOperation(value = "用户列表", notes = "用户列表", nickname = "userInfos")
    @Security(session = false)
    @RequestMapping(value = "/userInfos", method = RequestMethod.GET)
    public DataApiResponse<UserInfo> getUserInfos(){
        List<UserInfo> users = sysUserService.getUsers();
        DataApiResponse<UserInfo> resp = new DataApiResponse<>();
        resp.setRows(users);

        return resp;
    }

    /**
     * 获取组织机构信息，给其他平台同步用（一般同步一次即可）
     */
    @ApiOperation(value = "机构列表", notes = "机构列表", nickname = "orgInfos")
    @Security(session = false)
    @RequestMapping(value = "/orgInfos", method = RequestMethod.GET)
    public DataApiResponse<OrgInfo> getOrgInfos(){
        List<OrgInfo> orgs = sysOrgService.getOrgs();
        DataApiResponse<OrgInfo> resp = new DataApiResponse<>();
        resp.setRows(orgs);

        return resp;
    }
}
