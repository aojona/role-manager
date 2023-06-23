--liquibase formatted sql

--changeset aojona:1
CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username    VARCHAR(64) UNIQUE NOT NULL,
    password    VARCHAR(64) NOT NULL
);
--rollback DROP TABLE users

--changeset aojona:2
CREATE TABLE IF NOT EXISTS roles
(
    id          SMALLINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name        VARCHAR(64) UNIQUE NOT NULL
);
--rollback DROP TABLE roles

--changeset aojona:3
CREATE TABLE IF NOT EXISTS users_role
(
    users_id    BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id     SMALLINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    UNIQUE (users_id, role_id)
);
--rollback DROP TABLE users_role