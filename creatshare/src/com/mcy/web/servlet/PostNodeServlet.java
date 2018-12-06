package com.mcy.web.servlet;

import com.mcy.daomain.User;
import com.mcy.exception.token.TokenCreateException;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "PostNodeServlet",urlPatterns = "/postnode")
public class PostNodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tel = request.getParameter("tel");
        User user = new User();
        user.setTel(tel);
        PrintWriter out = response.getWriter();
        String token = "";
        if (!user.judgeTelFormat(user.getTel()))
        {
            out.print(JsonStringUtil.fail("405",token));//手机号格式不对
            out.close();
            return;
        }
//        PostNodeUtil postNodeUtil = new PostNodeUtil();
//        postNodeUtil.post(tel);
//        Cookie cookie = new Cookie("code",postNodeUtil.getCode());
//        保存三十分钟
        Map<String,Object> map = new HashMap<>();
        map.put("telCode","1234");
        try {
            token = TokenUtil.create(map);
        } catch (TokenCreateException e) {
            out.print(JsonStringUtil.fail("405","手机号格式不对"));
            out.close();
            return;
        }
        if (true)
        {
            //成功
            out.print(JsonStringUtil.success("200",token));
            out.close();
            return;
        }
        else
        {
            out.print(JsonStringUtil.fail("504","短信发送失败"));
            out.close();
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
