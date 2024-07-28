package com.cakemonster.easyapi.mapper;

import java.util.List;
import java.util.Map;

/**
 * 测试用
 *
 * @author cakemonster
 * @date 2024/7/29
 */
public interface BusinessTableMapper {

    List<Map<String, Object>> selectData(String mybatisSql);

    List<Map<String, Object>> selectMonthlyData(String startDt, String previousMaxDt);

}
