package com.weilus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * Created by liutq on 2018/6/7.
 */
@RestController
public class ProduceController {

    public static final Logger log = LoggerFactory.getLogger(ProducerApplication.class);
    @RequestMapping(value="sayHello",method= RequestMethod.POST)
    public String sayHello(@RequestBody Map<String,String> map, HttpServletRequest request){
        Map<String, String> hreads = getHeaders(request);
        log.info("feign 调用传递链路 请求头==>"+hreads);
//		try {
//			System.out.println("模拟服务阻塞....");
//			Thread.sleep(10000l);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        log.info("sayHello服务被消费。。。。。。。。。。。。。。。");
        return "hello"+map.get("name");
    }

    @RequestMapping(value="hiMan",method=RequestMethod.POST)
    @RolesAllowed("CLIENT_A")
    public String hiMan(@RequestBody Map<String,String> map){
        log.info("hiMan服务被消费。。。。。。。。。。。。。。。");
        return "hello"+map.get("name");
    }

    @RequestMapping(value="hi",method=RequestMethod.POST)
    public String apiGateWay(@RequestBody Map<String,String> map){
        return "hello"+map.get("name");
    }




    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
