package com.cakemonster.easyapi.process;

import com.cakemonster.easyapi.model.DataSourceQueryParam;
import com.cakemonster.easyapi.model.DataSourceQueryResult;
import com.cakemonster.easyapi.model.DatabaseColumn;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CycleHelper
 *
 * @author cakemonster
 * @date 2024/8/4
 */
public class CycleRatioProcessor {

    public static List<Map<String, DatabaseColumn>> calculateCycleRatio(List<Map<String, DatabaseColumn>> originList,
        List<ApiResponseDTO> apiResponses, String dateType, String cycleType,
        DataSourceQueryParam dataSourceQueryParam) {
        DataSourceQueryParam previousDataSourceQueryParam = new DataSourceQueryParam();
        BeanUtils.copyProperties(dataSourceQueryParam, previousDataSourceQueryParam);
        Map<String, Object> parameters = dataSourceQueryParam.getParameters();
        Map<String, Object> copyParameters = new HashMap<>(parameters);
        previousDataSourceQueryParam.setParameters(copyParameters);

        // 判断是否跨时间维度计算，并计算环比时间
        if (crossCalc(dateType, cycleType)) {
            // 如果跨时间维度，输入的只是dt，那么环比时间需要从上一个查询结果中获取endDateTime字段，
            // 开始时间需要从dataSourceQueryParam的parameters中获取dt字段根据cycleType计算环比日期，并根据dateType计算到小时或者到分钟
            // 举个例子比如dateType是hour那么，startDateTime就是 dt 00，如果是minute，那么startDateTime就是dt 00:00

        } else {
            // 不跨时间维度，如果时间只是dt，那么环比也只取dt
            // 不跨时间维度，如果时间是范围，那么环比也取范围
        }

        // 根据previousDataSourceQueryParam查询，你可以认为我有一个方法输入是DataSourceQueryParam，输出是List<Map<String, DatabaseColumn>>

        // 根据apiResponses计算环比 注意计算环比的时候查询结果一定会有响应的时间日期，比如小时环比一定会有dt,hh；日环比一定会有dt

        return null;
    }

    private static boolean crossCalc(String dateType, String cycleType) {
        return false;
    }

}
