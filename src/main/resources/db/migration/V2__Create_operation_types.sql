-- Migration to add operation types

CREATE TABLE IF NOT EXISTS operation_types (
    operation_type_id BIGINT PRIMARY KEY,
    description VARCHAR(100) NOT NULL
);

INSERT INTO operation_types (operation_type_id, description) VALUES
    (1, 'PURCHASE'),
    (2, 'INSTALLMENT PURCHASE'),
    (3, 'WITHDRAWAL'),
    (4, 'PAYMENT')
ON CONFLICT (operation_type_id) DO NOTHING;
