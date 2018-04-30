package com.microservice.demo.controller;


import com.microservice.demo.service.DemoServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@RestController
@Slf4j
@RequestMapping("/demo")
@Api(value="microservice-demo", description="Operations for spring boot microservice demo")
public class DistributedPropertiesController {


    HelloClient client;

    private final DemoServerService demoServerService;




    public DistributedPropertiesController(HelloClient helloClient, DemoServerService demoServerService) {
        this.client = helloClient;
        this.demoServerService = demoServerService;

    }



    @GetMapping("/get_instance/")
    @ApiOperation(value = "Get the url for the demo service instance we are talking to from Eureka", response = String.class)
    @ApiResponses(value  = {
            @ApiResponse(code = 200, message = "Successfully retrieved url"),
    })
    public String serviceUrl() {

        return demoServerService.getUrlFromEureka();
    }



    @ApiOperation(value = "Get the value response from the demo server we are talking to", response = String.class)
    @ApiResponses(value  = {
            @ApiResponse(code = 200, message = "Successfully retrieved response"),
    })
    @GetMapping("/get-from-demo-server")
    public String getFromDemoServerEndpoint(){
        return this.demoServerService.getFromDemoServer();

    }

    @Value("${external.value:fallback}")
    private String value;

    @ApiOperation(value = "Get the value that is being inject by consul and the param you pass to uppercase", response = String.class)
    @ApiResponses(value  = {
            @ApiResponse(code = 200, message = "Successfully retrieved value"),
    })
    @GetMapping("/upper_name/{name}")
    public String getConfigFromValue(@PathVariable("name") String name) {
        log.error("Value {}", name);
        return name.toUpperCase() + value;
    }

    @ApiOperation(value = "This api endpoint returns the same as /get-from-demo-server\" but it uses the netflix OSS feignclient to make the call instead of restTemplate ", response = String.class)
    @ApiResponses(value  = {
            @ApiResponse(code = 200, message = "Successfully retrieved value from demo server"),
    })
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
