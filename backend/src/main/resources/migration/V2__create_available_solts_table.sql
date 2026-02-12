CREATE TABLE available_slots
(
    id          BIGSERIAL PRIMARY KEY,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NOT NULL,
    version     INTEGER,

    start_time  TIMESTAMP,
    end_time    TIMESTAMP,

    is_reserved BOOLEAN,
    holder_id   BIGINT
);

CREATE INDEX idx_available_slots_start_time ON available_slots (start_time) where is_reserved = false;
