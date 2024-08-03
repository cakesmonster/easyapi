package com.cakemonster.easyapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cakemonster.easyapi.model.dto.AppInfoDTO;
import com.cakemonster.easyapi.model.entity.AppInfoDO;

/**
 * API目录(AppInfo)表服务接口
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:17
 */
public interface AppInfoService extends IService<AppInfoDO> {

    AppInfoDTO getAppInfoByName(String appName);
}

