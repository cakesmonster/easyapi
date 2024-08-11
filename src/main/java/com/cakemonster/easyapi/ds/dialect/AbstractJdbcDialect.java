package com.cakemonster.easyapi.ds.dialect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.ds.SortOrder;

import java.util.List;

/**
 * AbstractJdbcDialect
 *
 * @author cakemonster
 * @date 2024/8/11
 */
public abstract class AbstractJdbcDialect implements Dialect {

    @Override
    public String getSortedSql(String originalSql, List<SortOrder> sortOrders) {
        if (sortOrders == null || sortOrders.isEmpty()) {
            return originalSql;
        }
        StringBuilder sortedSql = new StringBuilder(originalSql);
        sortedSql.append(" ORDER BY ");
        for (SortOrder sortOrder : sortOrders) {
            sortedSql.append(sortOrder.getColumn()).append(" ").append(sortOrder.getDirection()).append(", ");
        }
        // Remove last comma
        sortedSql.setLength(sortedSql.length() - 2);
        return sortedSql.toString();
    }

    @Override
    public String getCountSql(String originalSql) {
        return "SELECT COUNT(*) FROM (" + originalSql + ") AS total";
    }

    @Override
    public String getPagedSql(String originalSql, Page page) {
        return originalSql + " LIMIT " + page.getCurrent() + ", " + page.getSize();
    }
}
