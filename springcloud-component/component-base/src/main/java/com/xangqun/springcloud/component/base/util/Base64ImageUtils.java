package com.xangqun.springcloud.component.base.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by laixiangqun on 2017/9/29.
 * 本地或者网络图片资源转为Base64字符串
 */
public class Base64ImageUtils {
    /**
     * @param imgURL 网络资源位置
     * @return Base64字符串
     * @Title: GetImageStrFromUrl
     * @Description: 将一张网络图片转化成Base64字符串
     */
    public static String GetImageStrFromUrl(String imgURL) {
        byte[] data = null;
        InputStream inStream = null;
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();
            data = new byte[inStream.available()];
            inStream.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert inStream != null;
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * @param imgPath
     * @return
     * @Title: GetImageStrFromPath
     * @Description: (将一张本地图片转化成Base64字符串)
     */
    public static String GetImageStrFromPath(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert in != null;
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * @param imgStr
     * @param imgFilePath 图片文件名，如“E:/tmp.jpg”
     * @return
     * @Title: GenerateImage
     * @Description: base64字符串转化成图片
     */
    public static boolean saveImage(String imgStr, String imgFilePath) {
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();

            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

