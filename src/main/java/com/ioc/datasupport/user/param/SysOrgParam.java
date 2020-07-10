package com.ioc.datasupport.user.param;

import com.ioc.datasupport.common.CommonParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: lsw
 * @Date: 2020/7/10 10:50
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("机构数据")
@Data
public class SysOrgParam<T> extends CommonParam<T> {



}
