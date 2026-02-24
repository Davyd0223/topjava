DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (id, name, email, password)
VALUES (100000,'User', 'user@yandex.ru', 'password'),
       (100001,'Admin', 'admin@gmail.com', 'admin'),
       (100002,'Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (id, user_id, date_time, description, calories)
VALUES (100003, 100001, '2026-02-23 09:00:00', 'burger', 900);