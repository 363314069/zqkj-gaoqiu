package com.zqkj.remote.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "ADMIN-SERVICE", path = "/security/user")
public interface UserService {
	
	@RequestMapping(value = "/selectOne", method = RequestMethod.GET)
	public String selectOne(@RequestBody String guid);
}
