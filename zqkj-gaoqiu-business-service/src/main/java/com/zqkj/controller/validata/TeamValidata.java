package com.zqkj.controller.validata;


import com.zqkj.entity.TeamEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class TeamValidata {

    public R save(TeamEntity entity) {
        return null;
    }

    public R list(TeamEntity entity, String orderBy) {
        return null;
    }

    public R info(TeamEntity entity) {
        return null;
    }

    public R update(TeamEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5210,"主键不能为空");
        }
        return null;
    }

    public R saveList(@RequestBody List<TeamEntity> list) {
        if(ObjectUtil.listObjIsNull(list)){
            return R.error(Content.STATUS_CODE_5005).put("count", 0);
        }
        return null;
    }

    public R del(TeamEntity entity) {

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

    public R page(PageUtil<TeamEntity> page, TeamEntity entity) {

        return null;
    }

}
