package com.microservice.demo.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Service
public class DemoServerService {

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;
    private final HelloClient helloClient;

    public DemoServerService(RestTemplate restTemplate, EurekaClient eurekaClient, HelloClient helloClient) {
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
        this.helloClient = helloClient;
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


    /**
     * This method is the exact same as the restTemplate method it just uses the Netflix OSS feign client insteadc
     * @return
     */
    @HystrixCommand(fallbackMethod = "defaultDemoServer")
    public String feignClient() {
        return helloClient.hello();
    }


    //todo implement server to talk to
    @FeignClient("demo-server")
    interface HelloClient {
        @RequestMapping(value = "/", method = GET)
        String hello();
    }
}
