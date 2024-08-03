package com.cakemonster.easyapi.datasource.clickhouse;

import com.cakemonster.easyapi.datasource.client.AbstractJdbcDataSourceClient;
import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enumration.DbType;

/**
 * ClickHouseDataSourceClient
 *
 * @author cakemonster
 * @date 2024/8/3
 */

public class ClickHouseDataSourceClient extends AbstractJdbcDataSourceClient {

    public ClickHouseDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        super(baseConnectionParam, dbType);
    }

    @Override
    public DbType getType() {
        return DbType.CLICKHOUSE;
    }

}
