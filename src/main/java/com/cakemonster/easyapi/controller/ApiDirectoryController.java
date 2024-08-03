package com.cakemonster.easyapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.common.Result;
import com.cakemonster.easyapi.model.entity.ApiDirectoryDO;
import com.cakemonster.easyapi.service.ApiDirectoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * API目录(ApiDirectory)表控制层
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:13
 */
@RestController
@RequestMapping("/apiDirectory")
public class ApiDirectoryController {
    /**
     * 服务对象
     */
    @Autowired
    private ApiDirectoryService apiDirectoryService;

    /**
     * 分页查询所有数据
     *
     * @param page         分页对象
     * @param apiDirectory 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page<ApiDirectoryDO> page, ApiDirectoryDO apiDirectory) {
        return Result.success(this.apiDirectoryService.page(page, new QueryWrapper<>(apiDirectory)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.apiDirectoryService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param apiDirectoryDO 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody ApiDirectoryDO apiDirectoryDO) {
        return Result.success(this.apiDirectoryService.save(apiDirectoryDO));
    }

    /**
     * 修改数据
     *
     * @param apiDirectoryDO 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody ApiDirectoryDO apiDirectoryDO) {
        return Result.success(this.apiDirectoryService.updateById(apiDirectoryDO));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.success(this.apiDirectoryService.removeByIds(idList));
    }
}

