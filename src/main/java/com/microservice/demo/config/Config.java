package com.microservice.demo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    /**
     * Creating the restTemplate as a bean allows us to inject it
     * where it is being used and adding the @LoadBalanced instructs
     * Netflix ribbon to wrap the restTemplate bean with loadBalancing advice
     * @return
     */
    @Bean
//    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
