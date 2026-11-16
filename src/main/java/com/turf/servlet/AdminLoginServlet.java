package com.turf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.turf.util.DBUtil;

@WebServlet({"/adminlogin", "/AdminLoginServlet"})
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT * FROM users1 WHERE email=? AND password=? AND role='ADMIN'";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        HttpSession session = request.getSession();
                        session.setAttribute("adminId", rs.getInt("id"));
                        session.setAttribute("adminName", rs.getString("name"));
                        session.setAttribute("role", "ADMIN");

                        response.sendRedirect("AdminDashboardServlet");
                        return;
                    } else {
                        out.println("<p style='color:red;'>❌ Invalid admin credentials!</p>");
                        request.getRequestDispatcher("adminlogin.jsp").include(request, response);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red;'>⚠️ Error: " + e.getMessage() + "</p>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("adminlogin.jsp");
    }
}
