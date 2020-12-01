package com.zqkj.controller.validata;

import com.zqkj.entity.VipcardEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class VipcardValidata extends BaseValidata<VipcardEntity> {

    public R list(VipcardEntity entity, String orderBy) {
        return null;
    }

    public R info(VipcardEntity entity) {
        return null;
    }

    public R save(VipcardEntity entity) {
        return null;
    }

    public R update(VipcardEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(entity.getId() == null || StringUtil.isEmpty(entity.getGuid()))
            map.put("id", StatusCodeUtil.getMsg(Content.STATUS_CODE_5210));
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).putError(map);
        return null;
    }

    public R del(VipcardEntity entity) {

        return null;
    }

    public R delByIds(Long[] ids) {

        return null;
    }

    public R delByGuids(String[] guids) {

        return null;
    }

    public R page(PageUtil<VipcardEntity> page, VipcardEntity entity) {

        return null;
    }

    public R uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (file == null) {
            return R.error(Content.STATUS_CODE_5006, "上传文件为空！");
        }
        return null;
    }

    public R listByGuids(String[] guids) {
        if(guids == null || guids.length <= 0){
            return R.error(Content.STATUS_CODE_5006, "会员卡GUID空！");
        }
        return null;
    }
}
