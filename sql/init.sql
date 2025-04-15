-- Removes the database
DROP DATABASE IF EXISTS roll_the_table;

-- Creates the schema roll_the_table
CREATE SCHEMA roll_the_table;

-- Use the schema roll_the_table
USE roll_the_table;

-- Creates the table customer
CREATE TABLE IF NOT EXISTS customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    psw_hash VARCHAR(255) NOT NULL
);

-- Creates the table payment_method
CREATE TABLE IF NOT EXISTS payment_method (
    payment_method_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    owner VARCHAR(255) NOT NULL,
    expire_date DATE NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE
);

-- Creates the table purchase
CREATE TABLE IF NOT EXISTS purchase (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    payment_method_id INT DEFAULT NULL,
    purchase_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    shipping_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (payment_method_id) REFERENCES payment_method(payment_method_id) ON DELETE SET NULL
);

-- Creates the table invoice
CREATE TABLE IF NOT EXISTS invoice (
    invoice_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT DEFAULT NULL,
    purchase_id INT DEFAULT NULL,
    iva DECIMAL(5, 2) NOT NULL,
    notes VARCHAR(120),
    taxable DECIMAL(10, 2) NOT NULL,
    tax DECIMAL(10, 2) NOT NULL,
    invoice_pdf_path VARCHAR(255) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE SET NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchase(order_id) ON DELETE SET NULL
);

-- Creates the table product
CREATE TABLE IF NOT EXISTS product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    purchase_price DECIMAL(10, 2) NOT NULL,
    iva DECIMAL(5, 2) NOT NULL,
    sale_price DECIMAL(10, 2) NOT NULL,
    images_path VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    vendor VARCHAR(100) NOT NULL,
    stock_quantity INT NOT NULL,
    discount DECIMAL(5, 2) DEFAULT 0 NOT NULL,
    category VARCHAR(50) NOT NULL,
    minimum_age INT,
    barcode VARCHAR(50) UNIQUE NOT NULL,
    sale_status ENUM('on_sale', 'out_of_stock', 'not_available', 'no_longer_on_sale') DEFAULT 'on_sale'
);

-- Creates the table purchase_items
CREATE TABLE IF NOT EXISTS purchase_items (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price_at_purchase DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES purchase(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    PRIMARY KEY (order_id, product_id)
);

-- Creates the table address
CREATE TABLE IF NOT EXISTS address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    postcode VARCHAR(20) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street_name VARCHAR(100) NOT NULL,
    street_number VARCHAR(10) NOT NULL,
    type VARCHAR(50) NOT NULL
);

-- Creates the table uses
CREATE TABLE IF NOT EXISTS uses (
    customer INT NOT NULL,
    address INT NOT NULL,
    FOREIGN KEY (customer) REFERENCES customer(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (address) REFERENCES address(address_id) ON DELETE CASCADE,
    PRIMARY KEY(customer, address)
);

-- Creates the table cart
CREATE TABLE IF NOT EXISTS cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE
);

-- Creates the table cart_contains
CREATE TABLE IF NOT EXISTS cart_contains (
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
    PRIMARY KEY(product_id, cart_id)
);

-- Creates the table admin
CREATE TABLE IF NOT EXISTS `admin` (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    psw_hash VARCHAR(255) NOT NULL
);