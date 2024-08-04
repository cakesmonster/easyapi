package com.cakemonster.easyapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cakemonster.easyapi.model.dto.ApiInfoDTO;
import com.cakemonster.easyapi.model.entity.ApiInfoDO;

import java.util.List;

/**
 * API信息表(ApiInfo)表服务接口
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:15
 */
public interface ApiInfoService extends IService<ApiInfoDO> {

    boolean saveApiInfo(String appName, Long userId, ApiInfoDTO apiInfoDTO);

    boolean updateApiInfo(String appName, Long userId, ApiInfoDTO apiInfoDTO);

    boolean deleteApiInfo(String appName, Long userId, Long apiInfoId);

    List<ApiInfoDTO> getApiInfoList(String appName, String directoryId);

    ApiInfoDTO getApiInfoDetail(Long apiInfoId);
}

