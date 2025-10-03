CREATE TABLE course_details
(
    id NUMBER NOT NULL,
    name VARCHAR(255) NOT NULL,
    last_updated TIMESTAMP,
    created_time TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE student
(
    id NUMBER NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE passport
(
    id NUMBER NOT NULL,
    number VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE review
(
    id NUMBER NOT NULL,
    rating VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY(id)
);