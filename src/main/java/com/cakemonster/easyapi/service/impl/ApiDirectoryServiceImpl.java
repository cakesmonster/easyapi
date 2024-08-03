package com.cakemonster.easyapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cakemonster.easyapi.dao.ApiDirectoryMapper;
import com.cakemonster.easyapi.exception.BizException;
import com.cakemonster.easyapi.model.dto.ApiDirectoryDTO;
import com.cakemonster.easyapi.model.dto.AppInfoDTO;
import com.cakemonster.easyapi.model.entity.ApiDirectoryDO;
import com.cakemonster.easyapi.model.entity.AppInfoDO;
import com.cakemonster.easyapi.service.ApiDirectoryService;
import com.cakemonster.easyapi.service.AppInfoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * API目录(ApiDirectory)表服务实现类
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:14
 */
@Service("apiDirectoryService")
public class ApiDirectoryServiceImpl extends ServiceImpl<ApiDirectoryMapper, ApiDirectoryDO>
    implements ApiDirectoryService {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ApiDirectoryMapper apiDirectoryMapper;

    @Override
    public boolean save(String appName, Long userId, ApiDirectoryDTO apiDirectoryDTO) {
        AppInfoDTO appInfo = appInfoService.getAppInfoByName(appName);
        if (appInfo == null) {
            throw new BizException("应用不存在");
        }
        ApiDirectoryDO apiDirectoryDO = ApiDirectoryDTO.dtoToDo(apiDirectoryDTO);
        apiDirectoryDO.setAppName(appName);
        return apiDirectoryMapper.insert(apiDirectoryDO) == 1;
    }

    @Override
    public List<ApiDirectoryDTO> buildTreeDirectories(String appName) {
        AppInfoDTO appInfo = appInfoService.getAppInfoByName(appName);
        if (appInfo == null) {
            throw new BizException("应用不存在");
        }
        LambdaQueryWrapper<ApiDirectoryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiDirectoryDO::getAppName, appName).orderByAsc(ApiDirectoryDO::getGmtCreate);
        List<ApiDirectoryDO> doList = apiDirectoryMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(doList)) {
            return Lists.newArrayList();
        }
        return buildTreeStructure(doList);
    }

    private List<ApiDirectoryDTO> buildTreeStructure(List<ApiDirectoryDO> directoryDOList) {
        Map<Long, ApiDirectoryDTO> directoryMap = Maps.newHashMap();
        List<ApiDirectoryDTO> rootDirectories = new ArrayList<>();

        List<ApiDirectoryDTO> directories = directoryDOList.stream().map(ApiDirectoryDTO::doToDto).toList();
        for (ApiDirectoryDTO dir : directories) {
            directoryMap.put(dir.getId(), dir);
        }

        for (ApiDirectoryDTO dir : directories) {
            if (dir.getParentId() == null) {
                rootDirectories.add(dir);
            } else {
                ApiDirectoryDTO parentDir = directoryMap.get(dir.getParentId());
                if (parentDir != null) {
                    parentDir.getChildren().add(dir);
                }
            }
        }
        return rootDirectories;
    }
}

