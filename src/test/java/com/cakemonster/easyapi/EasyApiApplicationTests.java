package com.cakemonster.easyapi;

import com.alibaba.fastjson.JSON;
import com.cakemonster.easyapi.execute.DefaultSqlExecute;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class EasyApiApplicationTests {

    private static JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/admin?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("xxxxx");

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void test() throws Exception {
        // 有别名，无别名的情况
        String xml =
            "        SELECT first_name AS firstName, last_name AS lastName, salary, department_id as departmentId\n"
                + "        FROM employees\n" + "        WHERE first_name = #{firstName} \n"
                + "          AND last_name = #{lastName}\n" + "          AND salary >= #{minSalary}\n"
                + "          AND department_id IN\n"
                + "          <foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">\n"
                + "              #{item}\n" + "          </foreach>";

        Map<String, Object> conditions = new HashMap<>();
        conditions.put("firstName", "John");
        conditions.put("lastName", "Doe");
        conditions.put("minSalary", 50000.0);

        List<Long> list = new ArrayList<>();
        list.add(101L);
        list.add(102L);
        list.add(103L);
        conditions.put("list", list);

        DefaultSqlExecute execute = new DefaultSqlExecute(jdbcTemplate);
        List<Map<String, Object>> result = execute.executeSql(xml, conditions);

        Assertions.assertEquals(
            "[{\"firstName\":\"John\",\"lastName\":\"Doe\",\"departmentId\":101,\"salary\":50000.0}]",
            JSON.toJSONString(result));
    }

    @Test
    public void test1() {
        String xml =
            "SELECT \n" + "    department_id,\n" + "    AVG(salary) AS average_salary\n" + "FROM \n" + "    employees\n"
                + "GROUP BY \n" + "    department_id\n" + "ORDER BY \n" + "    average_salary DESC;";
        Map<String, Object> conditions = new HashMap<>();

        DefaultSqlExecute execute = new DefaultSqlExecute(jdbcTemplate);
        List<Map<String, Object>> result = execute.executeSql(xml, conditions);

        Assertions.assertEquals("[{\"department_id\":103,\"average_salary\":62000.0},{\"department_id\":102,\"average_salary\":60000.0},{\"department_id\":101,\"average_salary\":52500.0}]", JSON.toJSONString(result));
    }

}
