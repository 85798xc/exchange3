CREATE TABLE authorities (
                             id SERIAL PRIMARY KEY NOT NULL,
                             authority VARCHAR(255) NOT NULL
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY NOT NULL,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE user_authorities (
                                  id SERIAL PRIMARY KEY NOT NULL,
                                  id_user SERIAL NOT NULL,
                                  id_authority SERIAL NOT NULL
);

ALTER TABLE user_authorities
    ADD CONSTRAINT user_authority_unique UNIQUE (id_user, id_authority);

ALTER TABLE user_authorities
    ADD CONSTRAINT fk_user_authority_id_user FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE user_authorities
    ADD CONSTRAINT fk_user_authority_id_authority FOREIGN KEY (id_authority) REFERENCES authorities(id);

INSERT INTO authorities (authority) VALUES
                                        ('USER'),
                                        ('ADMIN');

INSERT INTO users (username, password) VALUES
                                           ('Peter', '$2a$12$RpT8PyQS7680K7ecWiWBOOrCG9yv5SXsy..0kBy6oF4Hid7Eoy/Sq'),
                                           ('Ben', '$2a$12$RpT8PyQS7680K7ecWiWBOOrCG9yv5SXsy..0kBy6oF4Hid7Eoy/Sq');

INSERT INTO user_authorities (id_user, id_authority) VALUES
                                                         (1, 1),
                                                         (2, 2);