package com.pepper.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //排除DataSourceConfiguratrion
@EnableTransactionManagement(proxyTargetClass = true)   //开启事物管理功能


public class ShardingMain {

    public static void main(String[] args) {
        SpringApplication.run(ShardingMain.class, args);
    }

    @Bean
    public  RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
