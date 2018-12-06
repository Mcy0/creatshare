package com.mcy.dao;

import com.mcy.daomain.Activity;
import com.mcy.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class ActivityDao {
    //添加活动
    public void add(Activity activity) throws SQLException {
        String sql = "insert into activity values(?,?,?,?,?,?,?,?)";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,activity.getTel(),activity.getType(),activity.getTheme(),activity.getPlace(),activity.getInfo(),activity.getSrc(),activity.getStar(),activity.getEnd());
    }
}
