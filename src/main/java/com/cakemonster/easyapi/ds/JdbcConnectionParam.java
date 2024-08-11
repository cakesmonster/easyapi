package com.cakemonster.easyapi.ds;

import com.cakemonster.easyapi.enums.DbType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * JdbcDataSourceConfig
 *
 * @author cakemonster
 * @date 2024/8/10
 */
@Data
public class JdbcConnectionParam implements ConnectionParam {

    private DbType dbType;

    private String username;

    private String password;

    private String host;

    private Integer port;

    private String jdbcUrl;

    private String address;

    private String database;

    private String driverLocation;

    private String driverClassName;

    private String validationQuery;

    private String other;

    private Map<String, String> properties = new HashMap<>();

}
