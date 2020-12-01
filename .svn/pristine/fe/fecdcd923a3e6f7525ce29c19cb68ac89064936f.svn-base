package com.zqkj.controller.validata;

import com.zqkj.entity.VipCouponsEntity;
import com.zqkj.utils.*;

import java.util.HashMap;
import java.util.Map;

public class VipCouponsValidata extends BaseValidata<VipCouponsEntity> {

    public R list(VipCouponsEntity entity, String orderBy) {
        return null;
    }

    public R info(VipCouponsEntity entity) {
        return null;
    }

    public R update(VipCouponsEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(entity.getId() == null || StringUtil.isEmpty(entity.getGuid()))
            map.put("id", StatusCodeUtil.getMsg(Content.STATUS_CODE_5210));
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).putError(map);
        return null;
    }

    public R del(VipCouponsEntity entity) {

        return null;
    }

    public R delByIds(Long[] ids) {

        return null;
    }

    public R delByGuids(String[] guids) {

        return null;
    }

    public R page(PageUtil<VipCouponsEntity> page, VipCouponsEntity entity) {

        return null;
    }
}
