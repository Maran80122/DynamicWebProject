package com.turf.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

import com.turf.util.DBUtil;

@WebServlet("/DeletePaymentServlet")
public class DeletePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            try (Connection con = DBUtil.getConnection()) {
                String query = "DELETE FROM payments1 WHERE id=?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
            }

            response.sendRedirect("ManagePaymentServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error deleting payment: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
