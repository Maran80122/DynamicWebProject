package com.turf.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {
    private int id;
    private int bookingId;
    private int userId;
    private String name;
    private String email;
    private String phone;
    private String slot;
    private int duration;
    private double amount;
    private String paymentMethod;
    private Timestamp createdAt;

    public Payment() {
    }

    public Payment(int id, int bookingId, String name, String email, String phone,
                   String slot, int duration, double amount, String paymentMethod, Timestamp createdAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.slot = slot;
        this.duration = duration;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

    public Payment(int id, int bookingId, int userId, String name, String email, String phone,
                   String slot, int duration, double amount, String paymentMethod, Timestamp createdAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.slot = slot;
        this.duration = duration;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.amount = amount.doubleValue();
        }
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
