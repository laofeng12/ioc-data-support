package com.ioc.datasupport.warehouse.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源目录宽表
 * </p>
 *
 * @author lsw
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DL_RESCATA_RESOURCE")
public class DlRescataResource extends Model<DlRescataResource> {

    private static final long serialVersionUID = 1L;

    /**
     * 资源目录宽表ID
     */
    @TableId("RESOURCE_ID")
    private Long resourceId;

    /**
     * 信息资源编码
     */
    @TableField("RESOURCE_CODE")
    private String resourceCode;

    /**
     * 源头信息资源编码
     */
    @TableField("SOURCE_INFO_CODE")
    private String sourceInfoCode;

    /**
     * 分库类别（dl.resource.repository.type）（1汇集库、2中心库、3其他）
     */
    @TableField("REPOSITORY_TYPE")
    private Integer repositoryType;

    /**
     * 资源名称
     */
    @TableField("RESOURCE_NAME")
    private String resourceName;

    /**
     * 资源类别（dl.resource.type）（1数据库、2文本、3图片、4音频、5视频）
     */
    @TableField("RESOURCE_TYPE")
    private Integer resourceType;

    /**
     * 存储标识
     */
    @TableField("RESOURCE_TABLE_NAME")
    private String resourceTableName;

    /**
     * 公开范围（dl.resource.open.scope）（1市级目录、2部门级目录、4个人目录）
     */
    @TableField("OPEN_SCOPE")
    private Integer openScope;

    /**
     * 是否只限内部使用（public.YN）（1是、0否）
     */
    @TableField("IS_INTERNAL_USE_ONLY")
    private Integer isInternalUseOnly;

    /**
     * 资源来源
     */
    @TableField("RESOURCE_SOURCE")
    private String resourceSource;

    /**
     * 更新周期（dl.resource.update.cycle）（1实时、2每日、3每周、4每月、5每季度、6每半年、7每年、8一次性、9不提供、10其他）
     */
    @TableField("UPDATE_CYCLE")
    private Integer updateCycle;

    /**
     * 提供时限
     */
    @TableField("PROVIDE_TIME_LIMIT")
    private String provideTimeLimit;

    /**
     * 业务负责人姓名
     */
    @TableField("BUSINESS_OWNER")
    private String businessOwner;

    /**
     * 是否短信提醒（public.YN）（1是，0否）
     */
    @TableField("IS_SMS_ALERTS")
    private Integer isSmsAlerts;

    /**
     * 是否同意在地图上展示（public.YN）（1是、0否）
     */
    @TableField("IS_SHOW_ON_MAP")
    private Integer isShowOnMap;

    /**
     * 资源摘要
     */
    @TableField("RESOURCE_SUMMARY")
    private String resourceSummary;

    /**
     * 关键字
     */
    @TableField("KEYWORD")
    private String keyword;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Long version;

    /**
     * 信息主体（dl.resource.information.agent）（1法人、2自然人、3自然人/法人、4城市部件、5数据信息、6其他）
     */
    @TableField("INFORMATION_AGENT")
    private Integer informationAgent;

    /**
     * 资源发布日期
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 最近数据更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 资源状态（dl.resource.resource.state）（1正常、2无效、3停更、4草稿）
     */
    @TableField("RESOURCE_STATE")
    private Integer resourceState;

    /**
     * 资源安全级别（dl.resource.security.level）（1未分级、2内部、3秘密、4机密、5绝密）
     */
    @TableField("SECURITY_LEVEL")
    private Integer securityLevel;

    /**
     * 数据标签
     */
    @TableField("DATA_LABEL")
    private String dataLabel;

    /**
     * 获取地址
     */
    @TableField("URL")
    private String url;

    /**
     * 是否为最新版（public.YN）（1是、0否）
     */
    @TableField("IS_LATEST")
    private Integer isLatest;

    /**
     * 数据提供方式（dl.resource.data.provide.mode）（1库表挂载、2附件上传、3接口获取、4数据归集）
     */
    @TableField("SOURCE_MODE")
    private Integer sourceMode;

    /**
     * 资源状态变动时间
     */
    @TableField("RESOURCE_STATE_UPDATE_TIME")
    private Date resourceStateUpdateTime;

    /**
     * 数据提供时间
     */
    @TableField("PROVIDE_TIME")
    private Date provideTime;

    /**
     * 禁用时间
     */
    @TableField("DISABLED_TIME")
    private Date disabledTime;

    /**
     * 创建人UUID
     */
    @TableField("CREATE_UUID")
    private Long createUuid;

    /**
     * 创建人账号
     */
    @TableField("CREATE_ACCOUNT")
    private String createAccount;

    /**
     * 创建人名称
     */
    @TableField("CREATE_FULLNAME")
    private String createFullname;

    /**
     * 创建单位名称
     */
    @TableField("CREATE_DEPT_NAME")
    private String createDeptName;

    /**
     * 创建局UUID
     */
    @TableField("CREATE_DEPT_TOP_ID")
    private String createDeptTopId;

    /**
     * 创建局名称
     */
    @TableField("CREATE_DEPT_TOP_NAME")
    private String createDeptTopName;

    /**
     * 提供单位名称
     */
    @TableField("PROVIDE_DEPT_NAME")
    private String provideDeptName;

    /**
     * 创建单位UUID
     */
    @TableField("CREATE_DEPT_ID")
    private String createDeptId;

    /**
     * 提供单位ID
     */
    @TableField("PROVIDE_DEPT_ID")
    private String provideDeptId;

    /**
     * 业务负责人账号
     */
    @TableField("BUSINESS_OWNER_ACCOUNT")
    private String businessOwnerAccount;

    /**
     * 提供局UUID
     */
    @TableField("PROVIDE_DEPT_TOP_ID")
    private String provideDeptTopId;

    /**
     * 提供局名称
     */
    @TableField("PROVIDE_DEPT_TOP_NAME")
    private String provideDeptTopName;

    /**
     * 提供单位全路径
     */
    @TableField("PROVIDE_DEPT_FULL_PATH")
    private String provideDeptFullPath;

    /**
     * 是否站内消息通知（public.YN）（1是，0否）
     */
    @TableField("IS_IN_STATION_MSG_NOTIFY")
    private Integer isInStationMsgNotify;

    /**
     * 数据领域（dl.resource.data.domain）
     */
    @TableField("DATA_DOMAIN")
    private Integer dataDomain;

    /**
     * 最近更新时间
     */
    @TableField("DATA_SUBMIT_TIME")
    private Date dataSubmitTime;

    /**
     * 最近一次更新时间
     */
    @TableField("DATA_LAST_SUBMIT_TIME")
    private Date dataLastSubmitTime;

    /**
     * 是否为内部数据源（public.YN）（1是、0否）
     */
    @TableField("IS_INTERNAL_DATA_SOURCE")
    private Integer isInternalDataSource;

    /**
     * 行业分类
     */
    @TableField("INDUSTRY")
    private String industry;

    /**
     * 服务分类
     */
    @TableField("SERVICE")
    private String service;

    /**
     * 资源形态分类
     */
    @TableField("RESOURCE_FORM")
    private String resourceForm;

    /**
     * Excel同步任务Id
     */
    @TableField("TASK_ID1")
    private String taskId1;

    /**
     * 汇聚的同步任务状态
     */
    @TableField("TASK_STATE")
    private Integer taskState;


    @Override
    protected Serializable pkVal() {
        return this.resourceId;
    }

}
