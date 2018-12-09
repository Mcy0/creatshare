package com.mcy.service;

import com.mcy.dao.UserDao;
import com.mcy.daomain.User;
import com.mcy.exception.loginOrRegister.LoginNotExitException;
import com.mcy.exception.loginOrRegister.RegisterException;
import com.mcy.exception.user.UserFormatException;
import com.mcy.utils.DataSourceUtils;
import com.sun.deploy.association.RegisterFailedException;
import jdk.nashorn.internal.runtime.UserAccessorProperty;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    /**
     * 登录验证
     * @param tel
     * @param password
     * @return
     */
    public User login(String tel, String password) throws UserFormatException, LoginNotExitException, SQLException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setTel(tel);
        user.setPassword(password);
        User user1 = null;
        if (!user.judgeTelFormat())
        {
            throw new UserFormatException("手机号格式错误");
        }
        if (userDao.queryByTel(user.getTel()) == null)
        {
            throw new LoginNotExitException("账户不存在");
        }
        if (user.judgeAttributeFormat())
        {
            user1 = userDao.queryByTelByPassword(tel,password);
            if (user1 != null)
                return user1;
        }
        else
        {
            throw new UserFormatException("密码格式错误");
        }
        return null;
    }

    /**
     *注册
     * @param user
     * @throws RegisterFailedException
     * @throws SQLException
     */
    //注册
    public void register(User user) throws RegisterException, SQLException, UserFormatException {

        UserDao userDao = new UserDao();
        if (!user.judgeAttributeFormat())
        {
            throw new UserFormatException("格式错误");
        }
        if (userDao.queryByTel(user.getTel()) != null
        )
        {
            throw new RegisterException("账户已经注册");
        }
        else {
            userDao.add(user);
        }
    }
    //找回密码
    public boolean retrievePassword(String tel,String newPassword) throws UserFormatException, SQLException {
        User user = new User();
        user.setTel(tel);
        user.setPassword(newPassword);
        UserDao userDao = new UserDao();
        if (!user.judgeAttributeFormat())
        {
            throw new UserFormatException("格式错误");
        }
        userDao.updatePasswordByTel(tel,newPassword);
        return true;
    }
    //修改资料
    public void modifyDate(User user) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.updateNicknameByTel(user.getTel(),user.getNickname());
    }
    //上传头像
    public void uploadHead(User user) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.updateHeadPortraitByTel(user.getTel(),user.getHeadPortrait());
    }
    //查询头像
    public String queryHeadPortraitByPassword(String tel) throws SQLException {
        UserDao userDao = new UserDao();
        User user = userDao.queryHeadPortraitByPassword(tel);
        return user.getHeadPortrait();
    }
    //分页查询用户
    public List<User> queryUserByPage(int pageNo, int pageSize) throws SQLException {
        pageNo--;
        UserDao userDao = new UserDao();
        List<User> users = userDao.queryUserByPage(pageNo,pageSize);
        return users;
    }
    //判断用户是否注册
    public boolean judgeRegister(String tel) throws SQLException {
        UserDao userDao = new UserDao();
        User user = userDao.queryByTel(tel);
        if (user == null)
        {
            return false;
        }
        else
            return true;
    }
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();
        System.out.println(userService.queryUserByPage(1,1));
    }

    public User queryUserByTel(String tel) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.queryByTel(tel);
    }
}
