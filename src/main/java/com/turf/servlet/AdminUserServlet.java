package com.turf.servlet;

import com.turf.model.User;
import com.turf.util.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/AdminUserServlet")
public class AdminUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("adminlogin.jsp");
            return;
        }

        List<User> users = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            String query = "SELECT * FROM users1 ORDER BY id DESC";
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(query)) {

                while (rs.next()) {
                    User u = new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getBoolean("active")
                    );
                    users.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminUsers.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("user_id"));
        String action = request.getParameter("action");

        try (Connection con = DBUtil.getConnection()) {
            String sql = "";
            if ("ACTIVATE".equals(action)) {
                sql = "UPDATE users1 SET active=1 WHERE id=?";
            } else if ("DEACTIVATE".equals(action)) {
                sql = "UPDATE users1 SET active=0 WHERE id=?";
            }

            if (!sql.isEmpty()) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("AdminUserServlet");
    }
}
