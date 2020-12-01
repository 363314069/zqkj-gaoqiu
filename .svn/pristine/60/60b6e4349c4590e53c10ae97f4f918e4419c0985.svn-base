package com.zqkj.controller.validata;


import com.zqkj.entity.CouponsactmapEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsactmapValidata {

    public R save(CouponsactmapEntity entity) {
        return null;
    }

    public R saveList(List<CouponsactmapEntity> list) {
        return null;
    }

    public R info(CouponsactmapEntity entity) {
        /*if(StringUtil.isEmpty(entity.getGuid())){
            return R.error(Content.STATUS_CODE_5004,"guid为空");
        }*/
        return null;
    }

    public R update(CouponsactmapEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).put("error", map);
        return null;
    }
    public R del(CouponsactmapEntity entity) {
        if(entity.getId() == null && StringUtils.isAllBlank(entity.getGuid())){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }

    public R delByIds(Long[] ids) {
        if(ids == null && ids.length == 0){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }

    public R delByGuids(String[] guids) {
        if(guids == null && guids.length == 0){
            return R.error(Content.STATUS_CODE_5006);
        }
        return null;
    }

    public R list(CouponsactmapEntity entity, String orderBy) {
        return null;
    }

    public R page(PageUtil<CouponsactmapEntity> page, CouponsactmapEntity entity) {
        return null;
    }

    public R selectList(@RequestBody CouponsactmapEntity entity) {
        return null;
    }

}
