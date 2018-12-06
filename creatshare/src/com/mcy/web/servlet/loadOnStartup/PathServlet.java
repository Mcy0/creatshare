package com.mcy.web.servlet.loadOnStartup;

import com.mcy.utils.FilePathUtil;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
//配置不起作用（再不嫁value的情况下）
//@WebServlet(name = "PathServlet",loadOnStartup = 1,value = "")
public class PathServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

        try {
            FilePathUtil.getFilePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(FilePathUtil.HEADPHOTO);
    }

}
