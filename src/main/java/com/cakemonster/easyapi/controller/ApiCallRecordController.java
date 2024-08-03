package com.cakemonster.easyapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.common.Result;
import com.cakemonster.easyapi.model.entity.ApiCallRecordDO;
import com.cakemonster.easyapi.service.ApiCallRecordService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * API调用记录(ApiCallRecord)表控制层
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:03
 */
@RestController
@RequestMapping("/apiCallRecord")
public class ApiCallRecordController {
    /**
     * 服务对象
     */
    @Autowired
    private ApiCallRecordService apiCallRecordService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param apiCallRecord 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page<ApiCallRecordDO> page, ApiCallRecordDO apiCallRecord) {
        return Result.success(this.apiCallRecordService.page(page, new QueryWrapper<>(apiCallRecord)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.apiCallRecordService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param apiCallRecordDO 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody ApiCallRecordDO apiCallRecordDO) {
        return Result.success(this.apiCallRecordService.save(apiCallRecordDO));
    }

    /**
     * 修改数据
     *
     * @param apiCallRecordDO 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody ApiCallRecordDO apiCallRecordDO) {
        return Result.success(this.apiCallRecordService.updateById(apiCallRecordDO));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.success(this.apiCallRecordService.removeByIds(idList));
    }
}

