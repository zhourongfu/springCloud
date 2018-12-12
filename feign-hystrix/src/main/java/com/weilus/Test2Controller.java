package com.weilus;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by liutq on 2018/12/12.
 */
@Configuration
@ConditionalOnProperty(name = "spring.application.name",havingValue = "feign-service")
@RestController
public class Test2Controller {

    @RequestMapping(value="sayHello",method= RequestMethod.POST)
    public String sayHello(@RequestBody Map<String, String> map){
        return "feign-service: Hello "+map.get("name");
    }

    @RequestMapping(value="hiMan",method=RequestMethod.POST)
    public String hiMan(@RequestBody Map<String, String> map){
        return "feign-service: Hi "+map.get("name");
    }

}
