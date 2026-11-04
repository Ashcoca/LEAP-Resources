CREATE TABLE IF NOT EXISTS authors (
    id   INT PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    id               INT PRIMARY KEY,
    title            VARCHAR(300) NOT NULL,
    author_id        INT NOT NULL REFERENCES authors(id),
    publication_year INT
);
