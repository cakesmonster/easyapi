package com.cakemonster.easyapi.dsl.model;

import java.util.List;

/**
 * GroupBy
 *
 * @author cakemonster
 * @date 2024/8/4
 */
public class GroupBy {
    /**
     * 选择需要输出的列字段
     */
    private List<Column> outputColumns;
    /**
     * 用于分组的字段
     */
    private List<Column> groupByColumns;
    /**
     * having条件设置
     */
    private FilterCondition havingCondition;

}
