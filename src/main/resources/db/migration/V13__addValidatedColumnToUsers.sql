ALTER TABLE users
ADD validated BOOLEAN DEFAULT false,
ADD token_confirmation VARCHAR(255);
