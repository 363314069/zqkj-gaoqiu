package com.zqkj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zqkj on 2019/10/24.
 */
@FeignClient(value = "business-Service",path = "/business/couponsuser")
public interface CouponsuserRemote {

    @RequestMapping(value = "/orderbindingcoupons", method = RequestMethod.POST)
    public String payOrderBindingCoupons(@RequestBody String jsonStr);

    @RequestMapping(value = "/cancelordercoupons", method = RequestMethod.POST)
    public String cancelOrderCoupons(@RequestBody String orderGuid);
}
