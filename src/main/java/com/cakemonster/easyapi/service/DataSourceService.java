package com.cakemonster.easyapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.model.dto.DataSourceDO;

/**
 * (Datasource)表服务接口
 *
 * @author cakemonster
 * @date 2024-08-04 17:29:50
 */
public interface DataSourceService extends IService<DataSourceDO> {

    DataSourceClient getDataSource(Long dataSourceId);
}

