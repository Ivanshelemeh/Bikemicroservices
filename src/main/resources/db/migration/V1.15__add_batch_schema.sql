ALTER TABLE cus_transaction ADD INDEX idx_status_desc(transaction_status, description_detail);
