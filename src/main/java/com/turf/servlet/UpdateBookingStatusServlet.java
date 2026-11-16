package com.turf.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

import com.turf.util.DBUtil;

@WebServlet("/UpdateBookingStatusServlet")
public class UpdateBookingStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String status = request.getParameter("status");

        try (Connection con = DBUtil.getConnection()) {
            String sql = "UPDATE bookings1 SET status=? WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, status);
                ps.setInt(2, bookingId);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("AdminBookingsServlet");
    }
}
