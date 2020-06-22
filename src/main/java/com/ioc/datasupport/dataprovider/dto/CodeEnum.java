package com.ioc.datasupport.dataprovider.dto;

/**
 * @author: lsw
 * @Date: 2019/8/19 16:44
 */
public interface CodeEnum<T> {
    T getCode();

    T getName();
}
