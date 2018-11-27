package com.mcy.dao;

import com.mcy.daomain.User;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class UserDao {
    public void add(User user) throws SQLException {

        String sql = "insert into users values(?,?,?,?,?,?,?,null)";


    }
}
