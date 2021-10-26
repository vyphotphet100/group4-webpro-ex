package com.group4.ex12_2.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPool {
    private static ConnectionPool pool;
    private static ResourceBundle rb;

    private ConnectionPool() {
        rb = ResourceBundle.getBundle("database");
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null)
            pool = new ConnectionPool();
        return pool;
    }

    public Connection getConnection() {
        // load the driver
        try {
            Class.forName(rb.getString("driverName"));

            // get a connection
            String dbURL = rb.getString("url");
            String username = rb.getString("username");
            String password = rb.getString("password");

            return DriverManager.getConnection(
                    dbURL, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}