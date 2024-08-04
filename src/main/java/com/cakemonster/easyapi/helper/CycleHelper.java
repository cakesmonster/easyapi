package com.cakemonster.easyapi.helper;

import com.cakemonster.easyapi.model.dto.ApiResponseDTO;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * CycleHelper
 *
 * @author cakemonster
 * @date 2024/8/4
 */
public class CycleHelper {

    public List<Map<String, Object>> calculateCycleRatios(List<Map<String, Object>> originalResults,
        List<Map<String, Object>> cycleResults, List<ApiResponseDTO> responseParams, String dateType) {
        if (dateType.equals("day")) {
            return calculateDailyCycleRatios(originalResults, cycleResults, responseParams);
        } else if (dateType.equals("month")) {
            return calculateMonthlyCycleRatios(originalResults, cycleResults, responseParams);
        } else {
            throw new IllegalArgumentException("Unknown date type for cycle calculation: " + dateType);
        }
    }

    private List<Map<String, Object>> calculateDailyCycleRatios(List<Map<String, Object>> originalResults,
        List<Map<String, Object>> cycleResults, List<ApiResponseDTO> responseParams) {
        Map<String, Map<String, Object>> cycleResultsMap = new HashMap<>();
        for (Map<String, Object> cycleResult : cycleResults) {
            String dt = (String)cycleResult.get("dt");
            cycleResultsMap.put(dt, cycleResult);
        }

        for (Map<String, Object> originalResult : originalResults) {
            String dt = (String)originalResult.get("dt");
            String cycleDt = calculatePreviousDate(dt, "day");

            for (ApiResponseDTO param : responseParams) {
                if (param.getNeedCycleCrc()) {
                    String paramName = param.getParamName();
                    Object originalValue = originalResult.get(paramName);
                    Object cycleValue =
                        cycleResultsMap.get(cycleDt) != null ? cycleResultsMap.get(cycleDt).get(paramName) : null;

                    if (cycleValue != null && originalValue instanceof Number && cycleValue instanceof Number) {
                        double cycleRatio =
                            ((Number)originalValue).doubleValue() / ((Number)cycleValue).doubleValue() - 1;
                        originalResult.put(paramName + "_cycle", cycleRatio);
                    } else {
                        originalResult.put(paramName + "_cycle", null);
                    }
                }
            }
        }

        return originalResults;
    }

    private List<Map<String, Object>> calculateMonthlyCycleRatios(List<Map<String, Object>> originalResults,
        List<Map<String, Object>> cycleResults, List<ApiResponseDTO> responseParams) {
        // 聚合原始结果和环比结果
        Map<String, Map<String, Object>> aggregatedOriginalResults =
            aggregateResultsByMonth(originalResults, responseParams);
        Map<String, Map<String, Object>> aggregatedCycleResults = aggregateResultsByMonth(cycleResults, responseParams);

        for (String month : aggregatedOriginalResults.keySet()) {
            Map<String, Object> originalResult = aggregatedOriginalResults.get(month);
            // 获取前一个月
            String cycleMonth = calculatePreviousDate(month + "-01", "month").substring(0, 7);

            Map<String, Object> cycleResult = aggregatedCycleResults.get(cycleMonth);

            for (ApiResponseDTO param : responseParams) {
                if (param.getNeedCycleCrc()) {
                    String paramName = param.getParamName();
                    Object originalValue = originalResult.get(paramName);
                    Object cycleValue = cycleResult != null ? cycleResult.get(paramName) : null;

                    if (cycleValue != null && originalValue instanceof Number && cycleValue instanceof Number) {
                        double cycleRatio =
                            ((Number)originalValue).doubleValue() / ((Number)cycleValue).doubleValue() - 1;
                        originalResult.put(paramName + "_cycle", cycleRatio);
                    } else {
                        originalResult.put(paramName + "_cycle", null);
                    }
                }
            }
        }

        return new ArrayList<>(aggregatedOriginalResults.values());
    }

    private Map<String, Map<String, Object>> aggregateResultsByMonth(List<Map<String, Object>> results,
        List<ApiResponseDTO> responseParams) {
        Map<String, Map<String, Object>> aggregatedResults = new HashMap<>();

        for (Map<String, Object> result : results) {
            String dt = (String)result.get("dt");
            // 提取月份
            String month = dt.substring(0, 7);

            Map<String, Object> aggregate = aggregatedResults.getOrDefault(month, new HashMap<>());
            for (ApiResponseDTO param : responseParams) {
                String paramName = param.getParamName();
                Object value = result.get(paramName);

                if (value instanceof Number) {
                    aggregate.put(paramName,
                        ((Number)aggregate.getOrDefault(paramName, 0)).doubleValue() + ((Number)value).doubleValue());
                } else {
                    aggregate.put(paramName, value);
                }
            }

            aggregatedResults.put(month, aggregate);
        }

        return aggregatedResults;
    }

    private String calculatePreviousDate(String date, String dateType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
            switch (dateType) {
                case "year":
                    calendar.add(Calendar.YEAR, -1);
                    break;
                case "month":
                    calendar.add(Calendar.MONTH, -1);
                    break;
                case "day":
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid dateType for cycle calculation: " + dateType);
            }
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + date, e);
        }
    }

}
