package com.turf.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.turf.util.DBUtil;

@WebServlet("/AdminApprovalServlet")
public class AdminApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("booking_id");
        String action = request.getParameter("action");

        if (bookingIdStr == null || action == null) {
            response.sendRedirect("AdminBookingsServlet");
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdStr);

            try (Connection con = DBUtil.getConnection()) {
                String sql = "UPDATE bookings1 SET status = ? WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, action);
                    ps.setInt(2, bookingId);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("AdminBookingsServlet");
    }
}
