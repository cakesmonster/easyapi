package com.cakemonster.easyapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DatabaseColumn
 *
 * @author cakemonster
 * @date 2024/8/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseColumn {

    private String columnName;

    private Class<?> columnType;

    private Object value;


}
