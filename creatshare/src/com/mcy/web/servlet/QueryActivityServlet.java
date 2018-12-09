package com.mcy.web.servlet;

import com.mcy.daomain.Activity;
import com.mcy.daomain.User;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.service.ActivityService;
import com.mcy.utils.JSONToUser;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import net.minidev.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "QueryActivityServlet",urlPatterns = "/queryActivity")
public class QueryActivityServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(QueryActivityServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNo = 1;
        int pageSize = 8;
        logger.info("pageNo:" + pageNo + "pageSize" + pageSize);
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String token = request.getHeader("Authentication");
        String type = request.getParameter("type");
        if (pageNoStr != null)
        {
            pageNo = Integer.parseInt(pageNoStr);
        }
        if (pageSizeStr != null)
        {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        logger.info("pageNo:" + pageNo + "pageSize" + pageSize);
        PrintWriter out = response.getWriter();
        if (token == null || type == null)
        {
            out.print(JsonStringUtil.fail("401","缺少参数"));
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
        ActivityService activityService = new ActivityService();
        List<Activity> activities = null;
        if (type.equals("false"))
        {
            JSONObject jsonObject = (JSONObject)map.get("user");
            User user = JSONToUser.getUser(jsonObject);
            try {
                activities = activityService.queryActivityByPage(pageNo,pageSize,user.getTel());
            } catch (SQLException e) {
                e.printStackTrace();
                out.print(JsonStringUtil.fail("500","查询数据库异常 "));
                out.close();
                return;
            }
        }
        else
        {
            try {
                activities = activityService.queryActivityByPage(pageNo,pageSize);
            } catch (SQLException e) {
                e.printStackTrace();
                out.print(JsonStringUtil.fail("500","查询数据库异常 "));
                out.close();
                return;
            }
        }
        out.print(JsonStringUtil.success("200",activities));
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
