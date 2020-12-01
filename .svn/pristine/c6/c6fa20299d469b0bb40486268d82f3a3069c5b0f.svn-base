package com.zqkj.controller.validata;


import com.zqkj.entity.CouponsorgmapEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsorgmapValidata {

    public R save(CouponsorgmapEntity entity) {
        return null;
    }

    public R saveList(List<CouponsorgmapEntity> list) {
        return null;
    }

    public R info(CouponsorgmapEntity entity) {
        /*if(StringUtil.isEmpty(entity.getGuid())){
            return R.error(Content.STATUS_CODE_5004,"guid为空");
        }*/
        return null;
    }

    public R update(CouponsorgmapEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).put("error", map);
        return null;
    }
    public R del(CouponsorgmapEntity entity) {
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

    public R list(CouponsorgmapEntity entity, String orderBy) {
        return null;
    }

    public R page(PageUtil<CouponsorgmapEntity> page, CouponsorgmapEntity entity) {
        return null;
    }

    public R selectList(@RequestBody CouponsorgmapEntity entity) {
        return null;
    }

}
