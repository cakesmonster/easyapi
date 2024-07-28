package com.cakemonster.easyapi.model;

import lombok.Data;

/**
 * ApiUserDefineParam
 *
 * @author cakemonster
 * @date 2024/7/29
 */
@Data
public class ApiUserDefineParam {

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 是否需要环比
     */
    private Boolean needCycleCrc;

    /**
     * 是否需要同比
     */
    private Boolean needSyncCrc;

    /**
     * 时间类型，如果是实时需要给出天/小时/分钟
     */
    private String dateType;

    /**
     * 数据类型 实时/离线
     */
    private String dataType;

}
