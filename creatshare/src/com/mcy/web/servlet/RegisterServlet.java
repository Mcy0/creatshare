package com.mcy.web.servlet;

import com.mcy.daomain.User;
import com.mcy.exception.loginOrRegister.RegisterException;
import com.mcy.exception.token.TokenCreateException;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.exception.user.UserFormatException;
import com.mcy.service.UserService;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

/**
 * 注册
 * 没有加验证码验证，手机号验证
 */
@WebServlet(name = "RegisterServlet",urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(LoginServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        String telCode = request.getParameter("telCode");
        String token = request.getHeader("Authentication");
        PrintWriter out = response.getWriter();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (Exception e) {
            logger.error(e.getMessage());
            out.print(JsonStringUtil.fail("500","缺少参数"));
            out.close();
            return;
        }
        if (token == null || user == null)
        {
            out.print(JsonStringUtil.fail("401","缺少参数"));
            out.close();
            return;
        }
        String code = null;
        //判断验证码
        Map<String,Object> map = null;
        try {
            map = TokenUtil.valid(token);
        } catch (TokenTimeoutException e) {
            out.print(JsonStringUtil.fail("402","授权过期"));
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
        code = (String) map.get("telCode");
        if (telCode == null || telCode.length() <= 0 || code == null || code.length() <= 0 || !telCode.equals(code)) {
            out.print(JsonStringUtil.fail("406", "验证码错误"));
            out.close();
            return;
        }
        else
        {
            map.remove("telCode");
        }
        UserService userService = new UserService();
        try {
            userService.register(user);
            map.put("user",user);
            map.remove("telCode");
            token = TokenUtil.create(map);
            out.print(JsonStringUtil.success("200",user,token,true));//成功
            return;
        } catch (RegisterException e) {
            out.print(JsonStringUtil.fail("403", "已经注册"));
            out.close();
            return;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            out.print(JsonStringUtil.fail("501", "注册失败"));
            out.close();
            return;
        } catch (UserFormatException e) {
            out.print(JsonStringUtil.fail("405", "格式错误"));
            out.close();
            return;
        } catch (TokenCreateException e) {
            out.print(JsonStringUtil.fail("500", "token创建异常"));
            out.close();
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
