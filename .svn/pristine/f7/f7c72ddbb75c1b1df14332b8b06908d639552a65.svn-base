package com.zqkj.remote;

import com.zqkj.bean.OrderVipBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "pay-Service",path = "/pay/order")
public interface OrderRemote {

    @RequestMapping(value = "/updatevipguid", method = {RequestMethod.POST})
    public String updateVipGuid(@RequestBody OrderVipBean orderVipBean);
}
