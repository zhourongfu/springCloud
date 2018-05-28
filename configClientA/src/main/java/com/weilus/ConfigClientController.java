package com.weilus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class ConfigClientController {
	
	@Value("${ltaiq.name:刘德华}")
	private String name;
	@Value("${ltaiq.age:50}")
	private int age;


	@RequestMapping("/test")
	public Object ss(){
		return Collections.singletonMap(name,age);
	}
}
