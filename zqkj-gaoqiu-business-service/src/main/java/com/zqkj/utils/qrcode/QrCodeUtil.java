package com.zqkj.utils.qrcode;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

/**
 * 生成二维码转base64
 */
public class QrCodeUtil {
    public static String creatRrCode(String contents, int width, int height) {
        String binary = null;
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    contents, BarcodeFormat.QR_CODE, width, height, hints);
            // 1、读取文件转换为字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedImage image = toBufferedImage(bitMatrix);
            //转换成png格式的IO流
            ImageIO.write(image, "png", out);
            byte[] bytes = out.toByteArray();

            // 2、将字节数组转为二进制
            BASE64Encoder encoder = new BASE64Encoder();
            binary = encoder.encodeBuffer(bytes).trim();
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return binary;
    }

    /**
     * image流数据处理
     *
     * @author ianly
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }


    public static void main(String[] args) {
        String message = "{\"ftpAddress\":\"222.240.61.122:2001\",\"ftpName\":\"sjbz\",\"ftpPassword\":\"123456\",\"fileName\":\"96C52223F46A4849BE8BB4C96E9CD188.zip\",\"dataType\":0,\"deviceType\":0,\"version\":\"SY_ZS20191126\",\"md5\":\"9a1c8e853e81176629031226f78b71fb\",\"organization\":\"10,14,15\",\"types\":0,\"path\":\"fileswap/123456.zip\",\"handover\":1,\"checker\":\"71\",\"reviewer\":null,\"approver\":\"72\",\"fileUploadTime\":\"2019-11-26 14:32:15\",\"fileUploader\":\"超级管理员\",\"modifyReason\":\"faasdf\",\"remark\":\"dfaf\",\"attachFile\":null}";
        String binary = creatRrCode(message, 500,500);
        System.out.println(binary);
    }
}
