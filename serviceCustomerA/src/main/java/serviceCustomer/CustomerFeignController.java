package serviceCustomer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.feign.FeignClientService;

import java.util.Collections;
import java.util.Map;

@RestController
public class CustomerFeignController {
	private static Logger logger = LoggerFactory.getLogger(CustomerFeignController.class);
	@Autowired
	FeignClientService service;
	
	@RequestMapping("test")
	public String test(String s){
		Map<String,String> params = Collections.singletonMap("name", "jhon");
		logger.info("     ====>"+params);
		if("1".equals(s))
		return service.sayHello(params);
		else return service.hiMan(params);
	}


	@RequestMapping("testGrayDeploy")
	public Object testGrayDeploy(String s){
		return Collections.singletonMap("result","ServiceA called!");
	}
}
