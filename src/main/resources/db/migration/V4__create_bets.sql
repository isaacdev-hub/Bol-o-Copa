CREATE TABLE bets (
                      id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      user_id     UUID      NOT NULL REFERENCES users(id),
                      match_id    UUID      NOT NULL REFERENCES matches(id),
                      score_a     INTEGER   NOT NULL,
                      score_b     INTEGER   NOT NULL,
                      points      INTEGER,
                      created_at  TIMESTAMP NOT NULL DEFAULT now(),
                      CONSTRAINT uk_bets_user_match UNIQUE (user_id, match_id)
);

CREATE INDEX idx_bets_user ON bets(user_id);
CREATE INDEX idx_bets_match ON bets(match_id);