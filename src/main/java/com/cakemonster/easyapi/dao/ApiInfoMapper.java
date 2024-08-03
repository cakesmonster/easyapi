package com.cakemonster.easyapi.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cakemonster.easyapi.model.entity.ApiInfoDO;
import org.apache.ibatis.annotations.Param;

/**
 * API信息表(ApiInfo)表数据库访问层
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:15
 */
public interface ApiInfoMapper extends BaseMapper<ApiInfoDO> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ApiInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ApiInfoDO> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ApiInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ApiInfoDO> entities);

}

