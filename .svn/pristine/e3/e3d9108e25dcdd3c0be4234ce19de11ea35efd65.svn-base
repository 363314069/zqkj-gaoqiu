package com.zqkj.controller.validata;

import com.zqkj.bean.FileBase64Bean;
import com.zqkj.utils.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public class BusinessCommonValidata {

    public R uploadImg(@RequestBody FileBase64Bean fileBase64Bean){
        if(StringUtil.isEmpty(fileBase64Bean.getImageFile())){
            return R.error(Content.STATUS_CODE_20100,"图片流为空");
        }
        if(StringUtil.isEmpty(fileBase64Bean.getSuffix())){
            return R.error(Content.STATUS_CODE_20100,"文件后缀名为空");
        }
        return null;
    }

    public R UploadImage(@RequestParam(value = "img", required = false) MultipartFile img) {
        return null;
    }
}
