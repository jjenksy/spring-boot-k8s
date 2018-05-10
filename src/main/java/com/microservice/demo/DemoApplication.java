package com.microservice.demo;

import com.microservice.demo.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * THe @SpringCloudApplication annotation replaces the old @SpringBootApplciation annotation and add
 * the EnableDiscoveryClient and the @EnableCircuitBreaker
 */
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
@RibbonClient(name = "name-service", configuration = RibbonConfiguration.class)
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
