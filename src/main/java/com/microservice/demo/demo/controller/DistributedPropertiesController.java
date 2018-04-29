package com.microservice.demo.demo.controller;


import com.microservice.demo.demo.service.DemoServerService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@Slf4j
public class DistributedPropertiesController {


    HelloClient client;

    private final DemoServerService demoServerService;




    public DistributedPropertiesController(HelloClient helloClient, DemoServerService demoServerService) {
        this.client = helloClient;
        this.demoServerService = demoServerService;

    }

    @GetMapping("/get_instance/")
    public String serviceUrl() {

        return demoServerService.getUrlFromEureka();
    }




    @GetMapping("/get-from-demo-server")
    public String getFromDemoServerEndpoint(){
        return this.demoServerService.getFromDemoServer();

    }






    @Value("${external.value:fallback}")
    private String value;


    @GetMapping("/upper_name/{name}")
    public String getConfigFromValue(@PathVariable("name") String name) {
        log.error("Value {}", name);
        return name.toUpperCase() + value;
    }

    @GetMapping("/test-new")
    public String newDeployTest() {

        return "{ \"name\":\"this is a new deploy\"}";
    }

    @GetMapping("/discovery_client")
    public String discoveryPing() {
        return client.hello();
    }


    //todo implement server to talk to
    @FeignClient("demo-server")
    interface HelloClient {
        @RequestMapping(value = "/", method = GET)
        String hello();
    }
}
