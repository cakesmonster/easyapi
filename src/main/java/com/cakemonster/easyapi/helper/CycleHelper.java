//package com.cakemonster.easyapi.helper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Map;
//
///**
// * 环比
// *
// * @author cakemonster
// * @date 2024/7/28
// */
//@Service
//public class CycleHelper {
//
//    public double calculateRatio(String mybatisSql, String startDt, String endDt, String rangeType) throws Exception {
//        switch (rangeType) {
//            case "dayRange":
//                if (endDt != null) {
//                    return calculateDailyRatioForRange(mybatisSql, startDt, endDt);
//                } else {
//                    return calculateDailyRatioForSingleDate(mybatisSql, startDt);
//                }
//            case "monthRange":
//                return calculateMonthlyRatio(mybatisSql, startDt, endDt);
//            default:
//                throw new IllegalArgumentException("Invalid range type");
//        }
//    }
//
//    private double calculateDailyRatioForRange(String mybatisSql, String startDt, String endDt) throws Exception {
//        // 日期格式化工具
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        // 解析传入的最小日期
//        Calendar minDay = Calendar.getInstance();
//        minDay.setTime(sdf.parse(startDt));
//
//        // 解析传入的最大日期，并前推一天
//        Calendar maxDay = Calendar.getInstance();
//        maxDay.setTime(sdf.parse(endDt));
//        maxDay.add(Calendar.DAY_OF_YEAR, -1);
//        String previousMaxDt = sdf.format(maxDay.getTime());
//
//        // 查询当前日期范围的数据
//        List<Map<String, Object>> currentData = businessTableMapper.selectData(mybatisSql);
//
//        // 查询前一天的范围数据
//        List<Map<String, Object>> previousData = businessTableMapper.selectMonthlyData(startDt, previousMaxDt);
//
//        // 计算当前日期范围和前一天范围的总金额
//        double currentAmountTotal = currentData.stream().mapToDouble(data -> (Double)data.get("amount")).sum();
//        double previousAmountTotal = previousData.stream().mapToDouble(data -> (Double)data.get("amount")).sum();
//
//        // 计算环比增长率
//        if (previousAmountTotal == 0) {
//            throw new Exception("Previous day amount total is zero, cannot calculate ratio");
//        }
//
//        // 环比计算公式：(当前值 - 前一天值) / 前一天值
//        double dailyRatio = (currentAmountTotal - previousAmountTotal) / previousAmountTotal;
//
//        return dailyRatio;
//    }
//
//    private double calculateDailyRatioForSingleDate(String mybatisSql, String dt) throws Exception {
//        // 日期格式化工具
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        // 解析传入的日期
//        Calendar currentDay = Calendar.getInstance();
//        currentDay.setTime(sdf.parse(dt));
//
//        // 计算前一天的日期
//        currentDay.add(Calendar.DAY_OF_YEAR, -1);
//        String previousDay = sdf.format(currentDay.getTime());
//
//        // 查询当前日期的数据
//        List<Map<String, Object>> currentData = businessTableMapper.selectData(mybatisSql);
//
//        // 查询前一天的数据
//        List<Map<String, Object>> previousData = businessTableMapper.selectData(previousDay);
//
//        // 计算当前日期和前一天的总金额
//        double currentAmountTotal = currentData.stream().mapToDouble(data -> (Double)data.get("amount")).sum();
//        double previousAmountTotal = previousData.stream().mapToDouble(data -> (Double)data.get("amount")).sum();
//
//        // 计算环比增长率
//        if (previousAmountTotal == 0) {
//            throw new Exception("Previous day amount total is zero, cannot calculate ratio");
//        }
//
//        // 环比计算公式：(当前值 - 前一天值) / 前一天值
//        double dailyRatio = (currentAmountTotal - previousAmountTotal) / previousAmountTotal;
//
//        return dailyRatio;
//    }
//
//    private double calculateMonthlyRatio(String mybatisSql, String minDt, String maxDt) throws Exception {
//        // 日期格式化工具
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        // 解析传入的最小日期
//        Calendar minDay = Calendar.getInstance();
//        minDay.setTime(sdf.parse(minDt));
//
//        // 计算上一月的最小日期和最大日期
//        minDay.add(Calendar.MONTH, -1);
//        String previousMinDt = sdf.format(minDay.getTime());
//
//        Calendar maxDay = Calendar.getInstance();
//        maxDay.setTime(sdf.parse(minDt));
//        maxDay.add(Calendar.DAY_OF_YEAR, -1);
//        String previousMaxDt = sdf.format(maxDay.getTime());
//
//        // 查询当前月份的数据
//        //        List<Map<String, Object>> currentMonthData = businessTableMapper.selectMonthlyData(minDt, maxDt);
//        //        double currentMonthCounter = currentMonthData != null ? (Double)currentMonthData.get("monthCounter") : 0.0;
//        //        double currentMonthAmount = currentMonthData != null ? (Double)currentMonthData.get("monthAmount") : 0.0;
//        //
//        //        // 查询上一个月份的数据
//        //        List<Map<String, Object>> previousMonthData =
//        //            businessTableMapper.selectMonthlyData(previousMinDt, previousMaxDt);
//        //        double previousMonthCounter = previousMonthData != null ? (Double)previousMonthData.get("monthCounter") : 0.0;
//        //        double previousMonthAmount = previousMonthData != null ? (Double)previousMonthData.get("monthAmount") : 0.0;
//        //
//        //        // 计算环比增长率
//        //        if (previousMonthCounter == 0 || previousMonthAmount == 0) {
//        //            throw new Exception("Previous month data is zero, cannot calculate ratio");
//        //        }
//        //
//        //        double counterRatio = (currentMonthCounter - previousMonthCounter) / previousMonthCounter;
//        //        double amountRatio = (currentMonthAmount - previousMonthAmount) / previousMonthAmount;
//
//        return 0.0d;
//    }
//
//}
