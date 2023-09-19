ALTER TABLE users
DROP COLUMN tokenEmail,
DROP COLUMN tokenExpiration,
ADD token_mail VARCHAR(255),
ADD token_expiration DATETIME;