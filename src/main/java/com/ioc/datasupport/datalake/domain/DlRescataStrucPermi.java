package com.ioc.datasupport.datalake.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资源目录字段权限表
 * </p>
 *
 * @author lsw
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DL_RESCATA_STRUC_PERMI")
public class DlRescataStrucPermi extends Model<DlRescataStrucPermi> {

    private static final long serialVersionUID = 1L;

    /**
     * 字段权限表ID
     */
    @TableId("COLUMN_PERMISSION_ID")
    private Long columnPermissionId;

    /**
     * 订阅申请表ID
     */
    @TableField("SUBSCRIBE_FORM_ID")
    private Long subscribeFormId;

    /**
     * 局委办部门ID
     */
    @TableField("OWNER_DEPT_ID")
    private String ownerDeptId;

    /**
     * 局委办顶级部门ID
     */
    @TableField("OWNER_DEPT_TOP_ID")
    private String ownerDeptTopId;

    /**
     * 申请人账号
     */
    @TableField("OWNER_ACCOUNT")
    private String ownerAccount;

    /**
     * 申请人账号UUID
     */
    @TableField("OWNER_UUID")
    private String ownerUuid;

    /**
     * 信息资源编码
     */
    @TableField("RESOURCE_ID")
    private Long resourceId;

    /**
     * 资源目录结构表ID
     */
    @TableField("STRUCTURE_ID")
    private Long structureId;

    /**
     * 是否不用加密
     */
    @TableField("IS_DECRYPTION")
    private Integer isDecryption;

    /**
     * 是否不用脱敏
     */
    @TableField("IS_SENSITIVED")
    private Integer isSensitived;

    /**
     * 资源目录标识
     */
    @TableField("RESOURCE_TABLE_NAME")
    private String resourceTableName;

    /**
     * 资源目录版本号
     */
    @TableField("RESOURCE_VERSION")
    private Long resourceVersion;

    /**
     * 权限获取时间
     */
    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 信息资源编码
     */
    @TableField("RESOURCE_CODE")
    private String resourceCode;

    /**
     * 局委办顶级部门名称
     */
    @TableField("OWNER_DEPT_TOP_NAME")
    private String ownerDeptTopName;

    /**
     * 局委办部门名称
     */
    @TableField("OWNER_DEPT_NAME")
    private String ownerDeptName;


    @Override
    protected Serializable pkVal() {
        return this.columnPermissionId;
    }

}
