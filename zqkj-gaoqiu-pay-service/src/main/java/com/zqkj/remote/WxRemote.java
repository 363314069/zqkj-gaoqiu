package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "admin-service", path = "/security/wxoauth")
public interface WxRemote {
	
	@RequestMapping(value = "/getaccesstoken", method = RequestMethod.GET)
	public String getAccessToken();
}
