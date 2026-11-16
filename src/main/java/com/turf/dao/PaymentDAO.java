package com.turf.dao;

import java.sql.*;
import java.util.*;
import com.turf.model.Payment;
import com.turf.util.DBUtil;

public class PaymentDAO {

    public List<Payment> getPaymentsByUser(int userId) throws Exception {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments1 WHERE user_id=? ORDER BY created_at DESC";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Payment p = new Payment();
                    p.setId(rs.getInt("id"));
                    p.setBookingId(rs.getInt("booking_id"));
                    p.setUserId(rs.getInt("user_id"));
                    p.setName(rs.getString("name"));
                    p.setEmail(rs.getString("email"));
                    p.setPhone(rs.getString("phone"));
                    p.setSlot(rs.getString("slot"));
                    p.setDuration(rs.getInt("duration"));
                    p.setAmount(rs.getDouble("amount"));
                    p.setPaymentMethod(rs.getString("payment_method"));
                    p.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(p);
                }
            }
        }
        return list;
    }

    public List<Payment> getPaymentsByUserId(int userId) throws Exception {
        return getPaymentsByUser(userId);
    }
}
