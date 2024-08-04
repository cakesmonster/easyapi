package com.cakemonster.easyapi.model.dto;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * (Datasource)表实体类
 *
 * @author cakemonster
 * @date 2024-08-04 17:28:41
 */
@Data
@SuppressWarnings("serial")
public class DataSourceDO extends Model<DataSourceDO> {

    /**
    * 主键
    */
    private Long id;
    
    /**
    * 数据源名称
    */
    private String name;
    
    /**
    * 描述
    */
    private String desc;
    
    /**
    * 数据源类型
    */
    private Integer dbType;
    
    /**
    * 创建人id
    */
    private Long userId;
    
    /**
    * 连接参数
    */
    private String connectionParams;
    
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

