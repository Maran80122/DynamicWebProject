-- ==========================================================
-- Indhira's Lake View Turf Database Schema
-- Database Name: turfdb
-- ==========================================================

CREATE DATABASE IF NOT EXISTS turfdb;
USE turfdb;

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users1 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'USER',
    active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Bookings Table
CREATE TABLE IF NOT EXISTS bookings1 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    booking_date DATE NOT NULL,
    slot VARCHAR(50) NOT NULL,
    duration INT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bookings_user FOREIGN KEY (user_id) REFERENCES users1(id) ON DELETE CASCADE
);

-- 3. Payments Table
CREATE TABLE IF NOT EXISTS payments1 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    user_id INT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    slot VARCHAR(50) NOT NULL,
    duration INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payments_booking FOREIGN KEY (booking_id) REFERENCES bookings1(id) ON DELETE CASCADE,
    CONSTRAINT fk_payments_user FOREIGN KEY (user_id) REFERENCES users1(id) ON DELETE SET NULL
);

-- 4. Initial Default Admin User (admin@turf.com / admin123)
INSERT INTO users1 (name, email, phone, password, role, active)
VALUES ('Admin', 'admin@turf.com', '9876543210', 'admin123', 'ADMIN', 1)
ON DUPLICATE KEY UPDATE role='ADMIN';

-- 5. Optional Demo User (user@turf.com / user123)
INSERT INTO users1 (name, email, phone, password, role, active)
VALUES ('Demo User', 'user@turf.com', '9123456789', 'user123', 'USER', 1)
ON DUPLICATE KEY UPDATE id=id;
