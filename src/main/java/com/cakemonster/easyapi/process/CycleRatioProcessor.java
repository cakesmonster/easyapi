package com.cakemonster.easyapi.process;

import com.cakemonster.easyapi.model.DataSourceQueryParam;
import com.cakemonster.easyapi.model.DatabaseColumn;
import com.cakemonster.easyapi.model.dto.ApiResponseDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CycleHelper
 *
 * @author cakemonster
 * @date 2024/8/4
 */
public class CycleRatioProcessor {

    public static boolean isCrossTimeDimension(String dateType, String cycleType) {
        List<String> dateTypes = Arrays.asList("minute", "hour", "day", "week", "month", "year");
        return dateTypes.indexOf(cycleType) > dateTypes.indexOf(dateType);
    }

    public static String getEndDateTime(List<Map<String, DatabaseColumn>> originList) {
        if (CollectionUtils.isNotEmpty(originList) && originList.get(0).containsKey("endDateTime")) {
            return originList.get(0).get("endDateTime").getValue().toString();
        }
        return null;
    }

    public static String formatStartDateTime(String dt, String dateType, String cycleType) {
        Date startDateTime = calculateStartDateTime(dt, cycleType);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String result = null;
        switch (dateType) {
            case "minute":
                result = minuteFormat.format(startDateTime);
                break;
            case "hour":
                result = hourFormat.format(startDateTime);
                break;
            case "day":
            case "week":
            case "month":
            case "year":
                result = dateFormat.format(startDateTime);
                break;
            default:
                throw new IllegalArgumentException("Unknown date type: " + dateType);
        }
        return result;
    }

    private static Date calculateStartDateTime(Object dt, String cycleType) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dt.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        switch (cycleType) {
            case "minute":
                calendar.add(Calendar.MINUTE, -1);
                break;
            case "hour":
                calendar.add(Calendar.HOUR_OF_DAY, -1);
                break;
            case "day":
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                break;
            case "week":
                calendar.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case "month":
                calendar.add(Calendar.MONTH, -1);
                break;
            case "year":
                calendar.add(Calendar.YEAR, -1);
                break;
            default:
                throw new IllegalArgumentException("Unknown cycle type: " + cycleType);
        }
        return calendar.getTime();
    }

    public static void calculateCycleRatios(List<ApiResponseDTO> apiResponses,
        List<Map<String, DatabaseColumn>> originList, List<Map<String, DatabaseColumn>> previousData, String dateType) {

        // 遍历apiResponses并计算环比
        for (ApiResponseDTO response : apiResponses) {
            if (response.getNeedCycleRatio()) {
                // 进行环比计算
                for (int i = 0; i < originList.size(); i++) {
                    Map<String, DatabaseColumn> current = originList.get(i);
                    Map<String, DatabaseColumn> previous = (i < previousData.size()) ? previousData.get(i) : null;

                    if (previous != null) {
                        // 示例计算环比（current/previous - 1）
                        String fieldName = response.getParamName();
                        DatabaseColumn currentValue = current.get(fieldName);
                        DatabaseColumn previousValue = previous.get(fieldName);

                        if (currentValue != null && previousValue != null) {
                            BigDecimal currentVal = new BigDecimal(currentValue.getValue().toString());
                            BigDecimal previousVal = new BigDecimal(previousValue.getValue().toString());

                            BigDecimal ratio = BigDecimal.ZERO;
                            if (previousVal.compareTo(BigDecimal.ZERO) != 0) {
                                ratio =
                                    currentVal.divide(previousVal, 5, RoundingMode.HALF_UP).subtract(BigDecimal.ONE);
                            }
                            // 将环比存储回originList
                            String cycleRatioName = fieldName + "CycleRatio";
                            current.put(cycleRatioName,
                                new DatabaseColumn(cycleRatioName, Double.class, ratio.doubleValue()));
                        }
                    }
                }
            }
        }
    }
}
