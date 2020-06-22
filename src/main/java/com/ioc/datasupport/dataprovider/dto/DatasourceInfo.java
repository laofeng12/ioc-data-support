package com.ioc.datasupport.dataprovider.dto;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ioc.datasupport.warehouse.domain.DlRescataDatabase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@ApiModel("数据源")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString
public class DatasourceInfo {
    @ApiModelProperty(value = "数据库类型（1:hive 2:PostgreSql）",required = true)
    @Max(99L)
    @NotNull
    private Integer databaseType;

    @ApiModelProperty("REPOSITORY_TYPE 分库类别（dl.resource.repository.type）（1归集库、2中心库、3基础库、4主题库、5专题库）")
    private Integer repositoryType;

    @ApiModelProperty("jdbc连接参数")
    @Length(min=0, max=512)
    private String jdbcParams;

    @ApiModelProperty(value = "主机IP",required = true)
    @Length(min=0, max=512)
    @NotBlank
    private String hostIp;

    @ApiModelProperty(value = "数据库名",required = true)
    @Length(min=0, max=32)
    @NotBlank
    private String databaseName;

    @ApiModelProperty("模式名称(postgres)")
    @Length(min=0, max=32)
    private String schemaName;

    @ApiModelProperty(value = "端口号",required = true)
    @Max(99999L)
    @NotNull
    private Long port;

    @ApiModelProperty(value = "用户名",required = true)
    @Length(min=0, max=128)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @Length(min=0, max=128)
    private String password;

    @ApiModelProperty(value = "是否持久连接")
    private Boolean pooled = false;

    @ApiModelProperty(value = "jdbc连接")
    private String jdbcUrl;

    public DatasourceInfo(DlRescataDatabase dlRescataDatabase) {
        this(dlRescataDatabase, false);
    }

    public DatasourceInfo(DlRescataDatabase dlRescataDatabase, Boolean pooled) {
        String dbJsonInfo = dlRescataDatabase.getDatabaseJsonInfo();
        JSONObject jsonObject = JSONObject.parseObject(dbJsonInfo);
        this.databaseName = jsonObject.getString("databaseName");
        this.hostIp = jsonObject.getString("ip");
        this.port = Long.parseLong(jsonObject.getString("port"));
        this.username = jsonObject.getString("username");
        this.password = jsonObject.getString("password");
        this.schemaName = jsonObject.getString("schema");

        this.databaseType = dlRescataDatabase.getDatabaseType();
        this.repositoryType = dlRescataDatabase.getRepositoryType();
        this.pooled = pooled;
    }
}
