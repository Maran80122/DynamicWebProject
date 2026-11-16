package com.turf.servlet;

import com.turf.model.Payment;
import com.turf.util.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/ManagePaymentServlet")
public class ManagePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Payment> payments = new ArrayList<>();

        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT * FROM payments1 ORDER BY created_at DESC";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(query)) {

                while (rs.next()) {
                    Payment payment = new Payment(
                            rs.getInt("id"),
                            rs.getInt("booking_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("slot"),
                            rs.getInt("duration"),
                            rs.getDouble("amount"),
                            rs.getString("payment_method"),
                            rs.getTimestamp("created_at")
                    );
                    payments.add(payment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("payments", payments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("managepayment.jsp");
        dispatcher.forward(request, response);
    }
}
