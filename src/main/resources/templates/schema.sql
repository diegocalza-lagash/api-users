DROP TABLE IF EXISTS user_phones;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id UUID NOT NULL,
    created TIMESTAMP(6) NOT NULL,
    email VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL,
    last_login TIMESTAMP(6),
    modified TIMESTAMP(6),
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    token VARCHAR(255),
    username VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);