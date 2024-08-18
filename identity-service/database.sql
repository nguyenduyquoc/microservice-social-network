-- Xóa cơ sở dữ liệu nếu đã tồn tại
DROP DATABASE IF EXISTS identity_service;

-- Tạo lại cơ sở dữ liệu
CREATE DATABASE identity_service;
USE identity_service;

-- Tạo bảng roles
CREATE TABLE roles (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       description TEXT,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

);

-- Tạo bảng permissions
CREATE TABLE permissions (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             description TEXT,
                             created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                             updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

);

-- Tạo bảng accounts
CREATE TABLE accounts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          phone_number VARCHAR(20) UNIQUE,
                          password VARCHAR(255),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng account_role_mapping (Mapping bảng accounts với bảng roles)
CREATE TABLE account_role_mapping (
                                      account_id INT,
                                      role_id INT,
                                      PRIMARY KEY (account_id, role_id),
                                      FOREIGN KEY (account_id) REFERENCES accounts(id),
                                      FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Tạo bảng role_permission_mapping (Mapping bảng roles với bảng permissions)
CREATE TABLE role_permission_mapping (
                                         role_id INT,
                                         permission_id INT,
                                         PRIMARY KEY (role_id, permission_id),
                                         FOREIGN KEY (role_id) REFERENCES roles(id),
                                         FOREIGN KEY (permission_id) REFERENCES permissions(id)
);
