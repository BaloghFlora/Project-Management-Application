-- Create: src/main/resources/db/migration/V004__fix_postgresql_auto_increment.sql

-- Fix auto-increment for PostgreSQL by creating sequences and setting them as defaults

-- Create sequences for all tables
CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS teams_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS projects_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS tasks_id_seq START WITH 1 INCREMENT BY 1;

-- Set the sequences as default values for ID columns
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_id_seq');
ALTER TABLE teams ALTER COLUMN id SET DEFAULT nextval('teams_id_seq');
ALTER TABLE projects ALTER COLUMN id SET DEFAULT nextval('projects_id_seq');
ALTER TABLE tasks ALTER COLUMN id SET DEFAULT nextval('tasks_id_seq');

-- Associate sequences with their respective tables (so they get dropped when table is dropped)
ALTER SEQUENCE users_id_seq OWNED BY users.id;
ALTER SEQUENCE teams_id_seq OWNED BY teams.id;
ALTER SEQUENCE projects_id_seq OWNED BY projects.id;
ALTER SEQUENCE tasks_id_seq OWNED BY tasks.id;

-- Set sequence values to start from the next available number
-- This handles the case where you might already have some data
SELECT setval('users_id_seq', COALESCE((SELECT MAX(id) FROM users), 0) + 1, false);
SELECT setval('teams_id_seq', COALESCE((SELECT MAX(id) FROM teams), 0) + 1, false);
SELECT setval('projects_id_seq', COALESCE((SELECT MAX(id) FROM projects), 0) + 1, false);
SELECT setval('tasks_id_seq', COALESCE((SELECT MAX(id) FROM tasks), 0) + 1, false);


-- Add unique constraint on email if it doesn't exist
ALTER TABLE users ADD CONSTRAINT users_email_unique UNIQUE (email);