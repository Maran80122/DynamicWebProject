package com.turf.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.turf.util.DBUtil;

@WebServlet("/BookingStatusServlet")
public class BookingStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String bookingDate = request.getParameter("date");
        if (bookingDate == null || bookingDate.trim().isEmpty()) {
            out.print("[]");
            return;
        }

        List<String> bookedSlots = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT slot FROM bookings1 WHERE booking_date = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, java.sql.Date.valueOf(bookingDate.trim()));
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String slot = rs.getString("slot");
                        if (slot != null && slot.contains("-")) {
                            bookedSlots.add(slot.trim());
                        }
                    }
                }
            }

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < bookedSlots.size(); i++) {
                json.append("\"").append(bookedSlots.get(i)).append("\"");
                if (i < bookedSlots.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");

            out.print(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
            out.print("[]");
        }
    }
}
