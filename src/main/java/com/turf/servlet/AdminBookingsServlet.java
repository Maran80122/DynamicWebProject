package com.turf.servlet;

import com.turf.model.Booking;
import com.turf.util.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet({"/AdminBookingsServlet", "/AdminBookingServlet"})
public class AdminBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Booking> bookings = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT * FROM bookings1 ORDER BY created_at DESC";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(query)) {

                while (rs.next()) {
                    Booking booking = new Booking(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getDate("booking_date"),
                            rs.getString("slot"),
                            rs.getInt("duration"),
                            rs.getString("status")
                    );
                    bookings.add(booking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("bookings", bookings);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminBookings.jsp");
        dispatcher.forward(request, response);
    }
}
