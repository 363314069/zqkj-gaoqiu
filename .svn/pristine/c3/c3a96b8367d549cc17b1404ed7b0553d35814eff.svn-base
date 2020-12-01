package com.zqkj.controller.validata;


import com.zqkj.entity.ActivityextendEntity;
import com.zqkj.utils.Content;
import com.zqkj.utils.ObjectUtil;
import com.zqkj.utils.PageUtil;
import com.zqkj.utils.R;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class ActivityextendValidata {

    public R save(ActivityextendEntity entity) {
        return null;
    }

    public R list(ActivityextendEntity entity, String orderBy) {
        return null;
    }

    public R info(ActivityextendEntity entity) {
        return null;
    }

    public R update(ActivityextendEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5210,"主键不能为空");
        }
        return null;
    }

    public R saveList(@RequestBody List<ActivityextendEntity> list) {
        if(ObjectUtil.listObjIsNull(list)){
            return R.error(Content.STATUS_CODE_5005).put("count", 0);
        }
        return null;
    }

    public R del(ActivityextendEntity entity) {

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

    public R page(PageUtil<ActivityextendEntity> page, ActivityextendEntity entity) {

        return null;
    }

}
