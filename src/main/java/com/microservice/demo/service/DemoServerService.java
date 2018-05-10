package com.microservice.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class DemoServerService {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public DemoServerService(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    public List<String> getUrlFromDiscoveryClient() {
            return this.discoveryClient.getServices();
    }


    @HystrixCommand(fallbackMethod = "getFallbackName", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public String getFromDemoServer(){
        return this.restTemplate.getForObject("http://demo-server/", String.class);
    }

    private String getFallbackName() {
        return "Fallback";
    }
}
