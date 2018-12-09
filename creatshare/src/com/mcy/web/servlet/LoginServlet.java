package com.mcy.web.servlet;

import com.mcy.daomain.User;
import com.mcy.exception.loginOrRegister.LoginNotExitException;
import com.mcy.exception.token.TokenCreateException;
import com.mcy.exception.user.UserFormatException;
import com.mcy.service.UserService;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 *没有加验证码验证
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(LoginServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //处理参数
        String telCode = request.getParameter("telCode");
        String token = null;
        PrintWriter out = response.getWriter();
        User user = new User();
        try{
            BeanUtils.populate(user,request.getParameterMap());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        if (user == null || telCode == null || telCode.length() <= 0 || user.getTel() == null ||  user.getTel().length() <= 0||user.getPassword() == null || user.getPassword().length() <= 0 )
        {
            out.print(JsonStringUtil.fail("401","缺少参数"));
            out.close();
            return;
        }
        //处理token
        Map<String,Object> map = new HashMap<>();
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        if (code == null || code.length() <= 0|| !code.equalsIgnoreCase(telCode))
        {
            out.print(JsonStringUtil.fail("406","验证码错误" + "验证码:" + code + ":::" +session));
            if (session != null)
            session.removeAttribute("code");
            out.close();
            return;
        }else //成功
        {
            session.removeAttribute("code");
        }
        //获取登录人信息
        User userLogin = null;
        UserService userService = new UserService();
        try {
            userLogin = userService.login(user.getTel(),user.getPassword());
        } catch (SQLException e) {
            logger.error(e.getMessage());
            out.print(JsonStringUtil.fail("500","服务器错误"));
            out.close();
            return;
        } catch (LoginNotExitException e) {
            out.print(JsonStringUtil.fail("403","账户不存在"));
            out.close();
            return;
        } catch (UserFormatException e) {
            out.print(JsonStringUtil.fail("405","格式错误"));
            out.close();
            return;
        }
        if (userLogin == null)
        {
            out.print(JsonStringUtil.fail("404","密码错误"));
            out.close();
            return;
        }
        else
        {
            map.put("user",userLogin);
            try {
                token = TokenUtil.create(map);
            } catch (TokenCreateException e) {
                out.print(JsonStringUtil.fail("500","服务器异常"));
            }
            out.print(JsonStringUtil.success("200",userLogin,token));
            out.close();
            return;
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
