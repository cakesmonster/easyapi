package com.cakemonster.easyapi.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cakemonster.easyapi.model.dto.AppInfoDTO;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * API目录(AppInfo)表实体类
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:17
 */
@Data
@SuppressWarnings("serial")
public class AppInfoDO extends Model<AppInfoDO> {

    /**
     * 主键
     */
    private Long id;

    /**
     * 应用名
     */
    private String appName;

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

    public static AppInfoDTO doToDto(AppInfoDO appInfoDO) {
        AppInfoDTO appInfoDTO = new AppInfoDTO();
        appInfoDTO.setId(appInfoDO.getId());
        appInfoDTO.setAppName(appInfoDO.getAppName());
        appInfoDTO.setDesc(appInfoDO.getDesc());
        return appInfoDTO;
    }
}

