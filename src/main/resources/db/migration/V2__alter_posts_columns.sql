-- Flyway migration: adjust posts column types to match entity
ALTER TABLE posts
  ALTER COLUMN author_id TYPE varchar(100),
  ALTER COLUMN author_name TYPE varchar(255),
  ALTER COLUMN status TYPE varchar(50),
  ALTER COLUMN title TYPE varchar(500),
  ALTER COLUMN excerpt TYPE varchar(1000),
  ALTER COLUMN image TYPE varchar(500);

-- Ensure created_at is timestamp without time zone (LocalDate is mapped by app)
ALTER TABLE posts ALTER COLUMN created_at TYPE timestamp;
