package com.weilus;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

import static com.weilus.Test1Controller.LOGGER;

/**
 * Created by liutq on 2018/12/12.
 */
@Configuration
@ConditionalOnProperty(name = "spring.application.name",havingValue = "feign-service")
@RestController
public class Test2Controller {

    @RequestMapping(value="sayHello",method= RequestMethod.POST)
    public String sayHello(HttpServletRequest request, @RequestBody Map<String, String> map){
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String headerName = enumeration.nextElement();
            String header = request.getHeader(headerName);
            LOGGER.info("feign-service header [{}] : {}",headerName,header);
        }
        return "feign-service: Hello "+map.get("name");
    }

    @RequestMapping(value="hiMan",method=RequestMethod.POST)
    public String hiMan(@RequestBody Map<String, String> map){
        return "feign-service: Hi "+map.get("name");
    }

}
