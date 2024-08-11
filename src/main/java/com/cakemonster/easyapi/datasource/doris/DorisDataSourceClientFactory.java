package com.cakemonster.easyapi.datasource.doris;

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
public class DorisDataSourceClientFactory implements DataSourceClientFactory {

    @Override
    public DataSourceClient create(BaseConnectionParam baseConnectionParam, DbType dbType) {
        return new DorisDataSourceClient(baseConnectionParam, dbType);
    }

    @Override
    public BaseConnectionParam castConnectionParam(String connectionParam) {
        return JSON.parseObject(connectionParam, DorisConnectionParam.class);
    }

    @Override
    public DbType getDbType() {
        return DbType.DORIS;
    }
}
