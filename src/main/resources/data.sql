-- 建立一些假 User
INSERT INTO users (id, email, name) VALUES (1, 'john@example.com', 'John Doe');
INSERT INTO users (id, email, name) VALUES (2, 'jane@example.com', 'Jane Doe');
INSERT INTO users (id, email, name) VALUES (3, 'mary@example.com', 'Mary Lee');

-- 建立一些假 Order
INSERT INTO orders (product_name, amount, price, created_at, user_id)
VALUES ('iPhone 16', 1, 32999, CURRENT_TIMESTAMP, 1);

INSERT INTO orders (product_name, amount, price, created_at, user_id)
VALUES ('MacBook Pro', 1, 59999, CURRENT_TIMESTAMP, 1);

INSERT INTO orders (product_name, amount, price, created_at, user_id)
VALUES ('AirPods Pro', 2, 6999, CURRENT_TIMESTAMP, 2);

INSERT INTO orders (product_name, amount, price, created_at, user_id)
VALUES ('Apple Watch Ultra', 1, 25999, CURRENT_TIMESTAMP, 3);
