package com.cakemonster.easyapi.datasource.param;

import com.cakemonster.easyapi.ds.ConnectionParam;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseConnectionParam
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public abstract class BaseConnectionParam implements ConnectionParam {

    protected String user;

    protected String password;

    protected String jdbcUrl;

    protected String address;

    protected String database;

    protected String driverLocation;

    protected String driverClassName;

    protected String validationQuery;

    protected String other;

    private Map<String, String> properties = new HashMap<>();
}
