package com.cakemonster.easyapi.model;

import lombok.Data;
import org.apache.ibatis.mapping.BoundSql;

import java.util.List;
import java.util.Map;

/**
 * SqlExecuteParam
 *
 * @author cakemonster
 * @date 2024/7/27
 */
@Data
public class SqlExecuteParam {

    /**
     * mybatis boundSql
     */
    private BoundSql boundSql;

    /**
     * PreparedStatement执行sql按照实际参数值填充顺序的数据列表
     */
    private List<Object> paramValueList;

    /**
     * API请求条件数据
     */
    private Map<String, Object> conditions;
}
