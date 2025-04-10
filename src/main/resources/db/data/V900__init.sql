INSERT INTO roles (id, role_name) VALUES
                                      (1, 'admin'),
                                      (2, 'project_manager'),
                                      (3, 'team_member');

-- Insert into teams
INSERT INTO teams (id, team_name, description) VALUES
                                                   (1, 'Alpha Team', 'Handles frontend development'),
                                                   (2, 'Beta Team', 'Handles backend development');

-- Insert into users
INSERT INTO users (id, name, email, password, role_id, team_id) VALUES
                                                                    (1, 'Alice Johnson', 'alice@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 1, 1),
                                                                    (2, 'Bob Smith', 'bob@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 2, 1),
                                                                    (3, 'Charlie Brown', 'charlie@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 3, 2),
                                                                    (4, 'Diana Prince', 'diana@example.com', '$2a$12$ZfGXDxUDP44M4c82rRl64.JeoUtuThIX9iBZ55amQbEi8DLQn403m', 2 , 2);

-- Insert into projects
INSERT INTO projects (id, project_name, description, team_id) VALUES
                                                                  (1, 'Project Phoenix', 'Revamp old legacy system', 1),
                                                                  (2, 'Project Titan', 'Develop new microservices API', 2);

-- Insert into tasks
INSERT INTO tasks (id, task_name, description, status, project_id, assigned_user_id) VALUES
                                                                                         (1, 'Design UI mockups', 'Create wireframes for new dashboard', 'In Progress', 1, 1),
                                                                                         (2, 'Setup CI/CD pipeline', 'Configure GitHub Actions', 'To Do', 2, 3),
                                                                                         (3, 'Write unit tests', 'Add test cases for user module', 'In Progress', 2, 4),
                                                                                         (4, 'Sprint planning', 'Prepare backlog for next sprint', 'Done', 1, 2);
