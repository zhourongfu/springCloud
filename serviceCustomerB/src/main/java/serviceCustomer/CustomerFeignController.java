package serviceCustomer;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import service.feign.FeignClientService;

@RestController
public class CustomerFeignController {
	
	@Autowired
	FeignClientService service;
	
	@RequestMapping("test")
	public String test(){
		Map<String,String> params = Collections.singletonMap("name", "jhon");
		return service.sayHello(params);
	}
	
}
