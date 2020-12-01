package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "INTEGRAL-SERVICE", path = "/integral/userextend")
public interface IntegralRemote {
	
	@RequestMapping(value = "/registergiving", method = RequestMethod.POST)
	public String registerGiving(@RequestBody String userGuid);

	@RequestMapping(value = "/binvip", method = RequestMethod.POST)
	public String binVip(@RequestBody String jsonStr);
}
