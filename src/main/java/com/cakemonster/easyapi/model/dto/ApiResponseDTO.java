package com.cakemonster.easyapi.model.dto;

import com.cakemonster.easyapi.enumration.ApiRespParamClass;
import lombok.Data;

/**
 * ApiResponseDTO
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class ApiResponseDTO {

    /**
     * 参数名
     */
    private String paramName;
    /**
     * 参数类型
     */
    private ApiRespParamClass paramClass;
    /**
     * 示例值
     */
    private String exampleValue;
    /**
     * 描述
     */
    private String desc;
}
