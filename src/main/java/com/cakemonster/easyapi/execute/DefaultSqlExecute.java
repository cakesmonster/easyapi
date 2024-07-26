package com.cakemonster.easyapi.execute;

import com.alibaba.fastjson.JSON;
import com.cakemonster.easyapi.parse.MybatisSqlParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AbstractDataSourceService
 *
 * @author cakemonster
 * @date 2024/7/27
 */
@Slf4j
public class DefaultSqlExecute {

    private static final Configuration CONFIGURATION = new Configuration();

    private final MybatisSqlParser parser = new MybatisSqlParser();

    private final JdbcTemplate jdbcTemplate;

    public DefaultSqlExecute(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        return execute(boundSql.getSql(), parameters);
    }

    private List<Map<String, Object>> execute(String sql, List<Object> params) {
        return jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) -> {
            Map<String, Object> rowMap = new HashMap<>();
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                // TODO(hzq): H2好像直接通过meta获取别名会全部大写？还有别的数据库会这样么？
                String columnLabel = rs.getMetaData().getColumnLabel(i);
                Object columnValue = rs.getObject(i);
                rowMap.put(columnLabel, columnValue);
            }
            return rowMap;
        });
    }

    private Map<String, String> parseAliases(String sql) {
        Map<String, String> aliasMap = new HashMap<>();
        // 只考虑 SELECT 子句部分
        String selectClause = sql.split("(?i)from")[0];
        // 正则表达式匹配列名和别名
        Pattern pattern = Pattern.compile("(?i)([^,\\s]+)(\\s+AS\\s+([\\w]+))?");
        Matcher matcher = pattern.matcher(selectClause);
        while (matcher.find()) {
            String columnName = matcher.group(1).trim();
            String alias = matcher.group(3) != null ? matcher.group(3).trim() : columnName;
            aliasMap.put(columnName, alias);
        }
        return aliasMap;
    }

    private List<Object> extractParameters(BoundSql boundSql, Map<String, Object> conditions) {
        List<Object> parameters = new ArrayList<>();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        MetaObject metaObject = CONFIGURATION.newMetaObject(boundSql.getParameterObject());

        if (parameterMappings != null) {

            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
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

        return parameters;
    }
}
