package com.weilus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.weilus","com.feign.fallbacks"})
@EnableEurekaClient
@EnableFeignClients(basePackages="com.feign.clients")
@EnableHystrix
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
