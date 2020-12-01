package com.zqkj.bean;

import lombok.Data;

@Data
public class CouponsExchangeBean {
    private String userGuid; // 用户GUID
    private String reduceCouponsGuid; // 要减少优惠券的GUID
    private Integer reduceCouponsSum; // 要减少优惠券的数量
    private String addCouponsGuid; // 要增加优惠券的GUID
    private Integer addCouponsSum; // 要增加优惠券的数量
}