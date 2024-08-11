package com.cakemonster.easyapi.datasource.clickhouse;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enums.DbType;
import lombok.Data;

/**
 * ClickHouseConnectionParam
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class ClickHouseConnectionParam extends BaseConnectionParam {
    @Override
    public DbType getDbType() {
        return null;
    }
}
