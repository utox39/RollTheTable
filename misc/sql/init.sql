DROP DATABASE IF EXISTS roll_the_table;

CREATE SCHEMA roll_the_table;

USE roll_the_table;

CREATE TABLE IF NOT EXISTS customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    age INT NOT NULL
);

INSERT INTO customer (name, age) VALUE ("Totti", 48);