package com.design.oa.utils;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.design.oa.mailDao"},sqlSessionFactoryRef = "test2SqlSessionFactory")
public class MailDataSourceConfig {
    @Bean(name="test2DataSource")
    @ConfigurationProperties(prefix ="spring.test2.datasource")
    public DataSource testDataSource(){
        return DataSourceBuilder.create().build();
    }
    @Bean(name="test2SqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("test2DataSource") DataSource dataSource)throws Exception{
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mailMapping/*.xml"));
        return bean.getObject();
    }
    @Bean(name = "test2TransactionManager")
    public DataSourceTransactionManager test2TransactionManager(@Qualifier("test2DataSource")DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean(name="test2SqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("test2SqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

