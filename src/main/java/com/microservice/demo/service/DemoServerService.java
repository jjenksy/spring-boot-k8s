package com.microservice.demo.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoServerService {

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    public DemoServerService(RestTemplate restTemplate, EurekaClient eurekaClient) {
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
    }


    public InstanceInfo getNextServerFromEureka(String s, boolean b) {
        return this.eurekaClient.getNextServerFromEureka(s, b);
    }


    /**
     * THis method implements circuitbreaker so if the call fails if then fails over to the method Identified in the
     * HystrixCommand fallbackMethod
     * @return
     */
    @HystrixCommand(fallbackMethod = "defaultDemoServer")
    public String getFromDemoServer() {
        return restTemplate.getForObject(getUrlFromEureka(), String.class);
    }

    /**
     * This method is called if the remote server is not available
     * @return
     */
    public String defaultDemoServer(){
        return "Circuit breaker worked!! ";
    }

    public String getUrlFromEureka() {
            InstanceInfo instance = getNextServerFromEureka("DEMO-SERVER", false);
            return instance.getHomePageUrl();
    }
}
