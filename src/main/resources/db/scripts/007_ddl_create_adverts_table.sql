CREATE TABLE IF NOT EXISTS adverts(
    id SERIAL PRIMARY KEY,
    active boolean NOT NULL,
    created timestamp NOT NULL,
    title varchar(255) NOT NULL,
    filename varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    price int NOT NULL,
    car_id int UNIQUE,
    users_id int,
	FOREIGN KEY (car_id) REFERENCES cars (id),
	FOREIGN KEY (users_id) REFERENCES users (id)
);

COMMENT ON TABLE adverts IS 'Объявление';
COMMENT ON COLUMN adverts.id IS 'Идентификатор объявления';
COMMENT ON COLUMN adverts.active IS 'Статус объявления';
COMMENT ON COLUMN adverts.created IS 'Дата создания';
COMMENT ON COLUMN adverts.title IS 'Заголовок объявления';
COMMENT ON COLUMN adverts.filename IS 'Имя файла фотографии объявления';
COMMENT ON COLUMN adverts.description IS 'Описание объявления';
COMMENT ON COLUMN adverts.price IS 'Цена автомобиля в объявлении';
COMMENT ON COLUMN adverts.car_id IS 'Ссылка на автомобиль';
COMMENT ON COLUMN adverts.users_id IS 'Ссылка на пользователя';