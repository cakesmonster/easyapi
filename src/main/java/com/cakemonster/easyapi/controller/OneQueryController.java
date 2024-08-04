package com.cakemonster.easyapi.controller;

import com.cakemonster.easyapi.common.Result;
import com.cakemonster.easyapi.model.dto.QueryParamDTO;
import com.cakemonster.easyapi.service.OneQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * OneQueryController
 *
 * @author cakemonster
 * @date 2024/8/4
 */
@RestController
public class OneQueryController {

    @Autowired
    private OneQueryService oneQueryService;

    @PostMapping("/oneQuery")
    public Result<Object> oneQuery(@RequestBody QueryParamDTO queryParamDTO) {
        List<Map<String, Object>> result = oneQueryService.query(queryParamDTO);
        return Result.success(result);
    }
}
