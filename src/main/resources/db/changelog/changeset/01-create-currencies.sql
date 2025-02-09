CREATE TABLE currency (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR(3) NOT NULL UNIQUE
);

INSERT INTO currency (name) VALUES
                                  ('USD'),
                                  ('EUR'),
                                  ('GBP'),
                                  ('JPY'),
                                  ('AUD'),
                                  ('CAD'),
                                  ('CHF'),
                                  ('CNY'),
                                  ('INR'),
                                  ('MXN');
