package com.mcy.utils;

import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ImageUtil {

    public static void showImage(HttpServletResponse response, String name, boolean isResponseClose) throws IOException {
        //支持的文件类型
        String path = FilePathUtil.HEADPHOTO + "/" + name;
        File file = new File(path);
        String last = name.substring(name.lastIndexOf(".") + 1);
        response.setContentType("image/" + last);
        OutputStream outStream = response.getOutputStream();
        FileInputStream fis = new FileInputStream(path);
        byte data[] = new byte[1024];
        while (fis.read(data) > 0) {
            outStream.write(data);
        }
        fis.close();
        if (isResponseClose) {
            outStream.close();
        }

    }
}