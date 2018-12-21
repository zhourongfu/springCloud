package com.weilus;

import com.feign.clients.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by liutq on 2018/12/12.
 */

@Configuration
@ConditionalOnProperty(name = "spring.application.name",havingValue = "feign-call")
@RestController
public class Test1Controller {

    @Autowired
    FeignClientService service;

    @RequestMapping("test/sayHello")
    public String sayHello(){
        return service.sayHello(Collections.singletonMap("name", "jhon"));
    }

    @RequestMapping("test/hiMan")
    public String hiMan(){
        return service.hiMan(Collections.singletonMap("name", "jhon"));
    }

}