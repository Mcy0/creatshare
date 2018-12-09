package com.mcy.web.servlet;

import com.mcy.daomain.Activity;
import com.mcy.daomain.User;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.utils.JSONToUser;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import net.minidev.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "VimActivityServlet",urlPatterns = "/vimActivity")
public class VimActivityServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(VimActivityServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String theme = request.getParameter("theme");
        String token = request.getHeader("Authentication");
        PrintWriter out = response.getWriter();
        Activity activity = new Activity();
        try{
            BeanUtils.populate(activity,request.getParameterMap());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            out.print(JsonStringUtil.fail("500","接收参数出错 "));
            out.close();
            return;
        }
        if (token == null || theme == null)
        {
            logger.info("theme:" + theme + ";token:" + token);
            out.print(JsonStringUtil.fail("401","缺少参数 "));
            out.close();
            return;
        }
        Map<String,Object> map = null;
        try {
            map = TokenUtil.valid(token);
        } catch (TokenTimeoutException e) {
            out.print(JsonStringUtil.fail("402","授权 过期 "));
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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
