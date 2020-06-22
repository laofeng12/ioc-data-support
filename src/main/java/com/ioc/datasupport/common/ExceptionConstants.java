package com.ioc.datasupport.common;

import org.ljdp.component.result.APIConstants;

/**
 * 异常常量
 *
 * @author: lsw
 * @Date: 2019/8/1 11:00
 */
public class ExceptionConstants extends APIConstants {
    /** 异常错误 */
    public static int EXCEPTION_ERROR = CODE_SERVER_ERR;
    public static String EXCEPTION_ERROR_MSG = "服务异常：";

    public static int REQUEST_ERROR =  CODE_SERVER_ERR;
    public static String REQUEST_ERROR_MSG = "传入参数错误：";
}
