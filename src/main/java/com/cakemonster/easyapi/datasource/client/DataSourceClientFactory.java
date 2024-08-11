package com.cakemonster.easyapi.datasource.client;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enums.DbType;

/**
 * DataSourceClientFactory
 *
 * @author cakemonster
 * @date 2024/8/3
 */
public interface DataSourceClientFactory {

    DataSourceClient create(BaseConnectionParam baseConnectionParam, DbType dbType);

    BaseConnectionParam castConnectionParam(String connectionParam);

    DbType getDbType();
}
