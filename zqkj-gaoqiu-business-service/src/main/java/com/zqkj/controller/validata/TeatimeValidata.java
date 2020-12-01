package com.zqkj.controller.validata;


import com.zqkj.entity.TeatimeEntity;
import com.zqkj.entity.TeatimeGroupByEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeatimeValidata {

    public R save(TeatimeEntity entity) {
        return null;
    }

    public R list(TeatimeEntity entity, String orderBy) {
        return null;
    }

    public R info(TeatimeEntity entity) {
        return null;
    }

    public R update(TeatimeEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5210,"主键不能为空");
        }
        return null;
    }

    public R saveList(@RequestBody List<TeatimeEntity> list) {
        if(ObjectUtil.listObjIsNull(list)){
            return R.error(Content.STATUS_CODE_5005).put("count", 0);
        }
        return null;
    }

    public R del(TeatimeEntity entity) {

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

    public R page(PageUtil<TeatimeEntity> page, TeatimeEntity entity) {

        return null;
    }

    public R introductionCallback(TeatimeEntity teatimeEntity){
        return null;
    }


    public R selectList(@RequestBody TeatimeEntity teatimeEntity){
        return null;
    }


    public R setUpDateTime(String map,String introductionGuid,Integer type){
        if(StringUtil.isEmpty(map)){
            return R.error(Content.STATUS_CODE_5006,"请选择修改的时间");
        }
        if(StringUtil.isEmpty(introductionGuid)){
            return R.error(Content.STATUS_CODE_5006,"场地guid为空");
        }
        if(type == null){
            return R.error(Content.STATUS_CODE_5006,"类型为空");
        }else{
            if(type != 1 && type != 2){
                return R.error(Content.STATUS_CODE_5006,"类型错误");
            }
        }
        return null;
    }


    public R selectDateTimeGroupBy(String startDate,String endDate,String introductionGuid,Integer type){
        if(StringUtil.isEmpty(startDate)){
            return R.error(Content.STATUS_CODE_5006,"起始时间不能为空");
        }
        if(StringUtil.isEmpty(endDate)){
            return R.error(Content.STATUS_CODE_5006,"结束时间不能为空");
        }
        if(StringUtil.isEmpty(introductionGuid)){
            return R.error(Content.STATUS_CODE_5006,"场地guid为空");
        }
        if(type == null){
            return R.error(Content.STATUS_CODE_5006,"类型为空");
        }
        return null;
    }
}
