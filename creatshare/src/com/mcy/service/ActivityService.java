package com.mcy.service;

import com.mcy.dao.ActivityDao;
import com.mcy.daomain.Activity;

import java.sql.SQLException;

public class ActivityService {
    public void addActivity(Activity activity) throws SQLException {
        ActivityDao activityDao = new ActivityDao();
        activityDao.add(activity);
    }
}
