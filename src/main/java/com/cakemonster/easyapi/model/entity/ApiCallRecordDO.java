package com.cakemonster.easyapi.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * API调用记录(ApiCallRecord)表实体类
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:08
 */
@Data
@SuppressWarnings("serial")
public class ApiCallRecordDO extends Model<ApiCallRecordDO> {

    /**
     * 主键
     */
    private Long id;

    /**
     * api id
     */
    private Long apiInfoId;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 响应内容
     */
    private String responseBody;

    /**
     * 版本号
     */
    private String version;

    /**
     * 调用状态,0成功/1失败
     */
    private Integer status;

    /**
     * 消耗时间
     */
    private Integer costTime;

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

