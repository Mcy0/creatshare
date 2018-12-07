package com.mcy.dao;

import com.mcy.daomain.Activity;
import com.mcy.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ActivityDao {
    //添加活动
    public void add(Activity activity) throws SQLException {
        String sql = "insert into activity values(?,?,?,?,?,?,?,?)";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,activity.getTel(),activity.getType(),activity.getTheme(),activity.getPlace(),activity.getInfo(),activity.getSrc(),activity.getStar(),activity.getEnd());
    }
    //查询总页数
    public List<Activity> queryActivityByPage(int pageNo, int pageSize) throws SQLException {
        String sql = "select * from activity order by star limit ?,?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return (List<Activity>) runner.query(sql,new BeanListHandler(Activity.class),pageNo,pageSize);
    }
    //查询总页数
    public int queryAllPage(int pageSize) throws SQLException {
        String sql = "select count(0) from activity order by star";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        int pageNo = runner.query(sql,new ScalarHandler<Integer>());
        return pageNo;
    }
}
