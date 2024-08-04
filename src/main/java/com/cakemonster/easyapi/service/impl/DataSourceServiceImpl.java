package com.cakemonster.easyapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cakemonster.easyapi.dao.DatasourceMapper;
import com.cakemonster.easyapi.datasource.client.DataSourceClient;
import com.cakemonster.easyapi.datasource.client.DataSourceClientProvider;
import com.cakemonster.easyapi.enumration.DbType;
import com.cakemonster.easyapi.model.dto.DataSourceDO;
import com.cakemonster.easyapi.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * (Datasource)表服务实现类
 *
 * @author cakemonster
 * @date 2024-08-04 17:29:50
 */
@Service("dataSourceService")
public class DataSourceServiceImpl extends ServiceImpl<DatasourceMapper, DataSourceDO> implements DataSourceService {

    @Autowired
    private DatasourceMapper datasourceMapper;

    @Autowired
    private DataSourceClientProvider dataSourceClientProvider;

    @Override
    public DataSourceClient getDataSource(Long dataSourceId) {
        DataSourceDO dataSourceDO = datasourceMapper.selectById(dataSourceId);
        String connectionParams = dataSourceDO.getConnectionParams();
        DbType dbType = DbType.of(dataSourceDO.getDbType());
        try {
            return dataSourceClientProvider.getDataSourceClient(dbType, connectionParams);
        } catch (ExecutionException e) {

        }
        return null;
    }
}

