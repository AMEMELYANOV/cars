CREATE TABLE IF NOT EXISTS engines(
    id SERIAL PRIMARY KEY,
    capacity double precision NOT NULL,
    fuel varchar(255) NOT NULL,
    hp int NOT NULL
);

COMMENT ON TABLE engines IS 'Двигатель';
COMMENT ON COLUMN engines.id IS 'Идентификатор двигателя';
COMMENT ON COLUMN engines.capacity IS 'Объем двигателя';
COMMENT ON COLUMN engines.fuel IS 'Тип топлива двигателя';
COMMENT ON COLUMN engines.hp IS 'Количество лошадиных сил двигателя';