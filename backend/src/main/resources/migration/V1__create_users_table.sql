CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL,
    version    INTEGER,

    username   VARCHAR(255),
    password   VARCHAR(255) NOT NULL
);

ALTER TABLE users
    ADD CONSTRAINT uk_users_username UNIQUE (username);
CREATE INDEX idx_users_username ON users (username);
