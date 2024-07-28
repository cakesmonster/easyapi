package com.cakemonster.easyapi.model;

import lombok.Data;

/**
 * ApiSystemParam
 *
 * @author cakemonster
 * @date 2024/7/29
 */
@Data
public class ApiSystemParam {

    /**
     * 展示类型
     */
    private String showType;

    /**
     * 排序方向
     */
    private String order;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页内数据量
     */
    private Integer pageSize;
}
