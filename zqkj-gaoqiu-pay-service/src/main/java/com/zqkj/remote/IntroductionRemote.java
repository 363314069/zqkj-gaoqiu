package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "business-Service",path = "/business/introduction")
public interface IntroductionRemote {

    @RequestMapping(value = "/selectone", method = RequestMethod.GET)
    public String selectOne(@RequestBody String guid);
}
