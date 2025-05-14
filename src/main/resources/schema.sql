DROP TABLE IF EXISTS wallet CASCADE;
CREATE TABLE wallet
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    owner_email VARCHAR NOT NULL UNIQUE
);

DROP TABLE IF EXISTS asset CASCADE;
CREATE TABLE asset
(
    id          UUID PRIMARY KEY,
    wallet_id   UUID    NOT NULL,
    coin_cap_id VARCHAR NOT NULL,
    symbol      VARCHAR NOT NULL,
    quantity    NUMERIC NOT NULL,

    UNIQUE (wallet_id, symbol),
    CONSTRAINT fk_wallet FOREIGN KEY (wallet_id) REFERENCES wallet (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS price;
CREATE TABLE price
(
    asset_symbol VARCHAR PRIMARY KEY,
    usd_value    NUMERIC NOT NULL
);
