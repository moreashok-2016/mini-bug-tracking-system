CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS issue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    priority VARCHAR(20),
    status VARCHAR(20),
    assignee_id BIGINT,
    created_date DATETIME,
    CONSTRAINT fk_assignee FOREIGN KEY (assignee_id) REFERENCES user(id) ON DELETE SET NULL
);
