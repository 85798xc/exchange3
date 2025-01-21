CREATE TABLE api_request_logs (
                                  id UUID PRIMARY KEY NOT NULL,
                                  timestamp TIMESTAMP NOT NULL,
                                  url VARCHAR(255) NOT NULL,
                                  response JSONB
);
