package com.mcy.web.servlet;

import com.mcy.daomain.User;
import com.mcy.service.UserService;
import com.mcy.utils.JsonStringUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "QueryUserByTelServlet",urlPatterns = "/judgeRegister")
public class QueryUserByTelServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(QueryUserByTelServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tel = request.getParameter("tel");
        UserService userService = new UserService();
        PrintWriter out = response.getWriter();
        if (tel == null)
        {
            logger.info("tel:" + tel + ";");
            out.print(JsonStringUtil.fail("401","缺少参数"));
            out.close();
            return;
        }
        User user = null;

        try {
            user = userService.queryUserByTel(tel);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            out.print(JsonStringUtil.fail("500","服务器错误"));
            out.close();
            return;
        }
        if (user == null)
        {
            out.print(JsonStringUtil.fail("200","未注册"));
            out.close();
            return;
        }
        else
        {
            out.print(JsonStringUtil.fail("201","已经注册"));
            out.close();
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
