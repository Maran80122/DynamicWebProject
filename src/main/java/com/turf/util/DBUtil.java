package com.turf.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/turfdb";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASS = ""; // set DB_PASS in your environment instead

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String envUrl = System.getenv("DB_URL");
        String envUser = System.getenv("DB_USER");
        String envPass = System.getenv("DB_PASS");

        String url = (envUrl != null && !envUrl.trim().isEmpty()) ? envUrl : DEFAULT_URL;
        String user = (envUser != null && !envUser.trim().isEmpty()) ? envUser : DEFAULT_USER;
        String pass = (envPass != null) ? envPass : DEFAULT_PASS;

        return DriverManager.getConnection(url, user, pass);
    }
}