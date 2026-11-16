package com.turf.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

import com.turf.util.DBUtil;

@WebServlet("/UpdatePaymentServlet")
public class UpdatePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String paymentMethod = request.getParameter("paymentMethod");

            try (Connection con = DBUtil.getConnection()) {
                String query = "UPDATE payments1 SET amount=?, payment_method=? WHERE id=?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setDouble(1, amount);
                    ps.setString(2, paymentMethod);
                    ps.setInt(3, id);
                    ps.executeUpdate();
                }
            }

            response.sendRedirect("ManagePaymentServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating payment: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
