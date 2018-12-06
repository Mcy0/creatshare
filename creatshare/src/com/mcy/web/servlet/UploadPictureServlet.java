package com.mcy.web.servlet;

import com.mcy.daomain.User;
import com.mcy.exception.token.TokenCreateException;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.service.UserService;
import com.mcy.utils.FilePathUtil;
import com.mcy.utils.JSONToUser;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import net.minidev.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "UploadPictureServlet",urlPatterns = "/uploadPicture")
@MultipartConfig
public class UploadPictureServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader("Authentication");
        String photo = request.getParameter("photo");
        String last = photo.substring(photo.indexOf("/") + 1,photo.indexOf(";"));
        photo = photo.substring(photo.indexOf("64") + 3);
        PrintWriter out = response.getWriter();
        if (token == null || photo == null)
        {
            out.print(JsonStringUtil.fail("401","缺少参数"));
            out.close();
            return;
        }
        Map<String,Object> map = null;
        try {
            map = TokenUtil.valid(token);
        } catch (TokenTimeoutException e) {
                out.print(JsonStringUtil.fail("402","授权过期 "));
            out.close();
            return;
        } catch (TokenVerifyOrParseFailException e) {
            out.print(JsonStringUtil.fail("400","token解析失败 "));
            out.close();
            return;
        }
        JSONObject jsonObject = (JSONObject)map.get("user");
        User user = JSONToUser.getUser(jsonObject);
        if (user == null)
        {
            out.print(JsonStringUtil.fail("400","token解析失败获取user失败"));
            out.close();
            return;
        }
        String headPortrait = "http://132.232.119.121/creatshare/photo?name="+ user.getTel() +"." + last;
        String headPath = FilePathUtil.HEADPHOTO + "/" + user.getTel() + "." + last;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(photo);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        OutputStream outputStream = new FileOutputStream(headPath);
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();
        user.setHeadPortrait(headPortrait);
        UserService userService = new UserService();
        //修改数据库路径
        try {
            userService.uploadHead(user);
        } catch (SQLException e) {
            out.print(JsonStringUtil.fail("500","修改头像异常"));
            out.close();
            return;
        }
        map.put("user",user);
        try {
            token = TokenUtil.create(map);
        } catch (TokenCreateException e) {
            out.print(JsonStringUtil.fail("500","token创建异常 "));
            out.close();
            return;
        }
        out.print(JsonStringUtil.success("200",user,token,true));
        out.close();
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
///home/tomcat/apache-tomcat-9.0.12/bin