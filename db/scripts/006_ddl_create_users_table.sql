CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    email varchar(255) UNIQUE,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    phonenumber varchar(255) NOT NULL,
    active boolean NOT NULL
);

COMMENT ON TABLE users IS 'Пользователь';
COMMENT ON COLUMN users.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.email IS 'Адрес электронной почты пользователя';
COMMENT ON COLUMN users.username IS 'Имя пользователя';
COMMENT ON COLUMN users.password IS 'Пароль пользователя';
COMMENT ON COLUMN users.phonenumber IS 'Телефонный номер пользователя';
COMMENT ON COLUMN users.active IS 'Статус пользователя';