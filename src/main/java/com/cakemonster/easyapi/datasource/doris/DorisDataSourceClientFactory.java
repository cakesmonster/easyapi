package com.cakemonster.easyapi.datasource.doris;

import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.datasource.client.DataSourceClientFactory;
import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enumration.DbType;
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
    public DbType getDbType() {
        return DbType.DORIS;
    }
}
