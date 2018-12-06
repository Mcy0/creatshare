package com.mcy.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ImageUtil {

    public static void showImage(HttpServletResponse response, HttpServletRequest request, boolean isResponseClose) throws IOException {
        //支持的文件类型
        String name = request.getParameter("name");
        String type = request.getParameter("flag");
        String path = null;
        if (type.equals("activity"))
        {
            path = FilePathUtil.PHOTOS + "/" + name;
        }
        else if (type.equals("head"))
        {
            path = FilePathUtil.HEADPHOTO + "/" + name;
        }
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