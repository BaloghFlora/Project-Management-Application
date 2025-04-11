INSERT INTO roles (id, role_name) VALUES
                                      (0, 'admin'),
                                      (1, 'project_manager'),
                                      (2, 'team_member');

-- Insert into teams
INSERT INTO teams (id, team_name, description) VALUES
                                                   ('0', 'Alpha Team', 'Handles frontend development'),
                                                   ('1', 'Beta Team', 'Handles backend development');

-- Insert into users
INSERT INTO users (id, name, email, password, role_id, team_id) VALUES
                                                                    ('0', 'Alice Johnson', 'alice@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 0, '0'),
                                                                    ('1', 'Bob Smith', 'bob@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 1, '0'),
                                                                    ('2', 'Charlie Brown', 'charlie@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 2, '0'),
                                                                    ('3', 'Diana Prince', 'diana@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 1 , '0');

-- Insert into projects
INSERT INTO projects (id, project_name, description, team_id) VALUES
                                                                  ('0', 'Project Phoenix', 'Revamp old legacy system', '0'),
                                                                  ('1', 'Project Titan', 'Develop new microservices API', '1');

-- Insert into tasks
INSERT INTO tasks (id, task_name, description, status, project_id, assigned_user_id) VALUES
                                                                                         ('0', 'Design UI mockups', 'Create wireframes for new dashboard', 'In Progress','0', '0'),
                                                                                         ('1', 'Setup CI/CD pipeline', 'Configure GitHub Actions', 'To Do', '0', '1'),
                                                                                         ('2', 'Write unit tests', 'Add test cases for user module', 'In Progress', '0', '2'),
                                                                                         ('3', 'Sprint planning', 'Prepare backlog for next sprint', 'Done', '0', '2');
