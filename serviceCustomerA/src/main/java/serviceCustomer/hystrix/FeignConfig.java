package serviceCustomer.hystrix;

import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import service.feign.FeignClientService;

import java.util.Map;

@Configuration
public class FeignConfig {
	
	 @Bean
	 public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
	    return new BasicAuthRequestInterceptor("fadffsedfsf","rrfdaDSFfesnmhf");
	 }
	 @Bean
	 Request.Options feignRequestOptions(){
		 return new Request.Options(10 * 1000, 7 * 1000);
	 }
	@Component
	public static class FallBackLocal implements FeignClientService{
	 	@Override
	 	public String sayHello(Map<String, String> map) {
	 		return "hello, im local say";
	 	}

		@Override
		public String hiMan(@RequestBody Map<String, String> map) {
			return "hello, local hi Man";
		}
	}
}
