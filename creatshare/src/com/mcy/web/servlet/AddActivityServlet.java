package com.mcy.web.servlet;

import com.mcy.daomain.Activity;
import com.mcy.daomain.User;
import com.mcy.exception.token.TokenTimeoutException;
import com.mcy.exception.token.TokenVerifyOrParseFailException;
import com.mcy.service.ActivityService;
import com.mcy.utils.FilePathUtil;
import com.mcy.utils.JSONToUser;
import com.mcy.utils.JsonStringUtil;
import com.mcy.utils.TokenUtil;
import net.minidev.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "AddActivityServlet",urlPatterns = "/addActivity")
public class AddActivityServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(AddActivityServlet.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader("Authentication");
        PrintWriter out = response.getWriter();
        Activity activity = new Activity();
        String photo = request.getParameter("photo");
        if (token == null || photo == null || photo.length() <= 0)
        {
            out.print(JsonStringUtil.fail("401","缺少参数"));
            out.close();
            return;
        }
        if (photo.split("=").length > 9)
        {
            out.print(JsonStringUtil.fail("405","添加照片过多（最多九张）"));
            out.close();
            return;
        }
        Map<String,Object> map = null;
        try {
            map = TokenUtil.valid(token);
        } catch (TokenTimeoutException e) {
            out.print(JsonStringUtil.fail("402"," 授权过期"));
            out.close();
            return;
        } catch (TokenVerifyOrParseFailException e) {
            out.print(JsonStringUtil.fail("400","token解析失败"));
            out.close();
            return;
        }
        JSONObject jsonObject = (JSONObject)map.get("user");
        User user = JSONToUser.getUser(jsonObject);
        try{
            BeanUtils.populate(activity,request.getParameterMap());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            out.print(JsonStringUtil.fail("500","接收参数出错"));
            out.close();
            return;
        }
        activity.setTel(user.getTel());
        if (!activity.judgeAllFormat())
        {
            out.print(JsonStringUtil.fail("401","格式不对activity:" + activity));
            out.close();
            return;
        }

        Activity activity1 = null;
        try {
            ActivityService activityService1 = new ActivityService();
            activity1 = activityService1.queryActivityByTheme(activity.getTheme());
        } catch (SQLException e) {
            e.printStackTrace();
            out.print(JsonStringUtil.fail("500","数据库查询失败"));
            out.close();
            return;
        }
        if (activity1 != null)
        {
            out.print(JsonStringUtil.fail("403","活动已经存在"));
            out.close();
            return;
        }

        String[] photos = photo.split("=");
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (String p :
                photos) {
            i++;
            String last = photo.substring(photo.indexOf("/") + 1,photo.indexOf(";"));
            p = p.substring(photo.indexOf("64") + 3);
            String name = user.getTel() + "MCY" +i + "." + last;
            createImage(p,name);
            String activityPhoto = "http://132.232.119.121/creatshare/photo?name="+ name + "&" + "flag=activity";
            if(i !=1)
            {
                buffer.append("|");
                buffer.append(activityPhoto);
            }
            else
            {
                buffer.append(activityPhoto);
            }
        }
        activity.setSrc(buffer.toString());
        ActivityService activityService = new ActivityService();
        try {
            activityService.addActivity(activity);
            out.print(JsonStringUtil.success("200","成功添加活动",true));
            out.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            out.print(JsonStringUtil.fail("500","数据库添加活动失败" + activity));
            out.close();
            return;
        }
        //String activityPhotos = "http://132.232.119.121/creatshare/photo?name="+ name + "&" + "flag=activity";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    private void createImage(String photo,String name) throws IOException {
        String headPath = FilePathUtil.PHOTOS + "/" + name;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer(photo);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        OutputStream outputStream = new FileOutputStream(headPath);
        outputStream.write(b);
        outputStream.flush();
        outputStream.close();
    }
}
