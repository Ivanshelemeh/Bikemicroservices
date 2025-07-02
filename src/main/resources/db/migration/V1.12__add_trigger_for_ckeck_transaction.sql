DELIMITER $$
CREATE TRIGGER prevent_insert_fail_status
    BEFORE INSERT ON cus_transaction
    FOR EACH ROW
BEGIN
    DECLARE fail_count INT DEFAULT 0;

    -- Check if there are any transactions with FAIL status
    SELECT COUNT(*) INTO fail_count
    FROM cus_transaction
    WHERE tranansaction_status = 'fail' AND period_transaction = DATE_SUB(NOW(), INTERVAL 1 HOUR);

    -- If there are FAIL transactions, prevent the insert
    IF fail_count > 50 THEN
        SET MESSAGE_TEXT = 'Cannot insert in transactions: existing FAIL status transactions found for period of hour';
END IF;
END$$

DELIMITER ;