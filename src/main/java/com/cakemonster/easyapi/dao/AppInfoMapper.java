package com.cakemonster.easyapi.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.cakemonster.easyapi.model.entity.AppInfoDO;

/**
 * API目录(AppInfo)表数据库访问层
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:17
 */
public interface AppInfoMapper extends BaseMapper<AppInfoDO> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AppInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AppInfoDO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AppInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AppInfoDO> entities);

}

