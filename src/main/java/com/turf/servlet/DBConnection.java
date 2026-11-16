package com.turf.servlet;

import java.sql.Connection;
import com.turf.util.DBUtil;

public class DBConnection {
    public static Connection getConnection() {
        try {
            return DBUtil.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
