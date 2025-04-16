DROP TABLE IF EXISTS wallet CASCADE;
CREATE TABLE wallet
(
    id          UUID PRIMARY KEY,
    owner_email VARCHAR NOT NULL UNIQUE
);

DROP TABLE IF EXISTS asset CASCADE;
CREATE TABLE asset
(
    id          UUID PRIMARY KEY,
    wallet_id   UUID    NOT NULL,
    coin_cap_id VARCHAR NOT NULL,
    symbol      CHAR(3) NOT NULL,
    quantity    NUMERIC NOT NULL,

    UNIQUE (wallet_id, symbol),
    CONSTRAINT fk_wallet FOREIGN KEY (wallet_id) REFERENCES wallet (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS price;
CREATE TABLE price
(
    asset_symbol CHAR(3) PRIMARY KEY,
    usd_value    NUMERIC NOT NULL
);

CREATE OR REPLACE VIEW asset_view AS
SELECT a.wallet_id,
       a.symbol,
       a.quantity,
       p.usd_value              AS price,
       a.quantity * p.usd_value AS value
FROM asset a
         LEFT JOIN price p ON a.symbol = p.asset_symbol;