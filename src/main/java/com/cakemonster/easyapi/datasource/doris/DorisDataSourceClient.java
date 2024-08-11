package com.cakemonster.easyapi.datasource.doris;

import com.cakemonster.easyapi.datasource.client.AbstractJdbcDataSourceClient;
import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enums.DbType;

/**
 * DorisDataSourceClient
 *
 * @author cakemonster
 * @date 2024/8/3
 */

public class DorisDataSourceClient extends AbstractJdbcDataSourceClient {

    public DorisDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        super(baseConnectionParam, dbType);
    }

    @Override
    public DbType getType() {
        return DbType.DORIS;
    }

}
