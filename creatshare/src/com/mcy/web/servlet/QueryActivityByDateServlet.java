package com.mcy.web.servlet;

import com.mcy.daomain.Activity;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.service.ActivityService;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
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

@WebServlet(name = "QueryActivityByDateServlet",urlPatterns = "/queryActivityByDate")
public class QueryActivityByDateServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(QueryActivityByDateServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authentication");
        String date = request.getParameter("date");
        PrintWriter out = response.getWriter();

        if (token == null || date == null)
        {
            logger.info("date:" + date + ";token:" + token);
            out.print(JsonStringUtil.fail("401","缺少参数"));
            out.close();
            return;
        }
        Activity activity = new Activity();
        if (!activity.judgeDateFormat(date))
        {
            out.print(JsonStringUtil.fail("405","date日期格式不对"));
            out.close();
            return;
        }
        Map<String,Object> map = null;
        try {
            map = TokenUtil.valid(token);
        } catch (TokenTimeoutException e) {
            out.print(JsonStringUtil.fail("402","授权 过期"));
            out.close();
            return;
        } catch (TokenVerifyOrParseFailException e) {
            out.print(JsonStringUtil.fail("400","token 解析失败"));
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
        try {
            activities = activityService.queryActivityByDate(date);
        } catch (SQLException e) {
            e.printStackTrace();
            out.print(JsonStringUtil.fail("500","数据库查询活动失败"));
            out.close();
            return;
        }
        if (activities == null)
        {
            out.print(JsonStringUtil.fail("404","活动不存在"));
            out.close();
            return;
        }
        out.print(JsonStringUtil.success("200",activities));
        out.close();
        return;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
