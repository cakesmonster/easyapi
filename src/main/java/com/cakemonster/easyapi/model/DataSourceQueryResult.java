package com.cakemonster.easyapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * QueryResult
 *
 * @author cakemonster
 * @date 2024/8/10
 */
@Data
@NoArgsConstructor
public class DataSourceQueryResult {

    private List<Map<String, DatabaseColumn>> records;
    private long totalRecords;

    public DataSourceQueryResult(List<Map<String, DatabaseColumn>> records) {
        this.records = records;
    }
}
