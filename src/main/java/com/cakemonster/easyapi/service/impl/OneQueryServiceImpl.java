package com.cakemonster.easyapi.service.impl;

import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.enumration.ApiRespParamClass;
import com.cakemonster.easyapi.model.dto.ApiInfoDTO;
import com.cakemonster.easyapi.model.dto.ApiRequestDTO;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import com.cakemonster.easyapi.model.dto.QueryParamDTO;
import com.cakemonster.easyapi.service.ApiInfoService;
import com.cakemonster.easyapi.service.DataSourceService;
import com.cakemonster.easyapi.service.OneQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        // TODO(hzq): 分页查询
        Boolean enablePaging = apiInfo.getEnablePaging();

        List<ApiRequestDTO> apiRequests = apiInfo.getApiRequests();
        List<ApiResponseDTO> apiResponses = apiInfo.getApiResponses();

        // 构建参数数组
        Object[] queryParams = new Object[apiRequests.size()];
        int index = 0;
        for (ApiRequestDTO param : apiRequests) {
            String paramName = param.getParamName();
            if (param.getRequired() && !queryParamDTO.containsKey(paramName)) {
                throw new IllegalArgumentException("Missing required parameter: " + paramName);
            }
            queryParams[index++] = queryParamDTO.get(paramName);
        }

        // 执行SQL查询并处理结果集
        String dsl = apiInfo.getDsl();
        Long datasetId = apiInfo.getDatasetId();
        DataSourceClient dataSourceClient = dataSourceService.getDataSource(datasetId);

        List<Map<String, Object>> firstQueryResult =
            dataSourceClient.getJdbcTemplate().query(dsl, queryParams, new ApiResponseRowMapper(apiResponses));

        // TODO(hzq): 同环比
        boolean needCalcCycleCrc = apiResponses.stream().anyMatch(ApiResponseDTO::getNeedCycleCrc);
        boolean needCalcSyncCrc = apiResponses.stream().anyMatch(ApiResponseDTO::getNeedSyncCrc);

        return firstQueryResult;
    }



    private static class ApiResponseRowMapper implements RowMapper<Map<String, Object>> {

        private final List<ApiResponseDTO> responseParams;

        public ApiResponseRowMapper(List<ApiResponseDTO> responseParams) {
            this.responseParams = responseParams;
        }

        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> result = new HashMap<>();
            for (ApiResponseDTO param : responseParams) {
                String paramName = param.getParamName();
                Object value = getValueByType(rs, paramName, param.getParamClass());
                result.put(paramName, value);
            }
            return result;
        }

        private Object getValueByType(ResultSet rs, String paramName, ApiRespParamClass paramClass)
            throws SQLException {
            switch (paramClass) {
                case STRING:
                    return rs.getString(paramName);
                case INT:
                    return rs.getInt(paramName);
                case LONG:
                    return rs.getLong(paramName);
                case BOOLEAN:
                    return rs.getBoolean(paramName);
                case FLOAT:
                    return rs.getFloat(paramName);
                case DOUBLE:
                    return rs.getDouble(paramName);
                default:
                    return rs.getObject(paramName);
            }
        }
    }
}
