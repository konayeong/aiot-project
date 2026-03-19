package com.nhnacademy.shoppingmall.config;

import com.nhnacademy.shoppingmall.RootBase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

// 비웹 계층 (service, repository..) 빈 등록
@EnableTransactionManagement
@ComponentScan(
        basePackageClasses = {RootBase.class},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = {Controller.class}
        )
)
public class RootConfig {
    @Bean(name = "dataSource")
    public DataSource dataSource() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://s4.java21.net:13306/nhn_academy_3");
        dataSource.setUsername("nhn_academy_3");
        dataSource.setPassword("Jc1?tjnLZWi7LTIH");

        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(5);
        dataSource.setMaxIdle(5);
        dataSource.setMinIdle(5);

        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
