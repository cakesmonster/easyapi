package com.cakemonster.easyapi.dsl.model;

import com.cakemonster.easyapi.enums.ApiParamClassEnum;
import com.cakemonster.easyapi.enums.ApiParamType;
import lombok.Data;

/**
 * Column
 *
 * @author cakemonster
 * @date 2024/8/4
 */
@Data
public class Column {
    /**
     * 字段名称
     */
    private String columnName;
    /**
     * 别名
     */
    private String columnAlias;
    /**
     * 值
     */
    private Object value;
    /**
     * 类型
     */
    private ApiParamClassEnum columnClass;
    /**
     * common普通字段/function函数字段
     */
    private ApiParamType columnType;
    /**
     * 函数表达式 SUM(%s)
     */
    private String funcExpression;
    /**
     * 字段描述
     */
    private String columnComment;

}
