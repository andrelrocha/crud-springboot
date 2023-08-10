create table patients(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    street VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    zip VARCHAR(10) NOT NULL,
    complement VARCHAR(100),
    number VARCHAR(20),
    city VARCHAR(100) NOT NULL,
    state CHAR(2) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    active TINYINT NOT NULL,

    PRIMARY KEY (id)
);