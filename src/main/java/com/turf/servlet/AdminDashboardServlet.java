package com.turf.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.turf.util.DBUtil;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("adminlogin.jsp");
            return;
        }

        int totalBookings = 0;
        int pendingBookings = 0;
        int totalPayments = 0;
        int totalUsers = 0;

        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement()) {

            try (ResultSet rs1 = st.executeQuery("SELECT COUNT(*) FROM bookings1")) {
                if (rs1.next()) totalBookings = rs1.getInt(1);
            }

            try (ResultSet rs2 = st.executeQuery("SELECT COUNT(*) FROM bookings1 WHERE status='PENDING'")) {
                if (rs2.next()) pendingBookings = rs2.getInt(1);
            }

            try (ResultSet rs3 = st.executeQuery("SELECT COUNT(*) FROM payments1")) {
                if (rs3.next()) totalPayments = rs3.getInt(1);
            }

            try (ResultSet rs4 = st.executeQuery("SELECT COUNT(*) FROM users1")) {
                if (rs4.next()) totalUsers = rs4.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set values in request scope
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("pendingBookings", pendingBookings);
        request.setAttribute("totalPayments", totalPayments);
        request.setAttribute("totalUsers", totalUsers);

        // Forward to JSP
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
