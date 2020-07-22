package com.ioc.datasupport.util;

import com.ioc.datasupport.common.PublicConstant;
import com.ioc.datasupport.datalake.domain.DlInsensitivesRule;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.*;

/**
 * @Author xjd
 * @Date 2019/7/3 18:08
 * @Version 1.0
 */
public class InsensitivesUtil {

    private static Object invokeInsensitivesMethod(String className, String methodName, Object[] params) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // 不必每次都反射，缓存下，不然数据一多，浪费很大 TODO
        Class<?> clazz = Class.forName(className);
        Class[] paramsClass = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            paramsClass[i] = params[i].getClass();
        }
        Method declaredMethod = clazz.getDeclaredMethod(methodName, paramsClass);

        return declaredMethod.invoke(clazz, params);
    }

    /**
     * 传参有错就返回一个空的，保证数据安全
     * @param ruleInfo
     * @param columnValue
     * @return
     */
    public static Object insensitiveColumn(DlInsensitivesRule ruleInfo, String columnValue){
        Integer ruleType = ruleInfo.getRuleType();
        String ruleClassPath = ruleInfo.getRuleClassPath();
        String ruleMethodName = ruleInfo.getRuleMethodName();
        String[] params;
        try {
            if (PublicConstant.INSENSITIVES_RULE_CHAR_SET.equals(ruleType)) {
                params = new String[]{columnValue};
            } else {
                params = columnValue.split(",");
            }
            return invokeInsensitivesMethod(ruleClassPath, ruleMethodName, params);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return PublicConstant.DL_COLUMN_EMPTY_VAL;
    }

    public static void main(String[] args) {
        String columnValue = "a";
        String ruleParamsValue = "b,c";
        String join = StringUtils.join(columnValue, ",", ruleParamsValue);
        System.out.println(join);
        String className = PublicConstant.INSENSITIVES_RULE_CLASS_PATH;
        String methodName = "protectMiddle";
        String parms = "protectIdCard,4,10";
        String[] split = parms.split(",");
        long startIndex = 4;
        long endIndex = 10;
        try {
            Object protectIdCard = invokeInsensitivesMethod(className, methodName, split);
            System.out.println(protectIdCard);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
