package com.mcy.web.servlet;

import com.mcy.daomain.User;
import com.mcy.exception.token.TokenCreateException;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.service.UserService;
import com.mcy.utils.JSONToUser;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import net.minidev.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "ModifyDataServlet",urlPatterns = "/modifyDate")
public class ModifyDataServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader("Authentication");
        String nickname = request.getParameter("nickname");
        PrintWriter out = response.getWriter();
        if (token == null || nickname == null)
        {
            out.print(JsonStringUtil.fail("401","缺少参数" + "Authentication" + token + ";nickname" + nickname));
            out.close();
            return;
        }
        Map<String,Object> map = null;
        try {
            map = TokenUtil.valid(token);
        } catch (TokenTimeoutException e) {
            out.print(JsonStringUtil.fail("402","授权过期"));
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
            out.print(JsonStringUtil.fail("400","token解析获取user失败 "));
            out.close();
            return;
        }
        user.setNickname(nickname);
        UserService userService = new UserService();
        try {
            userService.modifyDate(user);
        } catch (SQLException e) {
            out.print(JsonStringUtil.fail("500","修改资料失败"));
            out.close();
            return;
        }
        map.put("user",user);
        System.out.println(user);
        try {
            token = TokenUtil.create(map);
        } catch (TokenCreateException e) {
            out.print(JsonStringUtil.fail("500","token创建异常"));
            out.close();
            return;
        }
        out.print(JsonStringUtil.success("200",user,token));
        out.close();
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
