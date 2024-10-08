package com.cakemonster.easyapi.datasource.mysql;

import com.alibaba.fastjson2.JSON;
import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.datasource.client.DataSourceClientFactory;
import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enums.DbType;
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
    public BaseConnectionParam castConnectionParam(String connectionParam) {
        return JSON.parseObject(connectionParam, MysqlConnectionParam.class);
    }

    @Override
    public DbType getDbType() {
        return DbType.MYSQL;
    }
}
