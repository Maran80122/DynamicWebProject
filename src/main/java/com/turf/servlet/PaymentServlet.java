package com.turf.servlet;

import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.turf.util.DBUtil;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("paymentpage.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Get booking ID from parameter or session
        String bookingIdParam = request.getParameter("booking_id");
        if (bookingIdParam == null || bookingIdParam.trim().isEmpty()) {
            bookingIdParam = request.getParameter("bookingId");
        }

        int bookingId = 0;
        if (bookingIdParam != null && !bookingIdParam.trim().isEmpty()) {
            try {
                bookingId = Integer.parseInt(bookingIdParam.trim());
            } catch (NumberFormatException ignored) {}
        }

        if (bookingId == 0 && session != null) {
            Object sBid = session.getAttribute("booking_id");
            if (sBid == null) sBid = session.getAttribute("bookingId");
            if (sBid != null) {
                try {
                    bookingId = Integer.parseInt(sBid.toString());
                } catch (NumberFormatException ignored) {}
            }
        }

        if (bookingId == 0) {
            request.setAttribute("error", "No booking found. Please book first.");
            request.getRequestDispatcher("paymentpage.jsp").forward(request, response);
            return;
        }

        // Get user id if logged in
        Integer userId = null;
        if (session != null) {
            Object uidObj = session.getAttribute("user_id");
            if (uidObj == null) uidObj = session.getAttribute("userId");
            if (uidObj != null) {
                try {
                    userId = Integer.parseInt(uidObj.toString());
                } catch (Exception ignored) {}
            }
        }

        // Form parameters with session fallbacks
        String name = request.getParameter("name");
        if ((name == null || name.trim().isEmpty()) && session != null) {
            name = (String) session.getAttribute("name");
        }

        String email = request.getParameter("email");
        if ((email == null || email.trim().isEmpty()) && session != null) {
            email = (String) session.getAttribute("email");
        }

        String phone = request.getParameter("phone");
        if ((phone == null || phone.trim().isEmpty()) && session != null) {
            phone = (String) session.getAttribute("phone");
        }

        String slot = request.getParameter("slot");
        if ((slot == null || slot.trim().isEmpty()) && session != null) {
            slot = (String) session.getAttribute("slot");
        }

        String durationStr = request.getParameter("duration");
        String amountStr = request.getParameter("amount");

        String method = request.getParameter("payment_method");
        if (method == null || method.trim().isEmpty()) {
            method = request.getParameter("paymentMethod");
        }
        if (method != null) {
            method = method.trim().toUpperCase();
        }

        int duration = 1;
        try {
            if (durationStr != null && !durationStr.trim().isEmpty()) {
                duration = Integer.parseInt(durationStr.trim());
            } else if (session != null && session.getAttribute("duration") != null) {
                duration = Integer.parseInt(session.getAttribute("duration").toString());
            }
        } catch (NumberFormatException e) {
            duration = 1;
        }

        double amount = 0.0;
        try {
            if (amountStr != null && !amountStr.trim().isEmpty()) {
                amount = Double.parseDouble(amountStr.trim());
            } else if (session != null && session.getAttribute("amount") != null) {
                amount = Double.parseDouble(session.getAttribute("amount").toString());
            }
        } catch (NumberFormatException e) {
            amount = duration * 1000.0;
        }

        if (amount <= 0) {
            amount = duration * 1000.0;
        }

        // Validate payment method safely
        if (method == null || (!method.equals("UPI") && !method.equals("CARD") && !method.equals("CASH"))) {
            request.setAttribute("error", "Please select a valid payment method (UPI, CARD, or CASH).");
            request.getRequestDispatcher("paymentpage.jsp").forward(request, response);
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            con.setAutoCommit(false);

            // Insert payment record into payments1 (with user_id)
            String insertSQL = "INSERT INTO payments1 (booking_id, user_id, name, email, phone, slot, duration, amount, payment_method) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
                ps.setInt(1, bookingId);
                if (userId != null) {
                    ps.setInt(2, userId);
                } else {
                    ps.setNull(2, java.sql.Types.INTEGER);
                }
                ps.setString(3, name != null ? name : "Customer");
                ps.setString(4, email != null ? email : "");
                ps.setString(5, phone != null ? phone : "");
                ps.setString(6, slot != null ? slot : "N/A");
                ps.setInt(7, duration);
                ps.setDouble(8, amount);
                ps.setString(9, method);

                if (ps.executeUpdate() == 0) {
                    con.rollback();
                    request.setAttribute("error", "Payment insertion failed.");
                    request.getRequestDispatcher("paymentpage.jsp").forward(request, response);
                    return;
                }
            }

            // Update booking status in bookings1
            String updateSQL = "UPDATE bookings1 SET status = 'PAID' WHERE id = ?";
            try (PreparedStatement ps2 = con.prepareStatement(updateSQL)) {
                ps2.setInt(1, bookingId);
                ps2.executeUpdate();
            }

            con.commit();

            if (session != null) {
                session.removeAttribute("booking_id");
                session.removeAttribute("bookingId");
                session.setAttribute("status", "PAID");
            }

            response.sendRedirect("paymentsuccess.jsp?bookingId=" + bookingId);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("paymentpage.jsp").forward(request, response);
        }
    }
}
