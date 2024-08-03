package com.cakemonster.easyapi.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * API目录(ApiDirectory)表实体类
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:13
 */
@Data
@SuppressWarnings("serial")
public class ApiDirectoryDO extends Model<ApiDirectoryDO> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 目录名
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 父目录id
     */
    private Long parentId;

    /**
     * 描述
     */
    private String desc;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModify;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

