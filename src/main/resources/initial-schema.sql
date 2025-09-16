-- Drop tables if they exist to avoid conflicts
DROP TABLE IF EXISTS user_phones;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL,
    last_login TIMESTAMP,
    modified TIMESTAMP,
    is_active BOOLEAN NOT NULL,
    token VARCHAR(255)
);

-- Create user_phones table
CREATE TABLE user_phones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id UUID NOT NULL,
    number VARCHAR(20) NOT NULL,
    citycode VARCHAR(10) NOT NULL,
    contrycode VARCHAR(10) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

