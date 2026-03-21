CREATE DATABASE IF NOT EXISTS mis_invoicing_db;
USE mis_invoicing_db;

-- Module 1: Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id       INT PRIMARY KEY AUTO_INCREMENT,
    full_name     VARCHAR(100) NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL,
    status        ENUM('active','inactive') DEFAULT 'active',
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Module 2: Group Table
CREATE TABLE IF NOT EXISTS `group` (
    group_id    INT PRIMARY KEY AUTO_INCREMENT,
    group_name  VARCHAR(255) NOT NULL UNIQUE,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Module 3: Chains Table
CREATE TABLE IF NOT EXISTS chains (
    chain_id     INT PRIMARY KEY AUTO_INCREMENT,
    company_name VARCHAR(255) NOT NULL,
    gstn_no      VARCHAR(15) NOT NULL UNIQUE,
    group_id     INT NOT NULL,
    is_active    BOOLEAN DEFAULT TRUE,
    created_at   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES `group`(group_id)
);

-- Module 4: Brands Table
CREATE TABLE IF NOT EXISTS brands (
    brand_id    INT PRIMARY KEY AUTO_INCREMENT,
    brand_name  VARCHAR(50) NOT NULL,
    chain_id    INT NOT NULL,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (chain_id) REFERENCES chains(chain_id)
);

-- Module 5: SubZones Table
CREATE TABLE IF NOT EXISTS subzones (
    zone_id     INT PRIMARY KEY AUTO_INCREMENT,
    zone_name   VARCHAR(50) NOT NULL,
    brand_id    INT NOT NULL,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id)
);
