-- Create roles table
CREATE TABLE IF NOT EXISTS roles (
                       id INT PRIMARY KEY,
                       role_name VARCHAR(255) NOT NULL
);

-- Create teams table
CREATE TABLE IF NOT EXISTS teams (
                       id INT PRIMARY KEY,
                       team_name VARCHAR(255) NOT NULL,
                       description VARCHAR(255)
);

-- Create users table
CREATE TABLE IF NOT EXISTS users (
                       id INT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role_id INT,
                       team_id INT,
                       FOREIGN KEY (role_id) REFERENCES roles(id),
                       FOREIGN KEY (team_id) REFERENCES teams(id)
);

-- Create projects table
CREATE TABLE IF NOT EXISTS projects (
                          id INT PRIMARY KEY,
                          project_name VARCHAR(255) NOT NULL,
                          description VARCHAR(255),
                          team_id INT NOT NULL,
                          FOREIGN KEY (team_id) REFERENCES teams(id)
);

-- Create tasks table
CREATE TABLE IF NOT EXISTS tasks (
                       id INT PRIMARY KEY,
                       task_name VARCHAR(255) NOT NULL,
                       description VARCHAR(255),
                       status VARCHAR(255),
                       project_id INT,
                       assigned_user_id INT,
                       FOREIGN KEY (project_id) REFERENCES projects(id),
                       FOREIGN KEY (assigned_user_id) REFERENCES users(id)
);
