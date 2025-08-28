USE email_service;

INSERT INTO users (username, password_hash, email, first_name, last_name, role)
VALUES
    ('admin', '$2b$10$4uwAU5Bto4dzw9gz5CEEOOka9Hgo8uHH5wRW.pE4etugiStEMB4Oa', 'admin@example.com', 'Alex', 'Bur', 'ADMIN'),
    ('user',  '$2b$10$wSm57v.13jH5TJsw2EzFDO51/EMrdo5Hhc/spHCDXJPuw3z26MTF2', 'user@example.com',  'Tom', 'Tor', 'USER');