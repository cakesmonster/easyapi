package com.cakemonster.easyapi.model.dto;

import com.cakemonster.easyapi.enums.ApiRespParamClass;
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

    /**
     * 是否需要计算环比
     */
    private Boolean needCycleCrc;

    /**
     * 是否需要计算同比
     */
    private Boolean needSyncCrc;
}
