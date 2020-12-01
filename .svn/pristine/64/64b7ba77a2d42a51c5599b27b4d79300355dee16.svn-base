package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "BUSINESS-SERVICE", path = "/business/couponsuser")
public interface CouponsRemote {
	
	@RequestMapping(value = "/membersbin", method = RequestMethod.POST)
	public String membersBin(@RequestBody String jsonStr);
}
