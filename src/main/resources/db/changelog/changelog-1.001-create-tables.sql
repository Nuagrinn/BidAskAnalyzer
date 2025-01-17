CREATE TABLE elvls (
                       id BIGSERIAL PRIMARY KEY,
                       isin VARCHAR(12) NOT NULL UNIQUE,
                       elvl DECIMAL(15, 2) NOT NULL
);

CREATE TABLE quotes (
                        id BIGSERIAL PRIMARY KEY,
                        isin VARCHAR(12) NOT NULL,
                        bid DECIMAL(15, 2) NOT NULL,
                        ask DECIMAL(15, 2) NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        elvl_id BIGINT UNIQUE,
                        CONSTRAINT fk_elvl FOREIGN KEY (elvl_id) REFERENCES elvls (id) ON DELETE CASCADE
);
