package serviceCustomer.hystrix;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import service.feign.FeignClientService;

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
	 }
}
