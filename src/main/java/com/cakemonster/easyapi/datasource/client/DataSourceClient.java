package com.cakemonster.easyapi.datasource.client;

import com.cakemonster.easyapi.enumration.DbType;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * DataSourceClient
 *
 * @author cakemonster
 * @date 2024/8/3
 */
public interface DataSourceClient extends AutoCloseable {

    void checkClient();

    @Override
    void close();

    DbType getType();

    JdbcTemplate getJdbcTemplate();
}
