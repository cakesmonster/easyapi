package com.cakemonster.easyapi.helper;

import com.cakemonster.easyapi.parse.MybatisSqlParser;
import com.github.pagehelper.Page;
import com.github.pagehelper.parser.SqlParser;
import com.github.pagehelper.parser.SqlParserUtil;
import com.github.pagehelper.util.SqlSafeUtil;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

import java.util.*;

/**
 * PageHelper
 *
 * @author cakemonster
 * @date 2024/7/27
 */
public class PageHelper {

    public static void main(String[] args) {
        String xml =
            "        SELECT first_name AS firstName, last_name AS lastName, salary, department_id as departmentId\n"
                + "        FROM employees\n" + "        WHERE first_name = #{firstName} \n"
                + "          AND last_name = #{lastName}\n" + "          AND salary >= #{minSalary}\n"
                + "          AND department_id IN\n"
                + "          <foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">\n"
                + "              #{item}\n" + "          </foreach> limit 2, 10";

        Map<String, Object> conditions = new HashMap<>();
        conditions.put("firstName", "John");
        conditions.put("lastName", "Doe");
        conditions.put("minSalary", 50000.0);

        List<Long> list = new ArrayList<>();
        list.add(101L);
        list.add(102L);
        list.add(103L);
        conditions.put("list", list);

        MybatisSqlParser parser = new MybatisSqlParser();
        SqlSource sqlSource = parser.parse(xml);
        BoundSql boundSql = sqlSource.getBoundSql(conditions);

        com.github.pagehelper.PageHelper pageHelper = new com.github.pagehelper.PageHelper();
        Properties properties = new Properties();
        // 根据实际数据库类型设置
        properties.setProperty("helperDialect", "mysql");
        pageHelper.setProperties(properties);
        com.github.pagehelper.PageHelper.startPage(2, 10);
        String countSql = pageHelper.getCountSql(null, boundSql, conditions, null, null);
        String pageSql = pageHelper.getPageSql(null, boundSql, conditions, null, null);

        System.out.println(countSql);
        System.out.println(pageSql);
    }

}
