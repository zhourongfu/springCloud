package com.weilus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages="com.feign.clients")
@ComponentScan({"com.weilus","com.feign.fallbacks"})
@Controller
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	TestProperties properties;

//	@RequestMapping("/me")
//	@PreAuthorize("#oauth2.hasScope('oauth-test')")
//	public @ResponseBody Object getUser(){
//		return SecurityContextHolder.getContext().getAuthentication();
//	}
//
//	@RequestMapping("/client")
//	@PreAuthorize("#oauth2.hasScope('resource')")
//	public @ResponseBody Object client(){
//		return SecurityContextHolder.getContext().getAuthentication();
//	}

	@GetMapping("test")
	public @ResponseBody Object test(){
		return properties;
	}
}
