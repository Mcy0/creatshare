package com.mcy.web.servlet;

import com.mcy.daomain.User;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.exception.user.UserFormatException;
import com.mcy.service.UserService;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "RetrievePasswordServlet",urlPatterns = "/updatePassword")
public class RetrievePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tel = request.getParameter("tel");
        String newPassword = request.getParameter("newPassword");
        String telCode = request.getParameter("telCode");
        String token = request.getHeader("Authentication");
        PrintWriter out = response.getWriter();
        if (token == null || tel == null || telCode == null)
        {
            out.print(JsonStringUtil.fail("401","缺少参数" + tel + ":tel:" + newPassword + ":newPassword:" + token));
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
            out.print(JsonStringUtil.fail("400","token解析失败"));
            out.close();
            return;
        }
        if (map == null)
        {
            out.print(JsonStringUtil.fail("500","token解析失败"));
            out.close();
            return;
        }
        String code = null;
        code = (String) map.get("telCode");
        if (telCode == null || telCode.length() <= 0 || code == null || code.length() <= 0 || !telCode.equals(code)) {
            out.print(JsonStringUtil.fail("406", "验证码错误 "));
            out.close();
            return;
        }
        else
        {
            map.remove("telCode");
        }
        UserService userService = new UserService();
        User user = null;
        try {
            user = userService.queryUserByTel(tel);
        } catch (SQLException e) {
            out.print(JsonStringUtil.fail("500","服务器异常"));
            out.close();
            return;
        }
        if (user == null)
        {
            out.print(JsonStringUtil.fail("404","用户不存在"));
            out.close();
            return;
        }
        try {
            userService.retrievePassword(tel,newPassword);
            out.print(JsonStringUtil.success("200","成功",true));
            out.close();
            return;

        } catch (UserFormatException e) {
            out.print(JsonStringUtil.fail("405", "格式错误"));
            out.close();
            return;
        } catch (SQLException e) {
            out.print(JsonStringUtil.fail("500", "更新密码错误"));
            out.close();
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
