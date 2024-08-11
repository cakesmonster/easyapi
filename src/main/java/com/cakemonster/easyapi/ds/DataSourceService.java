package com.cakemonster.easyapi.ds;

import com.cakemonster.easyapi.model.DataSourceQueryParam;
import com.cakemonster.easyapi.model.DataSourceQueryResult;

/**
 * DataSourceService
 *
 * @author cakemonster
 * @date 2024/8/11
 */
public interface DataSourceService {

    DataSourceQueryResult executeQuery(DataSourceQueryParam dataSourceQueryParam);
}
