package com.turf.servlet;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

import com.turf.util.DBUtil;

@WebServlet("/BookingActionServlet")
public class BookingActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        if (id == null || action == null) {
            response.sendRedirect("AdminBookingsServlet");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE bookings1 SET status=? WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, action);
                ps.setInt(2, Integer.parseInt(id));
                ps.executeUpdate();
            }

            response.sendRedirect("AdminBookingsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error updating booking status: " + e.getMessage());
        }
    }
}
