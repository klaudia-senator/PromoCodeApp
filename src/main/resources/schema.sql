CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(10, 2) NOT NULL,
                          currency VARCHAR(3) NOT NULL
);

CREATE TABLE promo_codes (
                             code VARCHAR(24) PRIMARY KEY,
                             expiration_date DATE NOT NULL,
                             discount DECIMAL(10, 2),
                             currency VARCHAR(3) NOT NULL,
                             max_usage INT NOT NULL,
                             usages INT DEFAULT 0
);

CREATE TABLE purchases (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           product_id BIGINT NOT NULL,
                           promo_code VARCHAR(24),
                           date_of_purchases TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           price DECIMAL(10, 2) NOT NULL,
                           discount DECIMAL(10, 2) DEFAULT 0,
                           FOREIGN KEY (product_id) REFERENCES products(id),
                           FOREIGN KEY (promo_code) REFERENCES promo_codes(code)
);
