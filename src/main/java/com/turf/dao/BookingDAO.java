package com.turf.dao;

import java.sql.*;
import java.util.*;
import com.turf.model.Booking;
import com.turf.util.DBUtil;

public class BookingDAO {

    public List<Booking> getBookingsByUser(int userId) throws Exception {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings1 WHERE user_id=? ORDER BY created_at DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setId(rs.getInt("id"));
                    b.setUserId(rs.getInt("user_id"));
                    b.setName(rs.getString("name"));
                    b.setPhone(rs.getString("phone"));
                    b.setEmail(rs.getString("email"));
                    b.setBookingDate(rs.getDate("booking_date"));
                    b.setSlot(rs.getString("slot"));
                    b.setDuration(rs.getInt("duration"));
                    b.setStatus(rs.getString("status"));
                    list.add(b);
                }
            }
        }
        return list;
    }

    public List<Booking> getBookingsByUserId(int userId) throws Exception {
        return getBookingsByUser(userId);
    }
}
