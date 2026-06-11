CREATE TABLE rounds (
                        id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        name        VARCHAR(100) NOT NULL,
                        start_date  DATE         NOT NULL,
                        end_date    DATE         NOT NULL,
                        status      VARCHAR(20)  NOT NULL DEFAULT 'CREATED',
                        created_at  TIMESTAMP    NOT NULL DEFAULT now()
);