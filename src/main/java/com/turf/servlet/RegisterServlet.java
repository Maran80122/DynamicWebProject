package com.turf.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.turf.util.DBUtil;

@WebServlet({"/register", "/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Phone number validation
        if (phone == null || !phone.matches("\\d{10}")) {
            request.setAttribute("errorMsg", "⚠️ Phone number must be exactly 10 digits.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try (Connection con = DBUtil.getConnection()) {
            String sql = "INSERT INTO users1(name, phone, email, password, role, active) VALUES (?, ?, ?, ?, 'USER', 1)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, phone);
                ps.setString(3, email);
                ps.setString(4, password);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    request.getRequestDispatcher("registerSuccess.jsp").forward(request, response);
                    return;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            request.getRequestDispatcher("registerFailed.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "⚠️ Something went wrong: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
