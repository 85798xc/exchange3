CREATE TABLE currencies (
                                      id BIGSERIAL PRIMARY KEY,
                                      currency VARCHAR(3) NOT NULL UNIQUE
);
