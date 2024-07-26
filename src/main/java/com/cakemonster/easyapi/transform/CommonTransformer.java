package com.cakemonster.easyapi.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据结果转换
 *
 * @author cakemonster
 * @date 2024/7/27
 */
public class CommonTransformer {

    public static List<Map<String, Object>> list(List<Map<String, Object>> originalData) {
        return originalData;
    }

    public static Map<String, List<Object>> trend(List<Map<String, Object>> originalData) {
        Map<String, List<Object>> chartData = new HashMap<>();
        for (Map<String, Object> row : originalData) {
            row.forEach((alias, value) -> {
                chartData.computeIfAbsent(alias, k -> new ArrayList<>()).add(value);
            });
        }
        return chartData;
    }

    public static Map<String, Double> pieChart(List<Map<String, Object>> originalData) {
        Map<String, Double> pieData = new HashMap<>();
        for (Map<String, Object> row : originalData) {
            row.forEach((alias, value) -> {
                if (value instanceof Number) {
                    pieData.merge(alias, ((Number)value).doubleValue(), Double::sum);
                }
            });
        }
        return pieData;
    }
}
