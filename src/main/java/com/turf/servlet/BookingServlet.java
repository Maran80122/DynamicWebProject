package com.turf.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.turf.util.DBUtil;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        } catch (Exception e) {
            response.sendRedirect("login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String date = request.getParameter("date");
        String slot = request.getParameter("slot");
        String durationStr = request.getParameter("duration");

        int duration = 1;
        try {
            if (durationStr != null && !durationStr.trim().isEmpty()) {
                duration = Integer.parseInt(durationStr.trim());
            }
        } catch (NumberFormatException e) {
            duration = 1;
        }

        // Convert to 24-hour format if needed
        slot = convertTo24Hour(slot);

        // Calculate rate: 1000/hr (day) or 1200/hr (night)
        double amount = calculateAmount(slot, duration);

        try (Connection con = DBUtil.getConnection()) {
            if (!userExists(con, userId)) {
                session.invalidate();
                response.sendRedirect("login.jsp");
                return;
            }

            String sql = "INSERT INTO bookings1 (user_id, name, phone, email, booking_date, slot, duration, status) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setString(2, name);
                ps.setString(3, phone);
                ps.setString(4, email);
                ps.setDate(5, java.sql.Date.valueOf(date));
                ps.setString(6, slot);
                ps.setInt(7, duration);
                ps.setString(8, "PENDING");

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            int bookingId = rs.getInt(1);
                            session.setAttribute("booking_id", bookingId);
                            session.setAttribute("bookingId", bookingId);
                            session.setAttribute("amount", amount);
                            session.setAttribute("duration", duration);
                            session.setAttribute("slot", slot);
                            session.setAttribute("name", name);
                            session.setAttribute("phone", phone);
                            session.setAttribute("email", email);
                            session.setAttribute("status", "PENDING");

                            response.sendRedirect("paymentpage.jsp");
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Server error: " + e.getMessage());
            request.getRequestDispatcher("Booking.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("paymentpage.jsp");
    }

    private boolean userExists(Connection con, int userId) throws SQLException {
        String q = "SELECT 1 FROM users1 WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private double calculateAmount(String slot, int duration) {
        if (slot == null || !slot.contains(":")) {
            return duration * 1000.0;
        }
        try {
            int startHour = Integer.parseInt(slot.split(":")[0].trim());
            return (startHour >= 6 && startHour < 18) ? (duration * 1000.0) : (duration * 1200.0);
        } catch (Exception e) {
            return duration * 1000.0;
        }
    }

    private String convertTo24Hour(String slot) {
        if (slot == null || !slot.contains(" - ")) {
            return slot;
        }
        try {
            String[] parts = slot.split(" - ");
            if (parts[0].toUpperCase().contains("AM") || parts[0].toUpperCase().contains("PM")) {
                java.text.SimpleDateFormat inFormat = new java.text.SimpleDateFormat("hh:mm a");
                java.text.SimpleDateFormat outFormat = new java.text.SimpleDateFormat("HH:mm");
                String start24 = outFormat.format(inFormat.parse(parts[0].trim()));
                String end24 = outFormat.format(inFormat.parse(parts[1].trim()));
                return start24 + " - " + end24;
            }
            return slot;
        } catch (Exception e) {
            return slot;
        }
    }
}
