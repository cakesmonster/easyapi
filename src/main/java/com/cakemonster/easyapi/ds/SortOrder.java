package com.cakemonster.easyapi.ds;

import lombok.Data;

/**
 * SortOrder
 *
 * @author cakemonster
 * @date 2024/8/11
 */
@Data
public class SortOrder {

    private String column;
    private String direction; // ASC or DESC
}
