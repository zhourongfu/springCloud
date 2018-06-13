package serviceCustomer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.feign.FeignClientService;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;
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


	@RequestMapping("test1")
//	@PreAuthorize("#oauth2.hasScope('read') and hasRole('ROLE_ADMIN')")
	@RolesAllowed("ROLE_ADDccc")
	public Object test1(Principal user){
		logger.info(user.toString());
		return Collections.singletonMap("result","ServiceA TEST1 called!");
	}

	@RequestMapping("test2")
	@PreAuthorize("hasRole('ROLE_QUERY')")
	public Object test2(Principal user){
		logger.info(user.getName());
		return Collections.singletonMap("result","ServiceA TEST2 called!");
	}
}
