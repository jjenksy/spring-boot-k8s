package com.microservice.demo.controller;


import com.microservice.demo.config.ConfigMap;
import com.microservice.demo.service.DemoServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/demo")
@Api(value="microservice-demo", description="Operations for spring boot microservice demo")
public class DistributedPropertiesController {

    private final DemoServerService demoServerService;


    private ConfigMap config;

    public DistributedPropertiesController(DemoServerService demoServerService, ConfigMap config) {
        this.demoServerService = demoServerService;
        this.config = config;
    }



    @GetMapping("/get_services/")
    @ApiResponses(value  = {
            @ApiResponse(code = 200, message = "Successfully retrieved url"),
    })
    public List<String> serviceUrl() {

        return demoServerService.getUrlFromDiscoveryClient();
    }

    @GetMapping("/get-demo-server")
    public String getFromDemoServer(){
        return demoServerService.getFromDemoServer();
    }


    @GetMapping("/config-map")
    public String getConfigData(){
        return this.config.getMessage();
    }


}
