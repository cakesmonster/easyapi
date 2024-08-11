package com.cakemonster.easyapi.datasource.clickhouse;

import com.alibaba.fastjson2.JSON;
import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.datasource.client.DataSourceClientFactory;
import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enums.DbType;
import org.springframework.stereotype.Service;

/**
 * DorisDataSourceClientFactory
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Service
public class ClickHouseDataSourceClientFactory implements DataSourceClientFactory {

    @Override
    public DataSourceClient create(BaseConnectionParam baseConnectionParam, DbType dbType) {
        return new ClickHouseDataSourceClient(baseConnectionParam, dbType);
    }

    @Override
    public BaseConnectionParam castConnectionParam(String connectionParam) {
        return JSON.parseObject(connectionParam, ClickHouseConnectionParam.class);
    }

    @Override
    public DbType getDbType() {
        return DbType.CLICKHOUSE;
    }
}
