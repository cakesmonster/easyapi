package com.cakemonster.easyapi.ds;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.ds.dialect.Dialect;
import com.cakemonster.easyapi.enums.DbType;
import com.cakemonster.easyapi.model.DatabaseColumn;
import com.cakemonster.easyapi.model.DataSourceQueryParam;
import com.cakemonster.easyapi.model.DataSourceQueryResult;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JdbcDataSourceService
 *
 * @author cakemonster
 * @date 2024/8/11
 */
public class JdbcDataSourceService implements DataSourceService {

    private final DataSourceManager dataSourceManager;

    private final Map<DbType, Dialect> dialectHandlers;

    public JdbcDataSourceService(DataSourceManager dataSourceManager, Map<DbType, Dialect> dialectHandlers) {
        this.dataSourceManager = dataSourceManager;
        this.dialectHandlers = dialectHandlers;
    }

    @Override
    public DataSourceQueryResult executeQuery(DataSourceQueryParam dataSourceQueryParam) {
        ConnectionParam config = dataSourceQueryParam.getConnectionParam();
        DataSource dataSource = dataSourceManager.getDataSource(config);
        String query = dataSourceQueryParam.getQuery();
        Map<String, Object> parameters = dataSourceQueryParam.getParameters();
        Page page = dataSourceQueryParam.getPage();
        List<SortOrder> sortOrders = dataSourceQueryParam.getSortOrders();
        List<ApiResponseDTO> ApiResponseDTOs = dataSourceQueryParam.getResponseFields();

        try (Connection connection = dataSource.getConnection()) {
            // 获取相应的方言处理器
            DbType dbType = config.getDbType();
            Dialect dialectHandler = dialectHandlers.get(dbType);
            if (dialectHandler == null) {
                throw new IllegalArgumentException("Unsupported db type: " + config.getDbType());
            }

            // 处理排序
            String finalQuery = dialectHandler.getSortedSql(query, sortOrders);

            if (page != null) {
                // 处理count查询
                String countSql = dialectHandler.getCountSql(finalQuery);
                try (PreparedStatement countStatement = connection.prepareStatement(countSql)) {
                    setParameters(countStatement, parameters);
                    ResultSet countResultSet = countStatement.executeQuery();
                    if (countResultSet.next()) {
                        long totalRecords = countResultSet.getLong(1);

                        // 处理分页查询
                        String pagedSql = dialectHandler.getPagedSql(finalQuery, page);
                        try (PreparedStatement pagedStatement = connection.prepareStatement(pagedSql)) {
                            setParameters(pagedStatement, parameters);
                            ResultSet resultSet = pagedStatement.executeQuery();
                            DataSourceQueryResult result = processResultSet(resultSet, ApiResponseDTOs);
                            result.setTotalRecords(totalRecords);
                            return result;
                        }
                    }
                }
            } else {
                // 直接执行排序后的查询
                try (PreparedStatement statement = connection.prepareStatement(finalQuery)) {
                    setParameters(statement, parameters);
                    ResultSet resultSet = statement.executeQuery();
                    return processResultSet(resultSet, ApiResponseDTOs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void setParameters(PreparedStatement statement, Map<String, Object> parameters) throws SQLException {
        int index = 1;
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            statement.setObject(index++, entry.getValue());
        }
    }

    private DataSourceQueryResult processResultSet(ResultSet resultSet, List<ApiResponseDTO> ApiResponseDTOs)
        throws SQLException {
        List<Map<String, DatabaseColumn>> records = new ArrayList<>();

        // 获取记录
        while (resultSet.next()) {
            Map<String, DatabaseColumn> record = new HashMap<>();
            for (ApiResponseDTO field : ApiResponseDTOs) {
                String columnName = field.getParamName();
                Object value = getValueByFieldClass(resultSet, columnName, field.getParamClass().name());
                record.put(columnName,
                    new DatabaseColumn(columnName, getFieldClass(field.getParamClass().name()), value));
            }
            records.add(record);
        }

        return new DataSourceQueryResult(records);
    }

    private Object getValueByFieldClass(ResultSet resultSet, String columnName, String fieldClass) throws SQLException {
        switch (fieldClass) {
            case "String":
                return resultSet.getString(columnName);
            case "Integer":
                return resultSet.getInt(columnName);
            case "Long":
                return resultSet.getLong(columnName);
            case "Double":
                return resultSet.getDouble(columnName);
            case "Float":
                return resultSet.getFloat(columnName);
            case "Boolean":
                return resultSet.getBoolean(columnName);
            case "Timestamp":
                return resultSet.getTimestamp(columnName);
            case "Date":
                return resultSet.getDate(columnName);
            case "Time":
                return resultSet.getTime(columnName);
            default:
                return resultSet.getObject(columnName);
        }
    }

    private Class<?> getFieldClass(String fieldClass) {
        switch (fieldClass) {
            case "String":
                return String.class;
            case "Integer":
                return Integer.class;
            case "Long":
                return Long.class;
            case "Double":
                return Double.class;
            case "Float":
                return Float.class;
            case "Boolean":
                return Boolean.class;
            case "Timestamp":
                return java.sql.Timestamp.class;
            case "Date":
                return java.sql.Date.class;
            case "Time":
                return java.sql.Time.class;
            default:
                return Object.class;
        }
    }
}
