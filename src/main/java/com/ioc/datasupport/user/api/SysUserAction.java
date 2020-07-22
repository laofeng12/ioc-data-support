package com.ioc.datasupport.user.api;


import com.ioc.datasupport.common.PublicConstant;
import com.ioc.datasupport.user.dto.*;
import com.ioc.datasupport.user.service.*;
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

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysResService sysResService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysRoleResService sysRoleResService;

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
    @ApiOperation(value = "机构树", notes = "机构树", nickname = "orgInfos")
    @Security(session = false)
    @RequestMapping(value = "/orgInfos", method = RequestMethod.GET)
    public DataApiResponse<OrgInfo> getOrgInfos(){
        List<OrgInfo> orgs = sysOrgService.getOrgs();
        DataApiResponse<OrgInfo> resp = new DataApiResponse<>();
        resp.setRows(orgs);

        return resp;
    }

    /**
     * 获取角色，给其他平台同步用（一般同步一次即可）
     * 但也存在修改，得通知其他平台同步更新
     */
    @ApiOperation(value = "角色列表", notes = "角色列表", nickname = "roleInfos")
    @Security(session = false)
    @RequestMapping(value = "/roleInfos", method = RequestMethod.GET)
    public DataApiResponse<RoleInfo> getRoleInfos(){
        List<RoleInfo> roleInfos = sysRoleService.getRoleInfos();
        DataApiResponse<RoleInfo> resp = new DataApiResponse<>();
        resp.setRows(roleInfos);

        return resp;
    }

    /**
     * 资源列表
     */
    @ApiOperation(value = "资源树", notes = "资源树", nickname = "resInfos")
    @Security(session = false)
    @RequestMapping(value = "/resInfos", method = RequestMethod.GET)
    public DataApiResponse<ResInfo> getResInfos(){
        List<ResInfo> resInfos = sysResService.getResInfos();
        DataApiResponse<ResInfo> resp = new DataApiResponse<>();
        resp.setRows(resInfos);

        return resp;
    }

    @ApiOperation(value = "用户角色关系信息", notes = "用户角色关系信息", nickname = "userRoleInfos")
    @Security(session = false)
    @RequestMapping(value = "/userRoleInfos", method = RequestMethod.GET)
    public DataApiResponse<UserRoleInfo> getUserRoleInfos(){
        List<UserRoleInfo> userRoleInfos = sysUserRoleService.getUserRoleInfos();
        DataApiResponse<UserRoleInfo> resp = new DataApiResponse<>();
        resp.setRows(userRoleInfos);

        return resp;
    }

    @ApiOperation(value = "角色资源权限信息", notes = "角色资源权限信息", nickname = "roleResInfos")
    @Security(session = false)
    @RequestMapping(value = "/roleResInfos", method = RequestMethod.GET)
    public DataApiResponse<RoleResInfo> getRoleResInfos(){
        List<RoleResInfo> roleResInfos = sysRoleResService.getRoleResInfos();
        DataApiResponse<RoleResInfo> resp = new DataApiResponse<>();
        resp.setRows(roleResInfos);

        return resp;
    }

}
