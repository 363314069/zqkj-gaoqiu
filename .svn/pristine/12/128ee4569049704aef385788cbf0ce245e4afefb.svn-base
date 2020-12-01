package com.zqkj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

//@ServletComponentScan(basePackages = {"com.sunvalley.demo.config"})	// 扫描servlet和filter
@SpringBootApplication
//@EnableCircuitBreaker
//@EnableFeignClients
//@EnableCaching		// 开启缓存
//@EnableDiscoveryClient
@EnableZuulProxy
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
