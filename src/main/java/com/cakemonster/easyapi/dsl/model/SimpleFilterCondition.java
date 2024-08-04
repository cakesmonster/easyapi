package com.cakemonster.easyapi.dsl.model;

import lombok.Data;

import java.util.List;

/**
 * SimpleFilterCondition
 *
 * @author cakemonster
 * @date 2024/8/4
 */
@Data
public class SimpleFilterCondition implements FilterCondition {

    /**
     * 左边需要配置的字段信息
     */
    private Column column;
    /**
     * 连接的符号
     */
    private SymbolEnum symbol;
    /**
     * 右边需要配置的字段信息
     */
    private List<Column> rightColumn;

}
