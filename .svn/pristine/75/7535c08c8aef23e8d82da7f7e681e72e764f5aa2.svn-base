package com.zqkj.controller.validata;

import com.zqkj.entity.MembersverifyEntity;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class MembersverifyValidata extends BaseValidata<MembersverifyEntity> {

    public R list(MembersverifyEntity entity, String orderBy) {
        return null;
    }

    public R info(MembersverifyEntity entity) {
        return null;
    }

    public R save(MembersverifyEntity entity) {
        if(StringUtil.isEmpty(entity.getName())){
            return R.error(Content.STATUS_CODE_5006,"姓名不能为空");
        }
        if(StringUtil.isEmpty(entity.getPhone())){
            return R.error(Content.STATUS_CODE_5006,"电话不能为空");
        }
        if(StringUtil.isEmpty(entity.getIdCard())){
            return R.error(Content.STATUS_CODE_5006,"身份证号码不能为空");
        }
        return null;
    }

    public R update(MembersverifyEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        if(entity.getId() == null || StringUtil.isEmpty(entity.getGuid()))
            map.put("id", StatusCodeUtil.getMsg(Content.STATUS_CODE_5210));
        if(map.size() > 0)
            return R.error(Content.STATUS_CODE_5006).putError(map);
        return null;
    }

    public R del(MembersverifyEntity entity) {

        return null;
    }

    public R delByIds(Long[] ids) {

        return null;
    }

    public R delByGuids(String[] guids) {

        return null;
    }

    public R page(PageUtil<MembersverifyEntity> page, MembersverifyEntity entity) {

        return null;
    }

    public R importData(@RequestParam MultipartFile file){
        if(file == null){
            return R.error(Content.STATUS_CODE_5006,"文件为空！");
        }
        return null;
    }

    public R verifyBin(MembersverifyEntity entity) {
        if(StringUtil.isEmpty(entity.getName())){
            return R.error(Content.STATUS_CODE_5006,"姓名不能为空");
        }
        if(StringUtil.isEmpty(entity.getPhone())){
            return R.error(Content.STATUS_CODE_5006,"电话不能为空");
        }
        if(StringUtil.isEmpty(entity.getIdCard())){
            return R.error(Content.STATUS_CODE_5006,"身份证号码不能为空");
        }
        return null;
    }


    public R backSave(MembersverifyEntity entity) {
        if(StringUtil.isEmpty(entity.getName())){
            return R.error(Content.STATUS_CODE_5006,"姓名不能为空");
        }
        if(StringUtil.isEmpty(entity.getPhone())){
            return R.error(Content.STATUS_CODE_5006,"电话不能为空");
        }
        if(StringUtil.isEmpty(entity.getIdCard())){
            return R.error(Content.STATUS_CODE_5006,"身份证号码不能为空");
        }
        return null;
    }


    public R backUpdate(MembersverifyEntity entity) {
        if(entity.getId() == null){
            return R.error(Content.STATUS_CODE_5006,"ID不能为空");
        }
        if(StringUtil.isEmpty(entity.getName())){
            return R.error(Content.STATUS_CODE_5006,"姓名不能为空");
        }
        if(StringUtil.isEmpty(entity.getPhone())){
            return R.error(Content.STATUS_CODE_5006,"电话不能为空");
        }
        if(StringUtil.isEmpty(entity.getIdCard())){
            return R.error(Content.STATUS_CODE_5006,"身份证号码不能为空");
        }
        return null;
    }


    public R pageList(PageUtil<MembersverifyEntity> page,MembersverifyEntity entity) {
        return null;
    }
}
