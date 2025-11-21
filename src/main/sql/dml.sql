INSERT INTO roles (id, name)
VALUES (1, 'VIEWER'), (2, 'WRITER'), (3, 'ADMIN');

ALTER TABLE users ALTER COLUMN creator_user_id INT NULL;

INSERT INTO users (username, hashed_password)
VALUES ('admin', 'wrofnfjnsflksdnmflsdknfsd');

UPDATE users
SET creator_user_id = 1
WHERE id = 1;

ALTER TABLE users ALTER COLUMN creator_user_id INT NOT NULL;


INSERT INTO users (username, hashed_password, creator_user_id)
VALUES
('user1', 'dgfdgfdgdsdfsefsgsfdgfdgf', 1),
('user2', 'kmdskffsdjonosdfnfjkwefef', 1);

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1), (2, 2), (3, 3);