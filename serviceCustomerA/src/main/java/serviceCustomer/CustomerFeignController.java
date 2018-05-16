package serviceCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.feign.FeignClientService;

import java.util.Collections;
import java.util.Map;

@RestController
public class CustomerFeignController {
	
	@Autowired
	FeignClientService service;
	
	@RequestMapping("test")
	public String test(String s){
		Map<String,String> params = Collections.singletonMap("name", "jhon");
		if("1".equals(s))
		return service.sayHello(params);
		else return service.hiMan(params);
	}
	
}
