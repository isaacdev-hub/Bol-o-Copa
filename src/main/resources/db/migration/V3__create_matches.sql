CREATE TABLE matches (
                         id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         round_id        UUID         NOT NULL REFERENCES rounds(id),
                         team_a          VARCHAR(100) NOT NULL,
                         team_b          VARCHAR(100) NOT NULL,
                         match_datetime  TIMESTAMP    NOT NULL,
                         score_a         INTEGER,
                         score_b         INTEGER
);

CREATE INDEX idx_matches_round ON matches(round_id);