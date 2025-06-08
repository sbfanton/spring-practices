CREATE DATABASE IF NOT EXISTS oauth2_v2_db;

create table if not exists users (
    username varchar(255) not null,
    password varchar(255),
    email varchar(255),
    avatar_url varchar(500),
    web varchar(500),
    provider varchar(20),
    primary key (username)
);
