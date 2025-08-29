USE email_service;

DELETE FROM users
WHERE username IN ('ivan', 'olga', 'alex', 'maria');

INSERT INTO users (username, password_hash, email, first_name, last_name, role)
VALUES
    ('ivan', '$2a$10$lRtdcEI9Blt6UJwGdcl80O7gEpq9kTy5Og/vlHiFNcKTwW9V/Tlhm', 'ivan@example.com', 'Ivan', 'Petrov', 'ADMIN'),
    ('olga', '$2a$10$WOYNeouHjhmAFJWYlrCd7eB8uVK0YZ9adG4b4n4iih0ezCx1EAjBq', 'olga@example.com', 'Olga', 'Zoloto', 'ADMIN'),
    ('alex',  '$2a$10$V2k4ScipvmmDgSobuOlgWe2eyxXkvU0wKA5ljy5k.ZUX90ogcgPgq', 'alex@example.com',  'Alex', 'Cool', 'USER'),
    ('maria',  '$2a$10$m9CbgYCDMQC.9lstux20YugGSRDrdZkOyGLKNIy89ODpTGSAwd38i', 'maria@example.com',  'Maria', 'Bel', 'USER');
