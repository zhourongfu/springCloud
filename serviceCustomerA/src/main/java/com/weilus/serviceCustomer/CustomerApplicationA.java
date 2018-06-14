package com.weilus.serviceCustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages="service.feign")
@EnableHystrix
public class CustomerApplicationA {
	
	public static void main(String[] args) {
	    SpringApplication.run(CustomerApplicationA.class, args);
	}
	
}
