CREATE TABLE IF NOT EXISTS transmissions(
    id SERIAL PRIMARY KEY,
    transmissionname varchar(255)
);

COMMENT ON TABLE transmissions IS 'Трансмиссия';
COMMENT ON COLUMN transmissions.id IS 'Идентификатор трансмиссии';
COMMENT ON COLUMN transmissions.transmissionname IS 'Наименование трансмиссии';