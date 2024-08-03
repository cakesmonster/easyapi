package com.cakemonster.easyapi.datasource.client;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enumration.DbType;

/**
 * DataSourceClientFactory
 *
 * @author cakemonster
 * @date 2024/8/3
 */
public interface DataSourceClientFactory {

    DataSourceClient create(BaseConnectionParam baseConnectionParam, DbType dbType);

    DbType getDbType();
}
