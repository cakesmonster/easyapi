package com.cakemonster.easyapi.datasource.doris;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enums.DbType;
import lombok.Data;

/**
 * DorisConnectionParam
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class DorisConnectionParam extends BaseConnectionParam {
    @Override
    public DbType getDbType() {
        return null;
    }
}
