package com.cakemonster.easyapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cakemonster.easyapi.common.Result;
import com.cakemonster.easyapi.model.entity.AppInfoDO;
import com.cakemonster.easyapi.service.AppInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * API目录(AppInfo)表控制层
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:17
 */
@RestController
@RequestMapping("/appInfo")
public class AppInfoController {
    /**
     * 服务对象
     */
    @Autowired
    private AppInfoService appInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param appInfoDO 查询实体
     * @return 所有数据
     */
    @GetMapping
    public Result selectAll(Page<AppInfoDO> page, AppInfoDO appInfoDO) {
        return Result.success(this.appInfoService.page(page, new QueryWrapper<>(appInfoDO)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.success(this.appInfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param appInfoDO 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Result insert(@RequestBody AppInfoDO appInfoDO) {
        return Result.success(this.appInfoService.save(appInfoDO));
    }

    /**
     * 修改数据
     *
     * @param appInfoDO 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Result update(@RequestBody AppInfoDO appInfoDO) {
        return Result.success(this.appInfoService.updateById(appInfoDO));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.success(this.appInfoService.removeByIds(idList));
    }
}

