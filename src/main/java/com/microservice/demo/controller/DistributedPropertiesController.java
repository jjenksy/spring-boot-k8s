package com.microservice.demo.controller;


import com.microservice.demo.service.DemoServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@Slf4j
@RequestMapping("/demo")
@RefreshScope
@Api(value="microservice-demo", description="Operations for spring boot microservice demo")
public class DistributedPropertiesController {




    private final DemoServerService demoServerService;

    @Value("${external.value:fallback}")
    private String value;


    public DistributedPropertiesController(DemoServerService demoServerService) {
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



    @ApiOperation(value = "Get the value that is being inject by consul and the param you pass to uppercase", response = String.class)
    @ApiResponses(value  = {
            @ApiResponse(code = 200, message = "Successfully retrieved value"),
    })
    @GetMapping("/consul_value/}")
    public String getConfigFromValue() {

        return value;
    }

    @ApiOperation(value = "This api endpoint returns the same as /get-from-demo-server\" but it uses the netflix OSS feignclient to make the call instead of restTemplate ", response = String.class)
    @ApiResponses(value  = {
            @ApiResponse(code = 200, message = "Successfully retrieved value from demo server"),
    })
    @GetMapping("/feign_client")
    public String discoveryPing() {
        return demoServerService.feignClient();
    }



}
