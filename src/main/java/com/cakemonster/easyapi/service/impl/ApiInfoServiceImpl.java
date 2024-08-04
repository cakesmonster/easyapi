package com.cakemonster.easyapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cakemonster.easyapi.dao.ApiInfoMapper;
import com.cakemonster.easyapi.enumration.*;
import com.cakemonster.easyapi.exception.BizException;
import com.cakemonster.easyapi.model.dto.ApiInfoDTO;
import com.cakemonster.easyapi.model.dto.ApiRequestDTO;
import com.cakemonster.easyapi.model.dto.AppInfoDTO;
import com.cakemonster.easyapi.model.entity.ApiInfoDO;
import com.cakemonster.easyapi.service.ApiInfoService;
import com.cakemonster.easyapi.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * API信息表(ApiInfo)表服务实现类
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:15
 */
@Service("apiInfoService")
public class ApiInfoServiceImpl extends ServiceImpl<ApiInfoMapper, ApiInfoDO> implements ApiInfoService {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    @Override
    public boolean saveApiInfo(String appName, Long userId, ApiInfoDTO apiInfoDTO) {
        checkApp(appName);
        // 处理分页
        if (apiInfoDTO.getEnablePaging() && apiInfoDTO.getApiProtocol().equals(ApiProtocolEnum.HTTP)) {
            String paramLocal = apiInfoDTO.getApiMethod().equals(ApiMethodEnum.GET) ? ApiParamLocalEnum.QUERY.name() :
                ApiParamLocalEnum.BODY.name();
            ApiRequestDTO page = buildPage(paramLocal);
            ApiRequestDTO pageSize = buildPageSize(paramLocal);
            apiInfoDTO.getApiRequests().add(page);
            apiInfoDTO.getApiRequests().add(pageSize);
        }

        ApiInfoDO apiInfoDO = ApiInfoDTO.toApiInfoDO(apiInfoDTO);
        apiInfoDO.setAppName(appName);
        return apiInfoMapper.insert(apiInfoDO) == 1;
    }

    @Override
    public boolean updateApiInfo(String appName, Long userId, ApiInfoDTO apiInfoDTO) {
        checkApp(appName);
        ApiInfoDO apiInfoDO = ApiInfoDTO.toApiInfoDO(apiInfoDTO);
        return apiInfoMapper.updateById(apiInfoDO) == 1;
    }

    @Override
    public boolean deleteApiInfo(String appName, Long userId, Long apiInfoId) {
        checkApp(appName);
        // 校验是不是owner
        LambdaQueryWrapper<ApiInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiInfoDO::getAppName, appName);
        queryWrapper.eq(ApiInfoDO::getId, apiInfoId);
        return apiInfoMapper.delete(queryWrapper) == 1;
    }

    @Override
    public List<ApiInfoDTO> getApiInfoList(String appName, String directoryId) {
        checkApp(appName);
        LambdaQueryWrapper<ApiInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiInfoDO::getAppName, appName);
        queryWrapper.eq(ApiInfoDO::getDirectoryId, directoryId);
        List<ApiInfoDO> dos = apiInfoMapper.selectList(queryWrapper);
        return dos.stream().map(ApiInfoDO::doToDto).toList();
    }

    @Override
    public ApiInfoDTO getApiInfoDetail(Long apiInfoId) {
        ApiInfoDO apiInfoDO = apiInfoMapper.selectById(apiInfoId);
        return ApiInfoDO.doToDto(apiInfoDO);
    }

    private void checkApp(String appName) {
        AppInfoDTO appInfo = appInfoService.getAppInfoByName(appName);
        if (appInfo == null) {
            throw new BizException("应用不存在");
        }
    }

    private ApiRequestDTO buildPage(String paramLocal) {
        ApiRequestDTO requestDTO = new ApiRequestDTO();
        requestDTO.setRequired(true);
        requestDTO.setParamName("page");
        requestDTO.setParamClass(ApiReqParamClass.INT);
        requestDTO.setParamType(ApiParamType.SYSTEM);
        requestDTO.setExampleValue("1");
        requestDTO.setParamLocal(paramLocal);
        requestDTO.setDesc("第几页");
        return requestDTO;
    }

    private ApiRequestDTO buildPageSize(String paramLocal) {
        ApiRequestDTO requestDTO = new ApiRequestDTO();
        requestDTO.setRequired(true);
        requestDTO.setParamName("pageSize");
        requestDTO.setParamClass(ApiReqParamClass.INT);
        requestDTO.setParamType(ApiParamType.SYSTEM);
        requestDTO.setExampleValue("1");
        requestDTO.setParamLocal(paramLocal);
        requestDTO.setDesc("每页数量");
        return requestDTO;
    }
}

