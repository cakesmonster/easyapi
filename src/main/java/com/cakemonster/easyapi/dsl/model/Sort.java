package com.cakemonster.easyapi.dsl.model;

import lombok.Data;

import java.util.List;

/**
 * Sort
 *
 * @author cakemonster
 * @date 2024/8/4
 */
@Data
public class Sort {

    private List<SortColumn> sortColumns;

    @Data
    public static class SortColumn {
        // 排序字段
        private Column column;
        // 升序还是降序
        private SortRule sortRule;
    }
}
