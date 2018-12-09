package com.mcy.service;

import com.mcy.dao.ActivityDao;
import com.mcy.daomain.Activity;

import java.sql.SQLException;
import java.util.List;

public class ActivityService {
    //添加活动
    public void addActivity(Activity activity) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        String date = activity.getStar().substring(0,activity.getStar().indexOf(" "));
        activity.setDate(date);
        activityDao.add(activity);
    }
    //分特查询
    public List<Activity> queryActivityByPage(int pageNo, int pageSize) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        pageNo--;
        List<Activity> activities = activityDao.queryActivityByPage(pageNo,pageSize);
        return activities;
    }
    //查询总页数
    public long queryAllPage(int pageSize) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        return activityDao.queryAllPage(pageSize);
    }
    //查询私人创建信息
    public List<Activity> queryActivityByPage(int pageNo, int pageSize, String tel) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        pageNo--;
        List<Activity> activities = activityDao.queryActivityByPage(pageNo,pageSize,tel);
        return activities;
    }
    //查询活动
    public Activity queryActivityByTelByTheme(String tel, String theme) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        return activityDao.queryActivityByTelByTheme(tel,theme);
    }

    public void deleteActivityByTelByTheme(String tel, String theme) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        activityDao.deleteActivityByTelByTheme(tel,theme);
    }

    public Activity queryActivityByTheme(String theme) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        return activityDao.queryActivityByTheme(theme);
    }
    //根据日期查询活动
    public List<Activity> queryActivityByDate(String date) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        return activityDao.queryActivityByDate(date);
    }
}
