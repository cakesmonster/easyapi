package com.cakemonster.easyapi.ds;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.ds.dialect.Dialect;
import com.cakemonster.easyapi.enums.DbType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DataSourceManager
 *
 * @author cakemonster
 * @date 2024/8/11
 */
@Component
public class DataSourceManager {

    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();
    private Map<String, Dialect> dialectHandlers;

    @Autowired
    private List<Dialect> dialectList;

    @PostConstruct
    public void init() {
        dialectList.forEach(dialect -> dialectHandlers.put(dialect.getDbType().getName(), dialect));
    }

    public DataSource getDataSource(ConnectionParam param) {
        if (param.getDbType().getJdbc()) {
            JdbcConnectionParam config = (JdbcConnectionParam)param;
            String key = getDatasourceUniqueId(config, param.getDbType());
            return dataSourceMap.computeIfAbsent(key, k -> createDataSource(config));
        }
        return null;
    }

    private DataSource createDataSource(JdbcConnectionParam config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getJdbcUrl(config));
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriverClassName());
        //        hikariConfig.setMaximumPoolSize(config.getMaxPoolSize());
        //        hikariConfig.setMinimumIdle(config.getMinIdle());
        //        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        return new HikariDataSource(hikariConfig);
    }

    private String getDatasourceUniqueId(ConnectionParam connectionParam, DbType dbType) {
        JdbcConnectionParam jdbcConnectionParam = (JdbcConnectionParam)connectionParam;
        return MessageFormat.format("{0}@{1}@{2}@{3}", dbType.getName(), jdbcConnectionParam.getUsername(),
            jdbcConnectionParam.getPassword(), jdbcConnectionParam.getJdbcUrl());
    }

    private String getJdbcUrl(JdbcConnectionParam config) {
        Dialect handler = dialectHandlers.get(config.getDbType().getName());
        if (handler == null) {
            throw new IllegalArgumentException("Unsupported db type: " + config.getDbType());
        }
        return handler.getJdbcUrl(config);
    }
}
