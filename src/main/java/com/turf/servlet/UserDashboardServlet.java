package com.turf.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.turf.model.Booking;
import com.turf.util.DBUtil;

@WebServlet({"/UserDashboardServlet", "/userDashboard"})
public class UserDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Object uidObj = session.getAttribute("user_id");
        if (uidObj == null) {
            uidObj = session.getAttribute("userId");
        }

        if (uidObj == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(uidObj.toString());
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Booking> bookings = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT id, user_id, name, phone, email, booking_date, slot, duration, status " +
                         "FROM bookings1 WHERE user_id = ? ORDER BY booking_date DESC";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Booking b = new Booking();
                        b.setId(rs.getInt("id"));
                        b.setUserId(rs.getInt("user_id"));
                        b.setName(rs.getString("name"));
                        b.setPhone(rs.getString("phone"));
                        b.setEmail(rs.getString("email"));
                        b.setBookingDate(rs.getDate("booking_date"));
                        b.setDate(rs.getDate("booking_date") != null ? rs.getDate("booking_date").toString() : "");
                        b.setSlot(rs.getString("slot"));
                        b.setDuration(rs.getInt("duration"));
                        b.setStatus(rs.getString("status"));
                        bookings.add(b);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("userdashboard.jsp").forward(request, response);
    }
}
