package com.xangqun.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.storage.mysql.v1.MySQLStorage;

import javax.sql.DataSource;

@Configuration
public class ZipkinConfig {

    @Bean
    @Autowired
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.newBuilder().datasource(datasource).executor(Runnable::run).build();
    }

}