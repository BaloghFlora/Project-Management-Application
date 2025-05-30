-- Check if the constraint already exists and add if not
DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1 FROM information_schema.table_constraints
            WHERE constraint_name = 'uk_users_email'
              AND table_name = 'users'
        ) THEN
            ALTER TABLE users ADD CONSTRAINT uk_users_email UNIQUE (email);
        END IF;
    END $$;

-- Add index for better performance on email lookups
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Add comments for documentation
COMMENT ON COLUMN users.email IS 'User email address - must be unique';
COMMENT ON COLUMN users.role IS 'User role: ADMIN, PROJECT_MANAGER, or TEAM_MEMBER';