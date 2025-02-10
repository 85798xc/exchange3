CREATE TABLE api_request (
                                  id UUID PRIMARY KEY NOT NULL,
                                  timestamp TIMESTAMP NOT NULL,
                                  url VARCHAR(255) NOT NULL,
                                  response TEXT
);
