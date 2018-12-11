package com.weilus;

import com.feign.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	FeignClientService service;

	@RequestMapping("test/sayHello")
	public String sayHello(){
		return service.sayHello(Collections.singletonMap("name", "jhon"));
	}

	@RequestMapping("test/hiMan")
	public String hiMan(){
		return service.hiMan(Collections.singletonMap("name", "jhon"));
	}
}
