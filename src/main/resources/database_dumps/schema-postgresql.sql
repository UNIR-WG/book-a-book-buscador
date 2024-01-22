CREATE TABLE books (
                       id BIGSERIAL PRIMARY KEY,
                       isbn VARCHAR(20) NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       language VARCHAR(50),
                       description TEXT,
                       category VARCHAR(50),
                       authorId BIGINT REFERENCES authors(id)
);
CREATE TABLE authors (
                         id BIGSERIAL PRIMARY KEY,
                         firstName VARCHAR(50) NOT NULL,
                         lastName VARCHAR(50) NOT NULL,
                         birthDate DATE,
                         nationality VARCHAR(50),
                         email VARCHAR(100),
                         webSite VARCHAR(100),
                         biography TEXT,
                         books_ids BIGINT[] REFERENCES books(id)
);
CREATE TABLE clients (
                         id BIGSERIAL PRIMARY KEY,
                         firstName VARCHAR(50) NOT NULL,
                         lastName VARCHAR(50) NOT NULL,
                         email VARCHAR(100),
                         address VARCHAR(255),
                         phoneNumber VARCHAR(20)
);