package com.mcy.service;

import com.mcy.dao.ActivityDao;
import com.mcy.daomain.Activity;

import java.sql.SQLException;
import java.util.List;

public class ActivityService {
    //添加活动
    public void addActivity(Activity activity) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        activityDao.add(activity);
    }
    //分特查询
    public List<Activity> queryActivityByPage(int pageNo, int pageSize) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        List<Activity> activities = activityDao.queryActivityByPage(pageNo,pageSize);
        return activities;
    }
    //查询总页数
    public int queryAllPage(int pageSize)
    {

        return 1;
    }

    public static void main(String[] args) throws SQLException {
        ActivityService activityService = new ActivityService();
        System.out.println(activityService.queryActivityByPage(0,2));
    }
}
