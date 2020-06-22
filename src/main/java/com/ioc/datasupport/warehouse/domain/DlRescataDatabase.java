package com.ioc.datasupport.warehouse.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lsw
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("DL_RESCATA_DATABASE")
public class DlRescataDatabase extends Model<DlRescataDatabase> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库编码主键ID
     */
    @TableId("DATABASE_ID")
    private Long databaseId;

    /**
     * 数据库名称
     */
    @TableField("DATABASE_NAME")
    private String databaseName;

    /**
     * 数据库链接JSON信息
     */
    @TableField("DATABASE_JSON_INFO")
    private String databaseJsonInfo;

    /**
     * 分库类别（dl.resource.repository.type）（1归集库、2中心库、3基础库、4主题库、5专题库）
     */
    @TableField("REPOSITORY_TYPE")
    private Integer repositoryType;

    /**
     * 数据库类型（1HIVE、2MPP）
     */
    @TableField("DATABASE_TYPE")
    private Integer databaseType;


    @Override
    protected Serializable pkVal() {
        return this.databaseId;
    }

}
