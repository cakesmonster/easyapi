package com.cakemonster.easyapi.service.impl;

import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.executor.DefaultSqlExecutor;
import com.cakemonster.easyapi.model.dto.ApiInfoDTO;
import com.cakemonster.easyapi.model.dto.ApiRequestDTO;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import com.cakemonster.easyapi.model.dto.QueryParamDTO;
import com.cakemonster.easyapi.service.ApiInfoService;
import com.cakemonster.easyapi.service.DataSourceService;
import com.cakemonster.easyapi.service.OneQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * OneQueryServiceImpl
 *
 * @author cakemonster
 * @date 2024/8/4
 */
@Slf4j
@Service
public class OneQueryServiceImpl implements OneQueryService {

    @Autowired
    private ApiInfoService apiInfoService;

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public List<Map<String, Object>> query(QueryParamDTO queryParamDTO) {
        Long apiInfoId = queryParamDTO.getApiInfoId();
        ApiInfoDTO apiInfo = apiInfoService.getApiInfoDetail(apiInfoId);
        if (apiInfo == null) {
            throw new IllegalArgumentException("API with ID " + apiInfoId + " not found");
        }

        // 验证必传参数
        List<ApiRequestDTO> requestParams = apiInfo.getApiRequests();
        for (ApiRequestDTO requestParam : requestParams) {
            if (requestParam.getRequired() && !queryParamDTO.containsKey(requestParam.getParamName())) {
                throw new RuntimeException("Missing required parameter: " + requestParam.getParamName());
            }
        }

        // TODO(hzq): 分页查询
        Boolean enablePaging = apiInfo.getEnablePaging();
        List<ApiResponseDTO> apiResponses = apiInfo.getApiResponses();

        // 执行SQL查询并处理结果集
        String dsl = apiInfo.getDsl();
        Long datasetId = apiInfo.getDatasetId();
        DataSourceClient dataSourceClient = dataSourceService.getDataSource(datasetId);

        // TODO(hzq): 替换自定义DSL，不一定用mybatis xml sql
        DefaultSqlExecutor defaultSqlExecutor = new DefaultSqlExecutor(dataSourceClient.getJdbcTemplate(), apiResponses);
        List<Map<String, Object>> firstQueryResult = defaultSqlExecutor.executeSql(dsl, queryParamDTO);

        // TODO(hzq): 同环比
        boolean needCalcCycleCrc = apiResponses.stream().anyMatch(ApiResponseDTO::getNeedCycleCrc);
        boolean needCalcSyncCrc = apiResponses.stream().anyMatch(ApiResponseDTO::getNeedSyncCrc);

        return firstQueryResult;
    }

}
