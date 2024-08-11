package com.cakemonster.easyapi.datasource.client;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.ds.ConnectionParam;
import com.cakemonster.easyapi.enums.DbType;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * DataSourceClientProvider
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Slf4j
@Component
public class DataSourceClientProvider {

    private static final Map<String, DataSourceClientFactory> DATA_SOURCE_CLIENT_FACTORY_MAP =
        new ConcurrentHashMap<>();

    private static final Cache<String, DataSourceClient> UNIQUE_ID2DATA_SOURCE_CLIENT_CACHE =
        CacheBuilder.newBuilder().expireAfterWrite(24, TimeUnit.HOURS)
            .removalListener((RemovalListener<String, DataSourceClient>)notification -> {
                try (DataSourceClient closedClient = notification.getValue()) {
                    log.info("Datasource: {} is removed from cache due to expire", notification.getKey());
                }
            }).maximumSize(100).build();

    @Autowired
    private List<DataSourceClientFactory> dataSourceClientFactoryList;

    @PostConstruct
    public void init() {
        log.info("Initializing DataSourceClientProvider, factory size {}", dataSourceClientFactoryList.size());
        dataSourceClientFactoryList.forEach(
            factory -> DATA_SOURCE_CLIENT_FACTORY_MAP.put(factory.getDbType().getName(), factory));
    }

    public DataSourceClient getDataSourceClient(DbType dbType, String connectionParam) throws ExecutionException {
        BaseConnectionParam baseConnectionParam = castConnectionParam(dbType, connectionParam);
        return getDataSourceClient(dbType, baseConnectionParam);
    }

    public DataSourceClient getDataSourceClient(DbType dbType, ConnectionParam connectionParam)
        throws ExecutionException {
        BaseConnectionParam baseConnectionParam = (BaseConnectionParam)connectionParam;
        String datasourceUniqueId = getDatasourceUniqueId(baseConnectionParam, dbType);
        log.info("Get connection from datasource {}", datasourceUniqueId);

        return UNIQUE_ID2DATA_SOURCE_CLIENT_CACHE.get(datasourceUniqueId, () -> {
            DataSourceClientFactory dataSourceChannel = DATA_SOURCE_CLIENT_FACTORY_MAP.get(dbType.getName());
            if (null == dataSourceChannel) {
                throw new RuntimeException(String.format("datasource factory '%s' is not found", dbType.getName()));
            }
            return dataSourceChannel.create(baseConnectionParam, dbType);
        });
    }

    public BaseConnectionParam castConnectionParam(DbType dbType, String connectionParam) {
        DataSourceClientFactory dataSourceClientFactory = DATA_SOURCE_CLIENT_FACTORY_MAP.get(dbType.getName());
        if (null == dataSourceClientFactory) {
            throw new RuntimeException(String.format("datasource factory '%s' is not found", dbType.getName()));
        }
        return dataSourceClientFactory.castConnectionParam(connectionParam);
    }

    private String getDatasourceUniqueId(ConnectionParam connectionParam, DbType dbType) {
        BaseConnectionParam baseConnectionParam = (BaseConnectionParam)connectionParam;
        return MessageFormat.format("{0}@{1}@{2}@{3}", dbType.getName(), baseConnectionParam.getUser(),
            baseConnectionParam.getPassword(), baseConnectionParam.getJdbcUrl());
    }

}
