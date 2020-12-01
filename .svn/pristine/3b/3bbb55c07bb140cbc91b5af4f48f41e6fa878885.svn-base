package com.zqkj.remote.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "PAY-SERVICE", path = "/pay/order")
public interface OrderService {
	
	@RequestMapping(value = "/selectOne", method = RequestMethod.GET)
	public String selectOne(@RequestBody String guid);

	@RequestMapping(value = "/selectOneExtend", method = RequestMethod.GET)
	public String selectOneExtend(@RequestBody String orderGuid);
}
