CREATE TABLE person
(
    id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    birth_date TIMESTAMP,
    PRIMARY KEY(id)
);

INSERT INTO person (id, name, location, birth_date) VALUES (10001, 'John', 'New York', SYSDATE());
INSERT INTO person (id, name, location, birth_date) VALUES (10002, 'Li', 'Shanghai', TO_TIMESTAMP('2003-02-15 14:54:00', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO person (id, name, location, birth_date) VALUES (10003, 'Hans', 'Berlin', TO_TIMESTAMP('1998-12-11 07:00:00', 'YYYY-MM-DD HH24:MI:SS'));