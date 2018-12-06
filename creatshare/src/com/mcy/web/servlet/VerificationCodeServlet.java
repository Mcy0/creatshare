package com.mcy.web.servlet;

import com.mcy.utils.SCaptcha;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "VerificationCodeServlet",urlPatterns = "/code")
public class VerificationCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        response.setContentType("image/jpeg");
        //取消缓存
        response.setHeader("Pragma", "no-cache");
        SCaptcha sCaptcha = new SCaptcha();
        OutputStream out = response.getOutputStream();
        String code = sCaptcha.getCode();
        session.setAttribute("code",code);
        sCaptcha.write(out,false);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
