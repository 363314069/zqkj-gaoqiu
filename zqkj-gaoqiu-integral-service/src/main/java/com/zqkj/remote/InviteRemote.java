package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "admin-Service",path = "")
public interface InviteRemote {

    @RequestMapping(value = "/security/invite/selectinviter", method = RequestMethod.POST)
    public String selectInviter(@RequestBody String userGuid);

    @RequestMapping(value = "/security/user/selectOne", method = RequestMethod.GET)
    public String selectOne(@RequestBody String guid);
}
