package com.cakemonster.easyapi.datasource.mysql;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enums.DbType;
import lombok.Data;

/**
 * MysqlConnectionParam
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class MysqlConnectionParam extends BaseConnectionParam {
    @Override
    public DbType getDbType() {
        return null;
    }
}
