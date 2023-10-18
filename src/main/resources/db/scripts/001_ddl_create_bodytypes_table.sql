CREATE TABLE IF NOT EXISTS bodytypes(
    id SERIAL PRIMARY KEY,
    bodytypename varchar(255) NOT NULL
);

COMMENT ON TABLE bodytypes IS 'Тип кузова';
COMMENT ON COLUMN bodytypes.id IS 'Идентификатор кузова';
COMMENT ON COLUMN bodytypes.bodytypename IS 'Наименование кузова';