CREATE DATABASE IF NOT EXISTS oauth2_v2_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    is_email_verified BIT NOT NULL,
    avatar_url VARCHAR(500),
    web VARCHAR(500),
    provider VARCHAR(20) NOT NULL,
    provider_id VARCHAR(500) NOT NULL,
    UNIQUE (username),
    UNIQUE (email),
    UNIQUE (provider, provider_id)
);

