package com.turf.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Booking {
    private int id;
    private int userId;
    private String name;
    private String phone;
    private String email;
    private Date bookingDate;
    private String slot;
    private int duration;
    private String status;

    public Booking() {
    }

    public Booking(int id, int userId, String name, String phone, String email,
                   Date bookingDate, String slot, int duration, String status) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bookingDate = bookingDate;
        this.slot = slot;
        this.duration = duration;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public void setBookingDate(Timestamp timestamp) {
        if (timestamp != null) {
            this.bookingDate = new Date(timestamp.getTime());
        }
    }

    public String getDate() {
        return bookingDate != null ? bookingDate.toString() : "";
    }

    public void setDate(String dateStr) {
        if (dateStr != null && !dateStr.trim().isEmpty()) {
            try {
                this.bookingDate = Date.valueOf(dateStr.trim());
            } catch (Exception ignored) {}
        }
    }

    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}