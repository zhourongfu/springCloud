package com.weilus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Created by liutq on 2018/5/16.
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class TurbineApplication {
    public static void  main(String[] args) {
        SpringApplication.run(TurbineApplication.class,args);
    }
}
