package com.zqkj.utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zqkj on 2019/9/28.
 */
public class UploadFile {

    public static String singleFile(MultipartFile file, String uploadPath) {
        // 获取文件的后缀名
        String savePath = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/";
        File saveFile = new File(uploadPath + "/img/" + savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        String suffixName = file.getOriginalFilename();
        String filename = UUID.randomUUID() + suffixName.substring(suffixName.indexOf("."));
        File newFile = new File(uploadPath + "/img/" + savePath + filename);
        try {
            // 拷贝文件，性能高效，比原先的方便
            file.transferTo(newFile);
            return "upload/img/" + savePath + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
