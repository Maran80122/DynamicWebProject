package com.turf.servlet;

import com.turf.dao.BookingDAO;
import com.turf.model.Booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/userBookings")
public class UserBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = new ArrayList<>();
        try {
            bookings = bookingDAO.getBookingsByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load bookings: " + e.getMessage());
        }

        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("userBookings.jsp").forward(request, response);
    }
}
