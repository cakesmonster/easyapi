package com.cakemonster.easyapi.datasource.mysql;

import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.datasource.client.DataSourceClientFactory;
import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enumration.DbType;
import org.springframework.stereotype.Service;

/**
 * MySqlDataSourceClientFactory
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Service
public class MySqlDataSourceClientFactory implements DataSourceClientFactory {

    @Override
    public DataSourceClient create(BaseConnectionParam baseConnectionParam, DbType dbType) {
        return new MySqlDataSourceClient(baseConnectionParam, dbType);
    }

    @Override
    public DbType getDbType() {
        return DbType.MYSQL;
    }
}
