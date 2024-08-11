package com.cakemonster.easyapi.model.dto;

import com.cakemonster.easyapi.enums.ApiParamType;
import com.cakemonster.easyapi.enums.ApiReqParamClass;
import lombok.Data;

/**
 * ApiRequestDTO
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class ApiRequestDTO {

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数类型
     */
    private ApiReqParamClass paramClass;

    /**
     * 通用/函数/系统
     */
    private ApiParamType paramType;

    /**
     * 参数位置
     */
    private String paramLocal;

    /**
     * 函数表达式
     */
    private String funcExpression;

    /**
     * 示例值
     */
    private String exampleValue;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 描述
     */
    private String desc;

}
