package com.cakemonster.easyapi.model.entity;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cakemonster.easyapi.enums.ApiInfoStatusEnum;
import com.cakemonster.easyapi.enums.ApiMethodEnum;
import com.cakemonster.easyapi.enums.ApiProtocolEnum;
import com.cakemonster.easyapi.enums.EnvEnum;
import com.cakemonster.easyapi.model.dto.ApiInfoDTO;
import com.cakemonster.easyapi.model.dto.ApiRequestDTO;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * API信息表(ApiInfo)表实体类
 *
 * @author cakemonster
 * @date 2024-08-04 14:29:41
 */
@Data
@SuppressWarnings("serial")
public class ApiInfoDO extends Model<ApiInfoDO> {

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
    private String apiMethod;

    /**
     * 协议：HTTP/RPC
     */
    private String apiProtocol;

    /**
     * 环境test/pre/prod
     */
    private String env;

    /**
     * sql模版/dsl
     */
    private String dsl;

    /**
     * 是否允许分页
     */
    private Integer enablePaging;

    /**
     * api请求参数
     */
    private String apiReq;

    /**
     * api响应参数
     */
    private String apiResp;

    /**
     * 数据集id
     */
    private Long datasetId;

    /**
     * 未发布0/待测试1/已发布2/下线3
     */
    private Integer status;

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
     * 创建人
     */
    private String creator;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModify;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }

    public static ApiInfoDTO doToDto(ApiInfoDO apiInfoDO) {
        ApiInfoDTO apiInfoDTO = new ApiInfoDTO();
        apiInfoDTO.setId(apiInfoDO.getId());
        apiInfoDTO.setApiName(apiInfoDO.getApiName());
        apiInfoDTO.setApiPath(apiInfoDO.getApiPath());
        apiInfoDTO.setApiMethod(ApiMethodEnum.valueOf(apiInfoDO.getApiMethod()));
        apiInfoDTO.setApiProtocol(ApiProtocolEnum.valueOf(apiInfoDO.getApiProtocol()));
        apiInfoDTO.setEnv(EnvEnum.valueOf(apiInfoDO.getEnv()));
        apiInfoDTO.setDsl(apiInfoDO.getDsl());
        apiInfoDTO.setEnablePaging(apiInfoDO.getEnablePaging() == 0);
        apiInfoDTO.setDatasetId(apiInfoDO.getDatasetId());
        apiInfoDTO.setStatus(ApiInfoStatusEnum.ofValue(apiInfoDO.getStatus()));
        apiInfoDTO.setApiOwner(apiInfoDO.getApiOwner());
        apiInfoDTO.setApiCoordinators(apiInfoDO.getApiCoordinators());
        apiInfoDTO.setApiDesc(apiInfoDO.getApiDesc());
        apiInfoDTO.setQps(apiInfoDO.getQps());
        apiInfoDTO.setTimeOut(apiInfoDO.getTimeOut());
        apiInfoDTO.setCacheTime(apiInfoDO.getCacheTime());
        apiInfoDTO.setCacheUnit(apiInfoDO.getCacheUnit());
        apiInfoDTO.setFullPath(apiInfoDO.getFullPath());
        apiInfoDTO.setDirectoryId(apiInfoDO.getDirectoryId());
        apiInfoDTO.setAppName(apiInfoDO.getAppName());
        apiInfoDTO.setApiRequests(JSON.parseArray(apiInfoDO.getApiReq(), ApiRequestDTO.class));
        apiInfoDTO.setApiResponses(JSON.parseArray(apiInfoDO.getApiResp(), ApiResponseDTO.class));
        return apiInfoDTO;
    }
}

