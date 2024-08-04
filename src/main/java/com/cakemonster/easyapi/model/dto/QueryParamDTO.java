package com.cakemonster.easyapi.model.dto;

import lombok.Data;

import java.util.HashMap;

/**
 * QueryParamDTO
 *
 * @author cakemonster
 * @date 2024/8/4
 */
@Data
public class QueryParamDTO extends HashMap<String, Object> {

    private Long apiInfoId;

}
