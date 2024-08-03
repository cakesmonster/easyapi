package com.cakemonster.easyapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cakemonster.easyapi.dao.AppInfoMapper;
import com.cakemonster.easyapi.model.dto.AppInfoDTO;
import com.cakemonster.easyapi.model.entity.AppInfoDO;
import com.cakemonster.easyapi.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * API目录(AppInfo)表服务实现类
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:17
 */
@Service("appInfoService")
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfoDO> implements AppInfoService {

    @Autowired
    private AppInfoMapper appInfoMapper;

    @Override
    public AppInfoDTO getAppInfoByName(String appName) {
        LambdaQueryWrapper<AppInfoDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AppInfoDO::getAppName, appName);
        AppInfoDO appInfoDO = appInfoMapper.selectOne(queryWrapper);
        return AppInfoDO.doToDto(appInfoDO);
    }
}

