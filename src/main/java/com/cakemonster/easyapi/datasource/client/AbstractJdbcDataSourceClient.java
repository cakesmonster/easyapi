package com.cakemonster.easyapi.datasource.client;

import com.cakemonster.easyapi.datasource.param.BaseConnectionParam;
import com.cakemonster.easyapi.enumration.DbType;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;

/**
 * JdbcDataSourceProvider
 *
 * @author cakemonster
 * @date 2024/8/3
 */
@Slf4j
public abstract class AbstractJdbcDataSourceClient implements DataSourceClient {

    private static final String COMMON_VALIDATION_QUERY = "select 1";
    protected BaseConnectionParam baseConnectionParam;
    protected HikariDataSource dataSource;
    protected JdbcTemplate jdbcTemplate;

    public AbstractJdbcDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        Preconditions.checkNotNull(baseConnectionParam);
        Preconditions.checkNotNull(dbType);
        this.baseConnectionParam = baseConnectionParam;
        checkValidationQuery(baseConnectionParam);
        initClient(baseConnectionParam, dbType);
        checkClient();
    }

    @Override
    public void checkClient() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            this.jdbcTemplate.execute(this.baseConnectionParam.getValidationQuery());
        } catch (Exception exception) {
            throw new RuntimeException("jdbc validation query failed", exception);
        } finally {
            log.info("Time to execute validation query: {} for {} ms", this.baseConnectionParam.getValidationQuery(),
                stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }

    @Override
    public void close() {
        log.info("do close dataSource {}.", baseConnectionParam.getDatabase());
        dataSource.close();
        this.jdbcTemplate = null;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    protected void initClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        this.dataSource = createDataSourcePool(baseConnectionParam, dbType);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    protected void checkValidationQuery(BaseConnectionParam baseConnectionParam) {
        if (StringUtils.isEmpty(baseConnectionParam.getValidationQuery())) {
            baseConnectionParam.setValidationQuery(COMMON_VALIDATION_QUERY);
        }
    }

    private HikariDataSource createDataSourcePool(BaseConnectionParam baseConnectionParam, DbType dbType) {
        HikariDataSource dataSource = new HikariDataSource();
        //        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //        loaderJdbcDriver(classLoader, baseConnectionParam, dbType);
        String drv = StringUtils.isBlank(baseConnectionParam.getDriverClassName()) ? dbType.getDriverClass() :
            baseConnectionParam.getDriverClassName();
        dataSource.setDriverClassName(drv);
        dataSource.setJdbcUrl(baseConnectionParam.getJdbcUrl());
        dataSource.setUsername(baseConnectionParam.getUser());
        dataSource.setPassword(baseConnectionParam.getPassword());

        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(50);
        dataSource.setConnectionTestQuery(baseConnectionParam.getValidationQuery());

        if (baseConnectionParam.getProperties() != null) {
            baseConnectionParam.getProperties().forEach(dataSource::addDataSourceProperty);
        }

        log.info("Creating HikariDataSource for {} success.", dbType.name());
        return dataSource;
    }

    private void loaderJdbcDriver(ClassLoader classLoader, BaseConnectionParam properties, DbType dbType) {
        String drv = StringUtils.isBlank(properties.getDriverClassName()) ? dbType.getDriverClass() :
            properties.getDriverClassName();
        try {
            final Class<?> clazz = Class.forName(drv, true, classLoader);
            final Driver driver = (Driver)clazz.newInstance();
            if (!driver.acceptsURL(properties.getJdbcUrl())) {
                log.warn("Jdbc driver loading error. Driver {} cannot accept url.", drv);
                throw new RuntimeException("Jdbc driver loading error.");
            }
        } catch (final Exception e) {
            log.warn("The specified driver not suitable.");
        }
    }
}
