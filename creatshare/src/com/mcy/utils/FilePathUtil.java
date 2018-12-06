package com.mcy.utils;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class FilePathUtil {
    /**
     * @param str
     * @return 返回绝对路径
     * @throws IOException
     */
    public static String HEADPHOTO = "";
    public static void getFilePath() throws IOException {
        URL url = FilePathUtil.class.getClassLoader().getResource("filepath.properties");
        Reader reader = new FileReader(url.getFile());
        Properties pro = new Properties();
        pro.load(reader);
        HEADPHOTO = pro.getProperty("headPhoto");
//        System.out.println(HEADPHOTO);
    }
}
