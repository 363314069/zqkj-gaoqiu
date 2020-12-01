package com.zqkj.controller.validata;

import com.zqkj.entity.AreaEntity;
import com.zqkj.entity.ProvinceEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.R;

public class AreaValidata extends BaseValidata<AreaEntity> {

    public R listByGuids(String[] guids) {
        if(guids == null || guids.length == 0)
            return R.error(Content.STATUS_CODE_5004).put("count", 0);
        return null;
    }

    public R list(AreaEntity entity, String orderBy) {
        return null;
    }

    public R info(AreaEntity entity) {
        return null;
    }

    public R threeLevelLinkage(Integer code,Integer countryCode) {
        return null;
    }
}
