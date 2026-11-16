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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection con = DBUtil.getConnection()) {
            String sql = "SELECT * FROM users1 WHERE email=? AND password=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        String name = rs.getString("name");
                        String role = rs.getString("role");

                        HttpSession session = request.getSession(true);
                        session.setAttribute("user_id", userId);
                        session.setAttribute("userId", userId);
                        session.setAttribute("user_name", name);
                        session.setAttribute("userName", name);
                        session.setAttribute("role", role);

                        // Redirect to index.jsp (Home page with active login state)
                        response.sendRedirect("index.jsp");
                    } else {
                        out.println("<p style='color:red;'>❌ Invalid email or password!</p>");
                        request.getRequestDispatcher("login.jsp").include(request, response);
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
        response.sendRedirect("login.jsp");
    }
}
