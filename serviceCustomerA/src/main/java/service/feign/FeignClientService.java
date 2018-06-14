package service.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.weilus.serviceCustomer.hystrix.FeignConfig;

import java.util.Map;

@FeignClient(name="serviceProducer",fallback=FeignConfig.FallBackLocal.class,configuration=FeignConfig.class)
public interface FeignClientService {
	
	@RequestMapping(value="sayHello",method=RequestMethod.POST)
	public String sayHello(@RequestBody Map<String,String> map);

	@RequestMapping(value="hiMan",method=RequestMethod.POST)
	public String hiMan(@RequestBody Map<String,String> map);
}
