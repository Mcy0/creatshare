package com.mcy.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceUtils{
    private static DataSource dataSource = new ComboPooledDataSource();
    public static DataSource getDataSource()
    {
        return dataSource;
    }
    public static Connection getConnection() throws SQLException {
        Connection conn = dataSource.getConnection();
        return conn;
    }
}
