package com.feign;

import com.feign.hystrix.FeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name="feign-service",fallback=FeignConfig.FallBackLocal.class,configuration=FeignConfig.class)
public interface FeignClientService {

    @RequestMapping(value="sayHello",method=RequestMethod.POST)
    String sayHello(@RequestBody Map<String, String> map);

    @RequestMapping(value="hiMan",method=RequestMethod.POST)
    String hiMan(@RequestBody Map<String, String> map);
}
