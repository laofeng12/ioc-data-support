package com.ioc.datasupport.user.dto;

import com.ioc.datasupport.user.domain.SysOrg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: lsw
 * @Date: 2020/7/10 10:52
 */
@ApiModel("机构信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgInfo {

    @ApiModelProperty("机构ID")
    private String orgId;

    @ApiModelProperty("机构名称")
    private String orgName;

    @ApiModelProperty("父级ID")
    private String pid;

    public OrgInfo(SysOrg sysOrg) {
        this.orgId = sysOrg.getOrgid();
        this.orgName = sysOrg.getOrgname();
        this.pid = sysOrg.getOrgsupid();
    }
}
