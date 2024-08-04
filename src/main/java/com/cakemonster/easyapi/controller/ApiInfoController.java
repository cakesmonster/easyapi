package com.cakemonster.easyapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.common.Result;
import com.cakemonster.easyapi.service.ApiInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * API信息表(ApiInfo)表控制层
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:14
 */
@RestController
@RequestMapping("/apiInfo")
public class ApiInfoController {
    /**
     * 服务对象
     */
    @Autowired
    private ApiInfoService apiInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param apiInfoDO 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page<ApiInfoDO> page, ApiInfoDO apiInfoDO) {
        return Result.success(this.apiInfoService.page(page, new QueryWrapper<>(apiInfoDO)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.apiInfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param apiInfoDO 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody ApiInfoDO apiInfoDO) {
        return Result.success(this.apiInfoService.save(apiInfoDO));
    }

    /**
     * 修改数据
     *
     * @param apiInfoDO 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody ApiInfoDO apiInfoDO) {
        return Result.success(this.apiInfoService.updateById(apiInfoDO));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.success(this.apiInfoService.removeByIds(idList));
    }
}

