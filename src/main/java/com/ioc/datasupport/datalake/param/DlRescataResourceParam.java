package com.ioc.datasupport.datalake.param;

import com.ioc.datasupport.common.CommonParam;
import com.ioc.datasupport.datalake.domain.DlRescataResource;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: lsw
 * @Date: 2020/7/10 14:49
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("资源信息")
@Data
public class DlRescataResourceParam extends CommonParam<DlRescataResource> {

}
