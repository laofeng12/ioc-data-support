package com.ioc.datasupport.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.CaseFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lsw
 * @Date: 2020/6/5 18:23
 */
public class CommonParam<T> {

    @ApiModelProperty("排序(id ASC,createTime DESC)")
    protected String sort;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public QueryWrapper<T> genQueryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        Class<?> clazz = this.getClass();
        //获取查询类Query的所有字段,包括父类字段
        List<Field> fields = getAllFieldsWithRoot(clazz);
        if (CollectionUtils.isEmpty(fields)) {
            return queryWrapper;
        }

        for (Field field : fields) {
            // 设置为可访问
            field.setAccessible(true);
            // 字段值
            Object value = null;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null) {
                // 若字段值为空，不处理
                continue;
            }

            // 属性名
            String fieldName = field.getName();
            // 处理operator
            handleOperator(queryWrapper, fieldName, value);
        }

        // 处理sort
        handleSort(queryWrapper);

        return queryWrapper;
    }

    /**
     * 处理运算符
     *
     * @param queryWrapper
     * @param fieldName
     * @param value
     */
    private void handleOperator(QueryWrapper<T> queryWrapper, String fieldName, Object value) {
        String[] fileArr = StringUtils.split(fieldName, "_");
        if (fileArr.length != 2) {
            return;
        }

        // 运算符
        String operator = fileArr[0];
        // 字段名（驼峰格式：catName）
        String column = fileArr[1];
        column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);

        switch (operator) {
            case "eq":
                // 等于
                queryWrapper.eq(column, value);
                break;
            case "ne":
                // 不等于
                queryWrapper.ne(column, value);
                break;
            case "le":
                // 小于
                queryWrapper.le(column, value);
                break;
            case "lt":
                // 小于等于
                queryWrapper.lt(column, value);
                break;
            case "ge":
                // 大于
                queryWrapper.ge(column, value);
                break;
            case "gt":
                // 大于等于
                queryWrapper.gt(column, value);
                break;
            case "like":
                // 模糊匹配like
                queryWrapper.like(column, "%" + value + "%");
                break;
            case "nlike":
                // 模糊匹配not like
                queryWrapper.notLike(column, "%" + value + "%");
                break;
            case "in":
                queryWrapper.in(column, (List) value);
                break;
            case "nin":
                // 不包含
                queryWrapper.notIn(column, (List) value);
                break;
            case "null":
                boolean isNull = Boolean.parseBoolean(value.toString());
                if (isNull) {
                    queryWrapper.isNull(column);
                } else {
                    queryWrapper.isNotNull(column);
                }
            default:
                break;
        }
    }

    /**
     * 处理排序
     *
     * @param queryWrapper
     */
    private void handleSort(QueryWrapper<T> queryWrapper) {
        if (StringUtils.isBlank(this.sort)) {
            return;
        }

        String[] split = StringUtils.split(this.sort, ",");
        if (split.length < 1) {
            return;
        }

        for (String item : split) {
            if (StringUtils.isBlank(item)) {
                continue;
            }

            // 如：time asc
            String[] sortItem = StringUtils.split(item, " ");
            if (sortItem.length != 2) {
                continue;
            }

            String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortItem[0]);
            String rule = StringUtils.upperCase(sortItem[1]);
            if ("ASC".equals(rule)) {
                queryWrapper.orderByAsc(column);
            }
            if ("DESC".equals(rule)) {
                queryWrapper.orderByDesc(column);
            }
        }
    }

    /**
     * 获取类clazz的所有Field，包括其父类的Field
     */
    private List<Field> getAllFieldsWithRoot(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        //获取本类所有字段
        Field[] dFields = clazz.getDeclaredFields();
        if (dFields.length > 0) {
            fieldList.addAll(Arrays.asList(dFields));
        }

        // 若父类是Object，则直接返回当前Field列表
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == Object.class) {
            return Arrays.asList(dFields);
        }

        // 递归查询父类的field列表
        List<Field> superFields = getAllFieldsWithRoot(superClass);
        if (!superFields.isEmpty()) {
            //不重复字段
            superFields.stream().
                    filter(field -> !fieldList.contains(field)).
                    forEach(fieldList::add);
        }

        return fieldList;
    }
}
