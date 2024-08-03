package com.cakemonster.easyapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cakemonster.easyapi.dao.ApiCallRecordMapper;
import com.cakemonster.easyapi.model.entity.ApiCallRecordDO;
import com.cakemonster.easyapi.service.ApiCallRecordService;
import org.springframework.stereotype.Service;

/**
 * API调用记录(ApiCallRecord)表服务实现类
 *
 * @author cakemonster
 * @date 2024-08-03 18:48:12
 */
@Service("apiCallRecordService")
public class ApiCallRecordServiceImpl extends ServiceImpl<ApiCallRecordMapper, ApiCallRecordDO>
    implements ApiCallRecordService {

}

