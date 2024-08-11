package com.cakemonster.easyapi.ds.dialect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.ds.ConnectionParam;
import com.cakemonster.easyapi.ds.JdbcConnectionParam;
import com.cakemonster.easyapi.ds.SortOrder;
import com.cakemonster.easyapi.enums.DbType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClickHouseDialect
 *
 * @author cakemonster
 * @date 2024/8/11
 */
@Service
public class ClickHouseDialect extends AbstractJdbcDialect {

    @Override
    public String getJdbcUrl(ConnectionParam param) {
        JdbcConnectionParam config = (JdbcConnectionParam)param;
        return "jdbc:clickhouse://" + config.getHost() + ":" + config.getPort() + "/" + config.getDatabase();
    }

    @Override
    public DbType getDbType() {
        return DbType.CLICKHOUSE;
    }
}
