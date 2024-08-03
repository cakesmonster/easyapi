package com.cakemonster.easyapi.model.dto;

import com.alibaba.fastjson2.JSON;
import com.cakemonster.easyapi.enumration.ApiInfoStatusEnum;
import com.cakemonster.easyapi.enumration.ApiMethodEnum;
import com.cakemonster.easyapi.enumration.ApiProtocolEnum;
import com.cakemonster.easyapi.enumration.EnvEnum;
import com.cakemonster.easyapi.model.entity.ApiInfoDO;
import lombok.Data;

import java.util.List;

/**
 * ApiInfoDTO
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class ApiInfoDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * api名称
     */
    private String apiName;

    /**
     * api路径
     */
    private String apiPath;

    /**
     * GET/POST/PUT/DELETE
     */
    private ApiMethodEnum apiMethod;

    /**
     * 类型：HTTP0/RPC1
     */
    private ApiProtocolEnum apiProtocol;

    /**
     * 环境test0/pre1/prod2
     */
    private EnvEnum env;

    /**
     * sql模版/dsl
     */
    private String dsl;

    /**
     * 数据集id
     */
    private Long datasetId;

    /**
     * 未发布0/待测试1/已发布2/下线3
     */
    private ApiInfoStatusEnum status;

    /**
     * owner
     */
    private String apiOwner;

    /**
     * 协作者
     */
    private String apiCoordinators;

    /**
     * 描述
     */
    private String apiDesc;

    /**
     * qps
     */
    private Integer qps;

    /**
     * 超时时间,单位ms
     */
    private Integer timeOut;

    /**
     * 缓存时间
     */
    private Integer cacheTime;

    /**
     * 缓存单位
     */
    private String cacheUnit;

    /**
     * 全路径
     */
    private String fullPath;

    /**
     * 目录id
     */
    private Long directoryId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 请求参数
     */
    private List<ApiRequestDTO> apiRequests;

    /**
     * 响应参数
     */
    private List<ApiResponseDTO> apiResponses;

    /**
     * 是否分页
     */
    private Boolean enablePaging;

    public static ApiInfoDO toApiInfoDO(ApiInfoDTO dto) {
        ApiInfoDO apiInfoDO = new ApiInfoDO();
        apiInfoDO.setId(dto.getId());
        apiInfoDO.setApiName(dto.getApiName());
        apiInfoDO.setApiPath(dto.getApiPath());
        apiInfoDO.setApiMethod(dto.getApiMethod().name());
        apiInfoDO.setApiProtocol(dto.getApiProtocol().name());
        apiInfoDO.setEnv(dto.getEnv().name());
        apiInfoDO.setDsl(dto.getDsl());
        apiInfoDO.setDatasetId(dto.getDatasetId());
        apiInfoDO.setStatus(dto.getStatus().getCode());
        apiInfoDO.setApiOwner(dto.getApiOwner());
        apiInfoDO.setApiCoordinators(dto.getApiCoordinators());
        apiInfoDO.setApiDesc(dto.getApiDesc());
        apiInfoDO.setQps(dto.getQps());
        apiInfoDO.setTimeOut(dto.getTimeOut());
        apiInfoDO.setCacheTime(dto.getCacheTime());
        apiInfoDO.setCacheUnit(dto.getCacheUnit());
        apiInfoDO.setFullPath(dto.getFullPath());
        apiInfoDO.setDirectoryId(dto.getDirectoryId());
        apiInfoDO.setAppName(dto.getAppName());
        apiInfoDO.setApiReq(JSON.toJSONString(dto.getApiRequests()));
        apiInfoDO.setApiResp(JSON.toJSONString(dto.getApiResponses()));
        return apiInfoDO;
    }

}
