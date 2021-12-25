DROP TABLE IF EXISTS maker;
CREATE TABLE maker
(
    id          IDENTITY        PRIMARY KEY,
    name        VARCHAR(255)    NOT NULL,
    site_url    VARCHAR(255)
);

DROP TABLE IF EXISTS brand;
CREATE TABLE brand
(
    id          IDENTITY        PRIMARY KEY,
    maker_id    INT,
    name        VARCHAR(255)    NOT NULL,
    site_url    VARCHAR(255)
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

DROP TABLE IF EXISTS maker_synonym;
CREATE TABLE maker_synonym
(
    maker_id    INT             NOT NULL,
    name        VARCHAR(255)    NOT NULL
);
ALTER TABLE maker_synonym ADD FOREIGN KEY(maker_id) REFERENCES maker(id);
ALTER TABLE maker_synonym ADD PRIMARY KEY(maker_id, name);

DROP TABLE IF EXISTS brand_synonym;
CREATE TABLE brand_synonym
(
    brand_id    INT             NOT NULL,
    name        VARCHAR(255)    NOT NULL
);
ALTER TABLE brand_synonym ADD FOREIGN KEY(brand_id) REFERENCES brand(id);
ALTER TABLE brand_synonym ADD PRIMARY KEY(brand_id, name);