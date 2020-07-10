package com.ioc.datasupport.user.param;

import com.ioc.datasupport.common.CommonParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: lsw
 * @Date: 2020/7/10 10:30
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户数据")
@Data
public class SysUserParam<T> extends CommonParam<T> {
}
