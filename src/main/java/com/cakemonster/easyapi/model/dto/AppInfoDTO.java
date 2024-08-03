package com.cakemonster.easyapi.model.dto;

import com.cakemonster.easyapi.model.entity.AppInfoDO;
import lombok.Data;

/**
 * AppInfoDTO
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class AppInfoDTO {

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

    public static AppInfoDO dtoToDO(AppInfoDTO dto) {
        AppInfoDO appInfoDO = new AppInfoDO();
        appInfoDO.setId(dto.getId());
        appInfoDO.setAppName(dto.getAppName());
        appInfoDO.setDesc(dto.getDesc());
        return appInfoDO;
    }

}
