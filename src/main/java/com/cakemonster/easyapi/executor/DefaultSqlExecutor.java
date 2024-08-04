package com.cakemonster.easyapi.executor;

import com.alibaba.fastjson.JSON;
import com.cakemonster.easyapi.enumration.ApiRespParamClass;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import com.cakemonster.easyapi.parse.MybatisSqlParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AbstractDataSourceService
 *
 * @author cakemonster
 * @date 2024/7/27
 */
@Slf4j
public class DefaultSqlExecutor {

    private static final Configuration CONFIGURATION = new Configuration();

    private final MybatisSqlParser parser = new MybatisSqlParser();

    private final ApiResponseRowMapper rowMapper;

    private final JdbcTemplate jdbcTemplate;

    public DefaultSqlExecutor(JdbcTemplate jdbcTemplate, List<ApiResponseDTO> responses) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = new ApiResponseRowMapper(responses);
    }

    public List<Map<String, Object>> executeSql(String xml, Map<String, Object> conditions) {
        // 解析XML
        SqlSource sqlSource = parser.parse(xml);
        Map<String, Object> copyConditions = new HashMap<>(conditions);
        // TODO(hzq)
        copyConditions.put("returnFields", "TODO");

        // 获取BoundSql
        BoundSql boundSql = sqlSource.getBoundSql(copyConditions);
        log.info("sql: {}", JSON.toJSONString(boundSql.getSql()));

        // 提取参数值列表
        List<Object> parameters = extractParameters(boundSql, conditions);
        log.info("parameters: {}", JSON.toJSONString(parameters));

        // 执行sql查询
        return jdbcTemplate.query(boundSql.getSql(), parameters.toArray(), rowMapper);
    }

    private List<Object> extractParameters(BoundSql boundSql, Map<String, Object> conditions) {
        List<Object> parameters = new ArrayList<>();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        MetaObject metaObject = CONFIGURATION.newMetaObject(boundSql.getParameterObject());

        List<String> paramNames = new ArrayList<>();
        if (parameterMappings != null) {

            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
                if (parameterMapping.getMode() == ParameterMode.IN) {
                    paramNames.add(propertyName);
                }
                Object value;

                try {
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (metaObject.hasGetter(propertyName)) {
                        value = metaObject.getValue(propertyName);
                    } else {
                        value = conditions.get(propertyName);
                    }

                    // TODO(hzq)
                    // 如果参数是个类？
                    // 如果参数是个map？
                    // 如果参数是个枚举？

                    //                    if (value instanceof Collection) {
                    //                        parameters.addAll((Collection<?>)value);
                    //                    } else if (value instanceof Map) {
                    //                        parameters.addAll(((Map<?, ?>)value).values());
                    //                    } else if (value.getClass().isArray()) {
                    //                        Object[] array = (Object[])value;
                    //                        Collections.addAll(parameters, array);
                    //                    } else if (value instanceof Enum) {
                    //                        parameters.add(((Enum<?>)value).ordinal());
                    //                    } else {
                    //                        parameters.add(value);
                    //                    }
                    parameters.add(value);
                } catch (Exception e) {
                    throw new BindingException("Error getting parameter value", e);
                }
            }
        }

        log.info("params: {}", JSON.toJSONString(paramNames));
        return parameters;
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
