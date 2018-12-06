package com.mcy.dao;

import com.mcy.daomain.User;
import com.mcy.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.Test;

import java.sql.SQLException;

public class UserDao {
    /**
     * 注册时添加普通用户
     * @param user
     * @return
     * @throws SQLException
     */
    public void add(User user) throws SQLException {

        String sql = "insert into users(tel,password,nickname) values(?,?,?)";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,user.getTel(),user.getPassword(),user.getTel());
    }
    /**
     * 查询用户
     * @param tel
     * @return
     * @throws SQLException
     */
    public User queryByTel(String tel) throws SQLException {
        String sql = "select * from users where tel = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql,new BeanHandler<>(User.class),tel);
    }

    /**
     * 根据庄户密码查询用户
     * @param tel
     * @param password
     * @return
     * @throws SQLException
     */
    public User queryByTelByPassword(String tel, String password) throws SQLException {
        String sql = "select * from users where tel = ? and password = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql,new BeanHandler<>(User.class),tel,password);
    }
    /**
     * 修改密码
     * @param tel
     * @param password
     * @throws SQLException
     */
    public void updatePasswordByTel(String tel,String password) throws SQLException {
        String sql = "update users set password = ? where tel = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,password,tel);
    }

    /**
     * 修改昵称
     * @param tel
     * @param nickname
     * @throws SQLException
     */
    public void updateNicknameByTel(String tel, String nickname) throws SQLException {
        String sql = "update users set nickname = ? where tel = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,nickname,tel);
    }

    //修改头像
    public void updateHeadPortraitByTel(String tel, String headPortrait) throws SQLException {
        String sql = "update users set headPortrait = ? where tel = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        runner.update(sql,headPortrait,tel);
    }
    //获取头像
    public User queryHeadPortraitByPassword(String tel) throws SQLException {
        String sql = "select * from users where tel = ?";
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        return runner.query(sql,new BeanHandler<>(User.class),tel);
    }
    @Test
    public void testQueryByTel() throws SQLException {
        new UserDao().updatePasswordByTel("15136010213","123456");
    }
}
