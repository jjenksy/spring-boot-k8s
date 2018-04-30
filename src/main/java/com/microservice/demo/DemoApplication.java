package com.microservice.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * THe @SpringCloudApplication annotation replaces the old @SpringBootApplciation annotation and add
 * the EnableDiscoveryClient and the @EnableCircuitBreaker
 */
@SpringCloudApplication
@EnableFeignClients
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
