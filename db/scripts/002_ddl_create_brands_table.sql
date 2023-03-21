CREATE TABLE IF NOT EXISTS brands(
    id SERIAL PRIMARY KEY,
	brandname varchar(255) NOT NULL
);

COMMENT ON TABLE brands IS 'Брэнд';
COMMENT ON COLUMN brands.id IS 'Идентификатор брэнда';
COMMENT ON COLUMN brands.brandname IS 'Наименование брэнда';