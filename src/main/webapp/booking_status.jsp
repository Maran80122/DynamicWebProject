<%@ page session="true" %>
<%@ page import="java.sql.*, com.turf.util.DBUtil" %>
<%
    Integer userId = null;
    Object uidObj = session.getAttribute("user_id");
    if (uidObj == null) {
        uidObj = session.getAttribute("userId");
    }
    if (uidObj != null) {
        try {
            userId = Integer.parseInt(uidObj.toString());
        } catch (Exception ignored) {}
    }

    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Booking Status</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
  <h2 class="mb-3">Booking Status</h2>
  <table class="table table-bordered table-striped">
    <thead class="table-dark">
      <tr>
        <th>Booking ID</th>
        <th>Slot</th>
        <th>Duration</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
    <%
      try {
          conn = DBUtil.getConnection();
          String sql = "SELECT id, slot, duration, status FROM bookings1 WHERE user_id=? ORDER BY created_at DESC";
          ps = conn.prepareStatement(sql);
          ps.setInt(1, userId);
          rs = ps.executeQuery();

          boolean hasRows = false;
          while (rs.next()) {
              hasRows = true;
    %>
            <tr>
              <td><%= rs.getInt("id") %></td>
              <td><%= rs.getString("slot") %></td>
              <td><%= rs.getInt("duration") %> hrs</td>
              <td>
                <% 
                   String st = rs.getString("status");
                   if("CONFIRMED".equalsIgnoreCase(st)) { %>
                      <span class="badge bg-success">✅ Confirmed</span>
                <% } else if("CANCELLED".equalsIgnoreCase(st)) { %>
                      <span class="badge bg-danger">❌ Cancelled</span>
                <% } else if("PAID".equalsIgnoreCase(st)) { %>
                      <span class="badge bg-warning text-dark">⏳ Waiting for Admin Approval</span>
                <% } else { %>
                      <span class="badge bg-secondary">🟡 Pending</span>
                <% } %>
              </td>
            </tr>
    <%
          }
          if (!hasRows) {
    %>
            <tr><td colspan="4" class="text-center">No bookings found.</td></tr>
    <%
          }
      } catch(Exception e) {
          e.printStackTrace();
          out.println("<div class='alert alert-danger'>Error loading status: " + e.getMessage() + "</div>");
      } finally {
          try { if(rs!=null) rs.close(); } catch(Exception e) {}
          try { if(ps!=null) ps.close(); } catch(Exception e) {}
          try { if(conn!=null) conn.close(); } catch(Exception e) {}
      }
    %>
    </tbody>
  </table>
  <a href="userDashboard" class="btn btn-primary mt-2">⬅ Back to Dashboard</a>
</body>
</html>
