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

-- Module 6: Estimates Table
CREATE TABLE IF NOT EXISTS estimates (
    estimated_id     INT PRIMARY KEY AUTO_INCREMENT,
    chain_id         INT NOT NULL,
    group_name       VARCHAR(50) NOT NULL,
    brand_name       VARCHAR(50) NOT NULL,
    zone_name        VARCHAR(50) NOT NULL,
    service          VARCHAR(100) NOT NULL,
    qty              INT NOT NULL,
    cost_per_unit    FLOAT NOT NULL,
    total_cost       FLOAT NOT NULL,
    delivery_date    DATE NOT NULL,
    delivery_details VARCHAR(100),
    created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (chain_id) REFERENCES chains(chain_id)
);

-- Module 7: Invoices Table
CREATE TABLE IF NOT EXISTS invoices (
    id               INT PRIMARY KEY AUTO_INCREMENT,
    invoice_no       INT NOT NULL UNIQUE,
    estimated_id     INT NOT NULL,
    chain_id         INT NOT NULL,
    service_details  VARCHAR(50) NOT NULL,
    qty              INT NOT NULL,
    cost_per_qty     FLOAT NOT NULL,
    amount_payable   FLOAT NOT NULL,
    balance          FLOAT DEFAULT 0,
    date_of_payment  DATETIME,
    date_of_service  DATE,
    delivery_details VARCHAR(100),
    email_id         VARCHAR(50),
    FOREIGN KEY (estimated_id) REFERENCES estimates(estimated_id),
    FOREIGN KEY (chain_id) REFERENCES chains(chain_id)
);
