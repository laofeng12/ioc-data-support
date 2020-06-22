package com.ioc.datasupport.util;

import com.ioc.datasupport.common.ExceptionConstants;
import org.apache.commons.lang3.StringUtils;
import org.ljdp.component.exception.APIException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 参数校验工具
 *
 * @author: lsw
 * @Date: 2019/8/19 9:43
 */
public final class ValidateUtil {

    private ValidateUtil(){}

    /**
     * 校验真
     *
     * @param expression    表达式
     * @param message       提示信息
     * @throws APIException 异常
     */
    public static void isTrue(boolean expression, String message) throws APIException {
        if (!expression) {
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR, message);
        }
    }

    /**
     * 校验不为null
     *
     * @param object        对象
     * @param message       提示信息
     * @throws APIException 异常
     */
    public static void notNull(Object object, String message) throws APIException {
        if (object == null) {
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR, message);
        }
    }

    /**
     * 校验集合不为空
     *
     * @param collection    集合
     * @param message       提示信息
     * @throws APIException 异常
     */
    public static void notEmpty(Collection collection, String message) throws APIException {
        if (CollectionUtils.isEmpty(collection)) {
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR, message);
        }
    }

    /**
     * 校验字符串不能为null且不能为""
     *
     * @param str           字符串
     * @param message       提示信息
     * @throws APIException 异常
     */
    public static void strNotBlank(String str, String message) throws APIException {
        if (StringUtils.isBlank(str)) {
            throw new APIException(ExceptionConstants.EXCEPTION_ERROR, message);
        }
    }
}
