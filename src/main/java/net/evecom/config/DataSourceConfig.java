package net.evecom.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 */
@Configuration
public class DataSourceConfig {

    //主数据源配置 ds1数据源
    @Primary
//    @Bean(name = "ds1DataSourceProperties")
    @Bean(name = "ds1DataSource",destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
//    public DataSourceProperties ds1DataSourceProperties() {
    public DruidDataSource ds1DataSourceProperties() {
        return new DruidDataSource();
    }

    //主数据源 ds1数据源
//    @Primary
//    @Bean(name = "ds1DataSource")
//    public DataSource ds1DataSource(@Qualifier("ds1DataSourceProperties") DataSourceProperties dataSourceProperties) {
//        return  dataSourceProperties.initializeDataSourceBuilder().build();
//    }

    //第二个ds2数据源配置
//    @Bean(name = "ds2DataSourceProperties")
    @Bean(name = "ds2DataSource",destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DruidDataSource ds2DataSourceProperties() {
        return new DruidDataSource();
    }

    //第二个ds2数据源
//    @Bean("ds2DataSource")
//    public DataSource ds2DataSource(@Qualifier("ds2DataSourceProperties") DataSourceProperties dataSourceProperties) {
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }

}
