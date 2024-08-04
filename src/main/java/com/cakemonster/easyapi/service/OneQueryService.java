package com.cakemonster.easyapi.service;

import com.cakemonster.easyapi.model.dto.QueryParamDTO;

import java.util.List;
import java.util.Map;

/**
 * OneQueryService
 *
 * @author cakemonster
 * @date 2024/8/4
 */
public interface OneQueryService {

    List<Map<String, Object>> query(QueryParamDTO queryParamDTO);

}
