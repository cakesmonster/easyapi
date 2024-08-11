package com.cakemonster.easyapi.enums;

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

    CLICKHOUSE(0, "clickhouse", "com.clickhouse.jdbc.ClickHouseDriver", true),
    DORIS(1, "doris", "com.mysql.cj.jdbc.Driver", true),
    MYSQL(2, "mysql", "com.mysql.cj.jdbc.Driver", true);

    private final Integer code;

    private final String name;

    private final String driverClass;

    private final Boolean jdbc;

    public static DbType of(Integer code) {
        for (DbType dbType : DbType.values()) {
            if (dbType.getCode().equals(code)) {
                return dbType;
            }
        }
        return null;
    }

    public static DbType of(String name) {
        for (DbType dbType : DbType.values()) {
            if (dbType.getName().equals(name)) {
                return dbType;
            }
        }
        return null;
    }
}
