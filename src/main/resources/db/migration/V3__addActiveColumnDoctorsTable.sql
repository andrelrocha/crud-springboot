ALTER TABLE doctors
ADD COLUMN active TINYINT;

UPDATE doctors SET active = 1;