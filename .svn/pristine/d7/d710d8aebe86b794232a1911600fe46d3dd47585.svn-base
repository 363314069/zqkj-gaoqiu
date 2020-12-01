package com.zqkj.controller.validata;

import com.zqkj.entity.OrderExtendEntity;
import com.zqkj.utils.*;
import java.util.HashMap;
import java.util.Map;

public class OrderExtendValidata extends BaseValidata<OrderExtendEntity> {

    public R list(OrderExtendEntity entity, String orderBy) {
        return null;
    }

    public R info(OrderExtendEntity entity) {
        return null;
    }

    public R update(OrderExtendEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(entity.getId() == null)
            map.put("id", StatusCodeUtil.getMsg(Content.STATUS_CODE_5210));
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).putError(map);
        return null;
    }

    public R del(OrderExtendEntity entity) {

        return null;
    }

    public R delByIds(Long[] ids) {

        return null;
    }

    public R delByGuids(String[] guids) {

        return null;
    }

    public R listByGuids(String[] guids){
        return null;
    }

    public R page(PageUtil<OrderExtendEntity> page, OrderExtendEntity entity) {

        return null;
    }
}
