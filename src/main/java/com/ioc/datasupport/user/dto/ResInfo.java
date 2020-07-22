package com.ioc.datasupport.user.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.common.collect.Lists;
import com.ioc.datasupport.user.domain.SysRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: lsw
 * @Date: 2020/7/22 11:51
 */
@ApiModel("菜单资源信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResInfo {

    @ApiModelProperty("资源ID")
    private Long resId;

    @ApiModelProperty("资源名称")
    private String resName;

    @ApiModelProperty("资源别名")
    private String alias;

    @ApiModelProperty("sn")
    private Long sn;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("父资源id")
    private Long parentId;

    @ApiModelProperty("默认URL")
    private String defaultUrl;

    @ApiModelProperty("可否展开")
    private Integer isFolder;

    @ApiModelProperty("是否默认菜单")
    private Integer isDisplayInMenu;

    @ApiModelProperty("是否打开")
    private Integer isOpen;

    @ApiModelProperty("完整路径")
    private String path;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("子资源列表")
    private List<ResInfo> subResList;

    public ResInfo(SysRes sysRes) {
        this.resId = sysRes.getResid();
        this.resName = sysRes.getResname();
        this.alias = sysRes.getAlias();
        this.sn = sysRes.getSn();
        this.icon = sysRes.getIcon();
        this.parentId = sysRes.getParentid();
        this.defaultUrl = sysRes.getDefaulturl();
        this.isFolder = sysRes.getIsfolder();
        this.isDisplayInMenu = sysRes.getIsdisplayinmenu();
        this.isOpen = sysRes.getIsopen();
        this.path = sysRes.getPath();
        this.sort = sysRes.getSort();
        this.subResList = Lists.newLinkedList();
    }
}
