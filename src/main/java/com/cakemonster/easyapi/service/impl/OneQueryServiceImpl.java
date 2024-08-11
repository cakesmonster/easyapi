package com.cakemonster.easyapi.service.impl;

import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.executor.DefaultSqlExecutor;
import com.cakemonster.easyapi.model.DataSourceQueryParam;
import com.cakemonster.easyapi.model.DatabaseColumn;
import com.cakemonster.easyapi.model.dto.ApiInfoDTO;
import com.cakemonster.easyapi.model.dto.ApiRequestDTO;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import com.cakemonster.easyapi.model.dto.QueryParamDTO;
import com.cakemonster.easyapi.process.CycleRatioProcessor;
import com.cakemonster.easyapi.service.ApiInfoService;
import com.cakemonster.easyapi.service.DataSourceService;
import com.cakemonster.easyapi.service.OneQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        DefaultSqlExecutor defaultSqlExecutor =
            new DefaultSqlExecutor(dataSourceClient.getJdbcTemplate(), apiResponses);
        List<Map<String, Object>> firstQueryResult = defaultSqlExecutor.executeSql(dsl, queryParamDTO);

        // TODO(hzq): 同环比
        boolean needCalcCycleCrc = apiResponses.stream().anyMatch(ApiResponseDTO::getNeedCycleRatio);
        boolean needCalcSyncCrc = apiResponses.stream().anyMatch(ApiResponseDTO::getNeedSyncRatio);

        return firstQueryResult;
    }

    public static List<Map<String, DatabaseColumn>> calculateCycleRatio(List<Map<String, DatabaseColumn>> originList,
        List<ApiResponseDTO> apiResponses, String dateType, String cycleType,
        DataSourceQueryParam dataSourceQueryParam) {

        DataSourceQueryParam previousDataSourceQueryParam = new DataSourceQueryParam();
        BeanUtils.copyProperties(dataSourceQueryParam, previousDataSourceQueryParam);
        Map<String, Object> parameters = dataSourceQueryParam.getParameters();
        Map<String, Object> copyParameters = new HashMap<>(parameters);
        previousDataSourceQueryParam.setParameters(copyParameters);

        // 跨时间维度计算
        if (CycleRatioProcessor.isCrossTimeDimension(dateType, cycleType)) {
            String endDateTime = CycleRatioProcessor.getEndDateTime(originList);
            String startDateTime =
                CycleRatioProcessor.formatStartDateTime(parameters.get("dt").toString(), cycleType, dateType);
            previousDataSourceQueryParam.getParameters().put("startDateTime", startDateTime);
            previousDataSourceQueryParam.getParameters().put("endDateTime", endDateTime);
        } else {
            // TODO(hzq): 考虑查询是时间范围的时候
            previousDataSourceQueryParam.getParameters().put("dt", parameters.get("dt"));
        }

        // 根据previousDataSourceQueryParam查询数据
        List<Map<String, DatabaseColumn>> cycleDateDataList = null;
        // 计算环比
        CycleRatioProcessor.calculateCycleRatios(apiResponses, originList, cycleDateDataList, dateType);
        return originList;
    }

}
