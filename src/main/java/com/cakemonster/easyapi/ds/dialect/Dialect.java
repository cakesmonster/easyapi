package com.cakemonster.easyapi.ds.dialect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.ds.ConnectionParam;
import com.cakemonster.easyapi.ds.SortOrder;
import com.cakemonster.easyapi.enums.DbType;

import java.util.List;

/**
 * Dialect
 *
 * @author cakemonster
 * @date 2024/8/10
 */
public interface Dialect {

    String getSortedSql(String originalSql, List<SortOrder> sortOrders);

    String getCountSql(String originalSql);

    String getPagedSql(String originalSql, Page page);

    String getJdbcUrl(ConnectionParam param);

    DbType getDbType();
}
