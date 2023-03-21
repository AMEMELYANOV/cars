CREATE TABLE IF NOT EXISTS cars(
    id SERIAL PRIMARY KEY,
    carmodel varchar(255) NOT NULL,
    color varchar(255) NOT NULL,
    drive varchar(255) NOT NULL,
    mileage int NOT NULL,
    year int NOT NULL,
	bodytype_id int,
	brand_id int,
	engine_id int,
	transmission_id int,
	FOREIGN KEY (bodytype_id) REFERENCES bodytypes (id),
	FOREIGN KEY (brand_id) REFERENCES brands (id),
	FOREIGN KEY (engine_id) REFERENCES engines (id),
	FOREIGN KEY (transmission_id) REFERENCES transmissions (id)
);

COMMENT ON TABLE cars IS 'Автомобиль';
COMMENT ON COLUMN cars.id IS 'Идентификатор автомобиля';
COMMENT ON COLUMN cars.carmodel IS 'Модель автомобиля';
COMMENT ON COLUMN cars.color IS 'Цвет автомобиля';
COMMENT ON COLUMN cars.drive IS 'Привод автомобиля';
COMMENT ON COLUMN cars.mileage IS 'Пробег автомобиля';
COMMENT ON COLUMN cars.year IS 'Год выпуска автомобиля';
COMMENT ON COLUMN cars.bodytype_id IS 'Ссылка на тип кузова';
COMMENT ON COLUMN cars.brand_id IS 'Ссылка на брэнд';
COMMENT ON COLUMN cars.engine_id IS 'Ссылка на двигатель';
COMMENT ON COLUMN cars.transmission_id IS 'Ссылка на трансмиссию';