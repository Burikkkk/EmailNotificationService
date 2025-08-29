USE email_service;

INSERT INTO users (username, password_hash, email, first_name, last_name, role)
VALUES
    ('ivan', '$2a$10$7N7v1kJ6Nf9xCq8h3WfK8uYq3sJ0c1Z9pHj/6v0K3f5G1y9tQpSg.', 'ivan@example.com', 'Ivan', 'Petrov', 'ADMIN'),
    ('olga', '$2a$10$F8p4kL9Jd3sP1oW6YhB2eTq8rN1vC7mQxLz/9v2N4b6H3y1rM0Pq', 'olga@example.com', 'Olga', 'Zoloto', 'ADMIN'),
    ('alex',  '$2a$10$G6k2jH3Vn8rY5bQ1LwF3cTq2sZ4mK9pXyLw/1v0H6d9F3j8pS4Tr', 'alex@example.com',  'Alex', 'Cool', 'USER'),
    ('maria',  '$2a$10$H4m5kJ9Qp2sR6vX3TnB7eKq1rL0yC5wZxFj/3v2N7b8H1p4yS6Lt', 'maria@example.com',  'Maria', 'Bel', 'USER');