package com.cakemonster.easyapi.datasource.mysql;

import com.cakemonster.easyapi.datasource.client.AbstractJdbcDataSourceClient;
import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enumration.DbType;

/**
 * MySqlDataSourceClient
 *
 * @author cakemonster
 * @date 2024/8/3
 */

public class MySqlDataSourceClient extends AbstractJdbcDataSourceClient {

    public MySqlDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        super(baseConnectionParam, dbType);
    }

    @Override
    public DbType getType() {
        return DbType.MYSQL;
    }

}
