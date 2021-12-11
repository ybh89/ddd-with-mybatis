DROP TABLE IF EXISTS maker;
CREATE TABLE maker
(
    id     IDENTITY        PRIMARY KEY,
    name   VARCHAR(255)    NOT NULL
);

DROP TABLE IF EXISTS brand;
CREATE TABLE brand
(
    id          IDENTITY        PRIMARY KEY,
    maker_id    INT,
    name        VARCHAR(255)    NOT NULL
);
ALTER TABLE brand ADD FOREIGN KEY(maker_id) REFERENCES maker(id);

DROP TABLE IF EXISTS series;
CREATE TABLE series
(
    id          IDENTITY        PRIMARY KEY,
    brand_id    INT,
    name        VARCHAR(255)    NOT NULL
);
ALTER TABLE series ADD FOREIGN KEY(brand_id) REFERENCES brand(id);