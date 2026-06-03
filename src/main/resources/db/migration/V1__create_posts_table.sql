-- Flyway migration: create posts table
CREATE TABLE IF NOT EXISTS posts (
  id BIGSERIAL PRIMARY KEY,
  author_id INTEGER,
  author_name VARCHAR(255),
  category VARCHAR(255),
  content TEXT,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
  excerpt TEXT,
  image VARCHAR(512),
  status INTEGER DEFAULT 1,
  title VARCHAR(512)
);
