DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;

INSERT INTO users (id, name, email, password)
VALUES (100000,'User', 'user@yandex.ru', 'password'),
(100001,'Admin', 'admin@gmail.com', 'admin'),
(100002,'Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
('ADMIN', 100001);

INSERT INTO meals (id, user_id, date_time, description, calories)
VALUES (100003, 100001, '2026-02-22 09:00:00', 'salat', 900),
(100004, 100001, '2026-02-22 15:00:00', 'burger', 900),
(100005, 100001, '2026-02-22 21:00:00', 'pizza', 1500),
(100006, 100001, '2026-02-23 00:00:00', 'salat', 400),
(100007, 100001, '2026-02-23 09:00:00', 'sandwich', 500),
(100008, 100001, '2026-02-23 15:00:00', 'spaghetti', 700),
(100009, 100000, '2026-02-23 09:00:00', 'sandwich', 500),
(100010, 100000, '2026-02-23 15:00:00', 'meet', 1100);

ALTER SEQUENCE global_seq RESTART WITH 100011;
