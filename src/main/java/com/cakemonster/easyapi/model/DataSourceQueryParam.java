package com.cakemonster.easyapi.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.ds.ConnectionParam;
import com.cakemonster.easyapi.ds.SortOrder;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * QueryRequest
 *
 * @author cakemonster
 * @date 2024/8/10
 */
@Data
public class DataSourceQueryParam {

    private String query;
    private Map<String, Object> parameters;
    private String queryType;
    private Page page;
    private ConnectionParam connectionParam;
    private List<SortOrder> sortOrders;
    private List<ApiResponseDTO> responseFields;
}
