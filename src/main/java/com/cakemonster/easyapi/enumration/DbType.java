package com.cakemonster.easyapi.enumration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DbType
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Getter
@AllArgsConstructor
public enum DbType {

    CLICKHOUSE(0, "clickhouse", "com.clickhouse.jdbc.ClickHouseDriver"),
    DORIS(1, "doris", "com.mysql.cj.jdbc.Driver"),
    MYSQL(2, "mysql", "com.mysql.cj.jdbc.Driver");

    private final Integer code;

    private final String name;

    private final String driverClass;

}
