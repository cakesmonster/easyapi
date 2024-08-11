package com.cakemonster.easyapi.process;

import com.alibaba.fastjson2.JSON;
import com.cakemonster.easyapi.model.DatabaseColumn;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ResultTransformProcessor
 *
 * @author cakemonster
 * @date 2024/8/11
 */
public class ResultTransformProcessor {

    public static List<Map<String, Object>> transformWithLineListSort(List<Map<String, DatabaseColumn>> data,
        String showType, String subjectKey, String sortField, String sortRule, String sortType) {

        // 只有 line_list 需要传递 sort 参数
        if (!"line_list".equalsIgnoreCase(showType)) {
            throw new IllegalArgumentException("Sorting parameters are only applicable for 'line_list' showType.");
        }

        return transformData(data, showType, subjectKey, sortField, sortRule, sortType);
    }

    public static List<Map<String, Object>> transform(List<Map<String, DatabaseColumn>> data, String showType,
        String subjectKey) {

        // 不能传递 sorting 参数
        return transformData(data, showType, subjectKey, null, null, null);
    }

    private static List<Map<String, Object>> transformData(List<Map<String, DatabaseColumn>> data, String showType,
        String subjectKey, String sortField, String sortRule, String sortType) {
        // 根据 showType 进行转换
        switch (showType) {
            case "overview":
            case "pie":
                return transformOverviewOrPie(data);
            case "line":
            case "bar":
                return transformLineOrBar(data);
            case "line_list":
                return transformLineListData(data, subjectKey, sortField, sortRule, sortType);
            default:
                throw new IllegalArgumentException("Unknown showType: " + showType);
        }
    }

    private static List<Map<String, Object>> transformOverviewOrPie(List<Map<String, DatabaseColumn>> data) {
        return data.stream().map(row -> row.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getValue())))
            .collect(Collectors.toList());
    }

    private static List<Map<String, Object>> transformLineOrBar(List<Map<String, DatabaseColumn>> data) {
        Map<String, List<Object>> groupedData = new HashMap<>();

        for (Map<String, DatabaseColumn> row : data) {
            for (Map.Entry<String, DatabaseColumn> entry : row.entrySet()) {
                groupedData.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).add(entry.getValue().getValue());
            }
        }

        List<Map<String, Object>> result = new ArrayList<>();
        groupedData.forEach((key, value) -> {
            Map<String, Object> map = new HashMap<>();
            map.put(key, value);
            result.add(map);
        });

        return result;
    }

    private static List<Map<String, Object>> transformLineListData(List<Map<String, DatabaseColumn>> data,
        String subjectKey, String sortField, String sortRule, String sortType) {
        // 首先根据 subjectKey 进行分组
        Map<Object, List<Map<String, DatabaseColumn>>> groupedData =
            data.stream().collect(Collectors.groupingBy(row -> row.get(subjectKey).getValue()));

        List<Map.Entry<Object, List<Map<String, DatabaseColumn>>>> groupedEntries =
            new ArrayList<>(groupedData.entrySet());

        if (StringUtils.isNotEmpty(sortType)) {
            sortGroupSubjectDataList(sortField, sortRule, sortType, groupedEntries);
        }

        // 按照排序后的分组顺序生成结果
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map.Entry<Object, List<Map<String, DatabaseColumn>>> entry : groupedEntries) {
            Map<String, Object> groupedMap = new HashMap<>();
            groupedMap.put(subjectKey, entry.getKey());
            groupedMap.put("series", entry.getValue().stream().map(row -> row.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, columnEntry -> columnEntry.getValue().getValue())))
                .collect(Collectors.toList()));
            result.add(groupedMap);
        }
        return result;
    }

    private static void sortGroupSubjectDataList(String sortField, String sortRule, String sortType,
        List<Map.Entry<Object, List<Map<String, DatabaseColumn>>>> groupedEntries) {
        // 根据 sortType 进行排序
        if ("latest".equalsIgnoreCase(sortType)) {
            // 根据分组后的最新值排序
            groupedEntries.sort((entry1, entry2) -> {
                Map<String, DatabaseColumn> lastRow1 = entry1.getValue().get(entry1.getValue().size() - 1);
                Map<String, DatabaseColumn> lastRow2 = entry2.getValue().get(entry2.getValue().size() - 1);
                return compareDatabaseColumnValues(lastRow1.get(sortField), lastRow2.get(sortField), sortRule);
            });
        } else if ("acc".equalsIgnoreCase(sortType)) {
            // 根据分组后的累加值排序
            groupedEntries.sort((entry1, entry2) -> {
                BigDecimal sum1 =
                    entry1.getValue().stream().map(row -> new BigDecimal(row.get(sortField).getValue().toString()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal sum2 =
                    entry2.getValue().stream().map(row -> new BigDecimal(row.get(sortField).getValue().toString()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return "asc".equalsIgnoreCase(sortRule) ? sum1.compareTo(sum2) : sum2.compareTo(sum1);
            });
        }
    }

    private static int compareDatabaseColumnValues(DatabaseColumn col1, DatabaseColumn col2, String sortRule) {
        Comparable value1 = (Comparable)col1.getValue();
        Comparable value2 = (Comparable)col2.getValue();
        return "asc".equalsIgnoreCase(sortRule) ? value1.compareTo(value2) : value2.compareTo(value1);
    }

    public static void main(String[] args) {
        // 示例数据
        List<Map<String, DatabaseColumn>> data = new ArrayList<>();

        // 第1行数据
        Map<String, DatabaseColumn> row1 = new HashMap<>();
        row1.put("pv", new DatabaseColumn("pv", Integer.class, 100));
        row1.put("uv", new DatabaseColumn("uv", Integer.class, 50));
        row1.put("userId", new DatabaseColumn("userId", String.class, "user1"));
        data.add(row1);

        // 第2行数据
        Map<String, DatabaseColumn> row2 = new HashMap<>();
        row2.put("pv", new DatabaseColumn("pv", Integer.class, 500));
        row2.put("uv", new DatabaseColumn("uv", Integer.class, 100));
        row2.put("userId", new DatabaseColumn("userId", String.class, "user2"));
        data.add(row2);

        // 第3行数据
        Map<String, DatabaseColumn> row3 = new HashMap<>();
        row3.put("pv", new DatabaseColumn("pv", Integer.class, 150));
        row3.put("uv", new DatabaseColumn("uv", Integer.class, 75));
        row3.put("userId", new DatabaseColumn("userId", String.class, "user1"));
        data.add(row3);

        List<Map<String, Object>> resultLineList = transform(data, "line_list", "userId");

        // 打印结果
        System.out.println("PRE Line List: " + JSON.toJSONString(resultLineList));

        List<Map<String, Object>> maps = transformWithLineListSort(data, "line_list", "userId", "pv", "desc", "acc");
        // 打印结果
        System.out.println("L Line List: " + JSON.toJSONString(maps));

    }
}
