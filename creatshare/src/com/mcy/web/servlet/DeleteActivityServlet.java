package com.mcy.web.servlet;

import com.mcy.daomain.Activity;
import com.mcy.daomain.User;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.service.ActivityService;
import com.mcy.utils.FilePathUtil;
import com.mcy.utils.JSONToUser;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import net.minidev.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "DeleteActivityServlet",urlPatterns = "/deleterActivity",asyncSupported = true)
public class DeleteActivityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader("Authentication");
        String theme = request.getParameter("theme");
        String tel = request.getParameter("tel");
        PrintWriter out = response.getWriter();
        if (token == null || theme == null)
        {
            out.print(JsonStringUtil.fail("401","缺少参数"));
            out.close();
            return;
        }
        ActivityService activityService = new ActivityService();
        Map<String,Object> map = null;
        try {
            map = TokenUtil.valid(token);
        } catch (TokenTimeoutException e) {
            out.print(JsonStringUtil.fail("402","授权 过期"));
            out.close();
            return;
        } catch (TokenVerifyOrParseFailException e) {
            out.print(JsonStringUtil.fail("400"," token解析失败"));
            out.close();
            return;
        }
        if (map == null)
        {
            out.print(JsonStringUtil.fail("500","token解析失败"));
            out.close();
            return;
        }
        JSONObject jsonObject = (JSONObject)map.get("user");
        User user = JSONToUser.getUser(jsonObject);
        Activity activity = null;
        try {
            activity = activityService.queryActivityByTelByTheme(user.getTel(),theme);
        } catch (SQLException e) {
            e.printStackTrace();
            out.print(JsonStringUtil.fail("500","服务器异常，查询活动失败"));
            out.close();
            return;
        }
        if (activity == null)
        {
            out.print(JsonStringUtil.fail("404","请求活动不存在"));
            out.close();
            return;
        }
        else
        {
            if (user.getIdentity() == false)
            {
                try {
                    activityService.deleteActivityByTelByTheme(user.getTel(), theme);
                } catch (SQLException e) {
                    out.print(JsonStringUtil.fail("500","服务器异常，删除活动失败"));
                    out.close();
                    return;
                }
            }
            else
            {
                if (tel == null)
                {
                    out.print(JsonStringUtil.fail("401","管理员操作缺少参数"));
                    out.close();
                    return;
                }
                try {
                    activityService.deleteActivityByTelByTheme(tel, theme);
                } catch (SQLException e) {
                    out.print(JsonStringUtil.fail("500","服务器异常，删除活动失败"));
                    out.close();
                    return;
                }
            }
        }
        out.print(JsonStringUtil.success("200","删除成功",true));
        out.close();
        //http://132.232.119.121/creatshare/photo?name=15829467352MCY1.jpg&flag=activity
        Activity finalActivity1 = activity;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] photos = finalActivity1.getSrc().split("=");
                String photo = photos[0];
                String nameStar = photo.substring(photo.indexOf("name=") + 5,photo.indexOf("MCY") + 3);
                String nameEnd = photo.substring(photo.indexOf("MCY") + 4,photo.indexOf("MCY") + 8);
                for (int i = 1; i <= 9; i++) {
                    String name = nameStar + i + nameEnd;
                    String path = FilePathUtil.PHOTOS + "/" + name;
                    File file = new File(path);
                    file.delete();
                }
            }
        }).start();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
