CREATE TABLE IF NOT EXISTS transactions (
                              transaction_id SERIAL PRIMARY KEY,
                              account_id BIGINT NOT NULL,
                              operation_type_id BIGINT NOT NULL,
                              amount DECIMAL(10,2) NOT NULL,
                              event_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (account_id) REFERENCES accounts(account_id),
                              FOREIGN KEY (operation_type_id) REFERENCES operation_types(operation_type_id)
);