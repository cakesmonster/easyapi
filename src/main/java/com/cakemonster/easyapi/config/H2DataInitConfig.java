package com.cakemonster.easyapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Date;

/**
 * H2DataInitConfig
 *
 * @author cakemonster
 * @date 2024/7/27
 */
// @Configuration
public class H2DataInitConfig {

    private JdbcTemplate jdbcTemplate;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

    @PostConstruct
    public void init() throws Exception {
        // 创建表
        jdbcTemplate.execute(
            "CREATE TABLE employees (" + "id INT AUTO_INCREMENT PRIMARY KEY," + "first_name VARCHAR(255),"
                + "last_name VARCHAR(255)," + "salary DOUBLE," + "hire_date DATE," + "department_id BIGINT)");

        // 插入数据
        jdbcTemplate.update(
            "INSERT INTO employees (first_name, last_name, salary, hire_date, department_id) VALUES (?, ?, ?, ?, ?)",
            "John", "Doe", 50000.0, Date.valueOf("2024-01-01"), 101);
        jdbcTemplate.update(
            "INSERT INTO employees (first_name, last_name, salary, hire_date, department_id) VALUES (?, ?, ?, ?, ?)",
            "Jane", "Smith", 60000.0, Date.valueOf("2023-05-15"), 102);
        jdbcTemplate.update(
            "INSERT INTO employees (first_name, last_name, salary, hire_date, department_id) VALUES (?, ?, ?, ?, ?)",
            "Mike", "Johnson", 55000.0, Date.valueOf("2024-02-28"), 101);
        jdbcTemplate.update(
            "INSERT INTO employees (first_name, last_name, salary, hire_date, department_id) VALUES (?, ?, ?, ?, ?)",
            "Sarah", "Williams", 62000.0, Date.valueOf("2023-08-01"), 103);
    }
}
