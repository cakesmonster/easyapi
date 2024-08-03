package com.cakemonster.easyapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cakemonster.easyapi.model.dto.ApiDirectoryDTO;
import com.cakemonster.easyapi.model.entity.ApiDirectoryDO;

import java.util.List;

/**
 * API目录(ApiDirectory)表服务接口
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:14
 */
public interface ApiDirectoryService extends IService<ApiDirectoryDO> {

    boolean save(String appName, Long userId, ApiDirectoryDTO apiDirectoryDTO);

    List<ApiDirectoryDTO> buildTreeDirectories(String appName);
}