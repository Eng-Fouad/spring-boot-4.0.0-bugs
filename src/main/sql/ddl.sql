CREATE TABLE users (
    id INT NOT NULL IDENTITY CONSTRAINT pk_users PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    deleted BIT NOT NULL INDEX idx_users_deleted CONSTRAINT df_users_deleted DEFAULT 0,
    creator_user_id INT NOT NULL CONSTRAINT fk_users_creator_user FOREIGN KEY REFERENCES users (id),
    updater_user_id INT CONSTRAINT fk_users_updater_user FOREIGN KEY REFERENCES users (id),
    creation_timestamp DATETIME2 NOT NULL CONSTRAINT df_users_creation_timestamp DEFAULT SYSUTCDATETIME(),
    update_timestamp DATETIME2,
    INDEX uq_users_username UNIQUE (username) WHERE deleted = 0
);
CREATE TABLE roles (
    id INT NOT NULL CONSTRAINT pk_roles PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    creation_timestamp DATETIME2 NOT NULL CONSTRAINT df_roles_creation_timestamp DEFAULT SYSUTCDATETIME(),
    INDEX uq_roles UNIQUE (name)
);
CREATE TABLE user_roles (
    user_id INT NOT NULL INDEX idx_user_roles_user CONSTRAINT fk_user_roles_user FOREIGN KEY REFERENCES users (id),
    role_id INT NOT NULL CONSTRAINT fk_user_roles_role FOREIGN KEY REFERENCES roles (id),
    creation_timestamp DATETIME2 NOT NULL CONSTRAINT df_user_roles_creation_timestamp DEFAULT SYSUTCDATETIME(),
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id)
);