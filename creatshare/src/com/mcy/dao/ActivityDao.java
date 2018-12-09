package com.mcy.dao;

import com.mcy.daomain.Activity;
import com.mcy.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;


public class ActivityDao {
    //添加活动
    private Logger logger = Logger.getLogger(ActivityDao.class);
    public void add(Activity activity) throws SQLException {
        String sql = "insert into activity values(?,?,?,?,?,?,?,?,?)";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,activity.getTel(),activity.getType(),activity.getTheme(),activity.getPlace(),activity.getInfo(),activity.getSrc(),activity.getStar(),activity.getEnd(),activity.getDate());
    }
    //查询总页数
    public List<Activity> queryActivityByPage(int pageNo, int pageSize) throws SQLException {
        String sql = "select * from activity order by star limit ?,?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        logger.info("pageNo:" + pageNo + "page" + pageNo * pageSize + ";pageSize:" + pageSize);

        return  runner.query(sql,new BeanListHandler<>(Activity.class),pageNo * pageSize , pageSize);
    }
    //查询总页数
    public long queryAllPage(int pageSize) throws SQLException {
        String sql = "select count(0) from activity order by star";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        Long page = runner.query(sql,new ScalarHandler<Long>());
        if (page / pageSize == 0)
        {
            return 1;
        }
        return page / pageSize;
    }
    //查询私人活动//select * from activity  limit ?,? order by star;
    public List<Activity> queryActivityByPage(int pageNo, int pageSize, String tel) throws SQLException {
        String sql = "select * from activity where tel = ? order by star limit ?,?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return  runner.query(sql,new BeanListHandler<>(Activity.class),tel,pageNo * pageSize,pageSize);
    }
    // 根据手机号活动名查询活动
    public Activity queryActivityByTelByTheme(String tel, String theme) throws SQLException {
        String sql = "select * from activity where tel = ? and theme = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql,new BeanHandler<>(Activity.class),tel,theme);
    }
    // 根据手机号活动名删除活动
    public void deleteActivityByTelByTheme(String tel, String theme) throws SQLException {
        String sql = "delete from activity where tel = ? and theme = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,tel,theme);
    }
    //根据主题名查询活动
    public Activity queryActivityByTheme(String theme) throws SQLException {
        String sql = "select * from activity where theme = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql,new BeanHandler<>(Activity.class),theme);
    }
    //根据时间查询活动
    public List<Activity> queryActivityByDate(String date) throws SQLException {
        String sql = "select * from activity where date = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql,new BeanListHandler<>(Activity.class),date);
    }
}
