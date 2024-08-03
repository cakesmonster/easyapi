package com.cakemonster.easyapi.model.dto;

import com.cakemonster.easyapi.model.entity.ApiDirectoryDO;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiDirectoryDTO
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Data
public class ApiDirectoryDTO {

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
     * 子目录
     */
    private List<ApiDirectoryDTO> children = Lists.newArrayList();

    /**
     * 将 ApiDirectoryDO 转换为 ApiDirectoryDTO
     */
    public static ApiDirectoryDTO doToDto(ApiDirectoryDO entity) {
        if (entity == null) {
            return null;
        }
        ApiDirectoryDTO dto = new ApiDirectoryDTO();
        dto.setId(entity.getId());
        dto.setAppName(entity.getAppName());
        dto.setName(entity.getName());
        dto.setPath(entity.getPath());
        dto.setParentId(entity.getParentId());
        dto.setDesc(entity.getDesc());
        return dto;
    }

    /**
     * 将 ApiDirectoryDTO 转换为 ApiDirectoryDO
     */
    public static ApiDirectoryDO dtoToDo(ApiDirectoryDTO dto) {
        if (dto == null) {
            return null;
        }
        ApiDirectoryDO entity = new ApiDirectoryDO();
        entity.setId(dto.getId());
        entity.setAppName(dto.getAppName());
        entity.setName(dto.getName());
        entity.setPath(dto.getPath());
        entity.setParentId(dto.getParentId());
        entity.setDesc(dto.getDesc());
        return entity;
    }

}
