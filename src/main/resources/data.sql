-- Insert wallets
INSERT INTO wallet (id, owner_email)
VALUES ('00000000-0000-0000-0000-000000000001', 'alice@example.com'),
       ('00000000-0000-0000-0000-000000000002', 'bob@example.com'),
       ('00000000-0000-0000-0000-000000000003', 'carol@example.com');

-- Insert assets
INSERT INTO asset (id, wallet_id, coin_cap_id, symbol, quantity)
VALUES
-- Alice's assets
(gen_random_uuid(), '00000000-0000-0000-0000-000000000001', 'bitcoin', 'BTC', 0.5),
(gen_random_uuid(), '00000000-0000-0000-0000-000000000001', 'ethereum', 'ETH', 3),

-- Bob's assets
(gen_random_uuid(), '00000000-0000-0000-0000-000000000002', 'bitcoin', 'BTC', 1.2),
(gen_random_uuid(), '00000000-0000-0000-0000-000000000002', 'cardano', 'ADA', 1500),

-- Carol's assets
(gen_random_uuid(), '00000000-0000-0000-0000-000000000003', 'ethereum', 'ETH', 1.8),
(gen_random_uuid(), '00000000-0000-0000-0000-000000000003', 'solana', 'SOL', 20);

-- Insert prices
INSERT INTO price (asset_symbol, usd_value)
VALUES ('BTC', 65000.00),
       ('ETH', 3200.000008),
       ('ADA', 0.58),
       ('SOL', 140.0056);
