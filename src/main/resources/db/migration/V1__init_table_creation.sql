DROP TABLE IF EXISTS `transactionmanagerdb`.`payload_tracker`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`payload_tracker_detail`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`transaction`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`transaction_detail`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`transaction_attributes`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`transaction_rate`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`trading_partner`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`transaction_status`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`sponsor`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`payer`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`broker`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`member`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`member_phone`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`member_address`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`member_identifier`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`member_email`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`member_language`;
DROP TABLE IF EXISTS `transactionmanagerdb`.`alternate_contact`;
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`payload_tracker` (
    `payload_tracker_sk` VARCHAR(36) NOT NULL,
    `payload_id` VARCHAR(45) NOT NULL COMMENT 'A unique id assigned for the payload',
    `payload_key` VARCHAR(50) NOT NULL COMMENT 'The key for the type of payload, like account number for account payload and zeus transaction control number for transaction payload.',
    `payload_key_type_code` VARCHAR(45) NOT NULL COMMENT 'Identifies the type of payload like ACCOUNT, TRANSACTION, FILE etc',
    `payload` LONGTEXT NOT NULL COMMENT 'The payload as a string',
    `payload_direction_type_code` VARCHAR(45) NOT NULL COMMENT 'Identifies the direction of the payload INBOUND or OUTBOUND',
    `src_dest` VARCHAR(100) NOT NULL COMMENT 'Identifies the source if the payload is inbound and destination if the payload is outbound',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`payload_tracker_sk`))
    ENGINE = InnoDB
    COMMENT = 'This table contains all the payloads that are sent out or received in to the transaction storage service';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`payload_tracker_detail` (
    `payload_tracker_detail_sk` VARCHAR(36) NOT NULL,
    `payload_tracker_sk` VARCHAR(36) NOT NULL COMMENT 'The foreign key of the payload tracker table',
    `response_type_code` VARCHAR(45) NOT NULL COMMENT 'The type of response received or sent. e.g. ACK, RESULT etc',
    `response_payload_id` VARCHAR(45) NOT NULL COMMENT 'The unique id assigned to the response payload',
    `response_payload` LONGTEXT NOT NULL,
    `payload_direction_type_code` VARCHAR(45) NOT NULL COMMENT 'Identifies the direction of the payload INBOUND or OUTBOUND',
    `src_dest` VARCHAR(100) NOT NULL COMMENT 'Identifies the source if the payload is inbound and destination if the payload is outbound',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`payload_tracker_detail_sk`),
    INDEX `payload_tracker_fk_idx` (`payload_tracker_sk` ASC) VISIBLE,
    CONSTRAINT `payload_tracker_fk`
    FOREIGN KEY (`payload_tracker_sk`)
    REFERENCES `transactionmanagerdb`.`payload_tracker` (`payload_tracker_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'The payload tracker detail table, that tracks all the responses received for an outbound payload and all the responses sent for an inbound payload';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`transaction` (
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `ztcn` VARCHAR(50) NOT NULL,
    `zfcn` VARCHAR(50) NULL,
    `source` VARCHAR(50) NOT NULL COMMENT 'The source of the transaction',
    `transaction_received_date` DATETIME NOT NULL,
    `created_date` DATETIME NULL,
    `updated_date` DATETIME NULL,
    PRIMARY KEY (`transaction_sk`))
    ENGINE = InnoDB
    COMMENT = 'Top level transaction table for each of the transactions';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`transaction_detail` (
    `transaction_detail_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'Transaction that is associated with the detail',
    `transaction_type_code` VARCHAR(45) NOT NULL COMMENT 'Transaction type of the transaction',
    `coverage_type_code` VARCHAR(50) NOT NULL COMMENT 'Identifies the type of coverage received for the transaction',
    `plan_id` VARCHAR(100) NULL COMMENT 'The plan id received in the transaction',
    `csr_variant` VARCHAR(10) NULL COMMENT 'The car variant received in the transaction',
    `group_policy_id` VARCHAR(50) NOT NULL COMMENT 'The group policy id received in the transaction',
    `effective_date` DATE NOT NULL COMMENT 'The effective date of the transaction',
    `end_date` DATE NULL COMMENT 'The end date received in the transaction',
    `maintenance_effective_date` DATE NULL,
    `created_date` DATETIME NULL COMMENT 'The date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'The date when the record was updated',
    PRIMARY KEY (`transaction_detail_sk`),
    INDEX `transaction_detail_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_detail_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Contains the details of the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`transaction_rate` (
    `transaction_rate_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The rate that the transaction is associated',
    `rate_type_code` VARCHAR(50) NOT NULL COMMENT 'The type of rate (PREAMTTOT, TOTRESAMT etc.)',
    `transaction_rate` DECIMAL(10,2) NOT NULL COMMENT 'The transaction rate amount',
    `csr_variant` VARCHAR(10) NULL COMMENT 'The CSR Variance associated with the rate',
    `rate_start_date` DATE NOT NULL COMMENT 'The rate start date',
    `rate_end_date` DATE NULL COMMENT 'The rate end date',
    `created_date` DATETIME NULL COMMENT 'The date the record was created',
    `updated_date` DATETIME NULL COMMENT 'The date the record was updated',
    PRIMARY KEY (`transaction_rate_sk`),
    INDEX `transaction_rate_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_rate_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'This table contains the policy amounts that were received in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`trading_partner` (
    `trading_partner_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The trading partner associated with the transaction',
    `trading_partner_id` VARCHAR(50) NOT NULL COMMENT 'The trading partner id of the trading partner',
    `line_of_business_type_code` VARCHAR(50) NOT NULL COMMENT 'The line of business of the transaction',
    `business_unit_type_code` VARCHAR(50) NOT NULL COMMENT 'The business unit of the transaction',
    `marketplace_type_code` VARCHAR(50) NOT NULL COMMENT 'The marketplace type code of the transaction',
    `state_type_code` VARCHAR(50) NOT NULL COMMENT 'The state type code of the transaction',
    `created_date` DATETIME NULL COMMENT 'The date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'The date when the record was updated',
    PRIMARY KEY (`trading_partner_sk`),
    INDEX `transaction_tp_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_tp_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'This table contains the trading partner details';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`sponsor` (
    `sponsor_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The transaction for which the sponsor was received',
    `sponsor_name` VARCHAR(200) NOT NULL COMMENT 'The name of the sponsor received in the transaction',
    `sponsor_id` VARCHAR(100) NULL COMMENT 'The id of the sponsor received in the transaction',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the sponsor record was received',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`sponsor_sk`),
    INDEX `transaction_sponsor_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_sponsor_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'The sponsor information received in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`payer` (
    `payer_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The transaction for which the payer information was received',
    `payer_name` VARCHAR(100) NOT NULL COMMENT 'The payer name received in the transaction',
    `payer_id` VARCHAR(50) NOT NULL COMMENT 'The payer id received in the transaction',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the payer information was received in the transaction',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`payer_sk`),
    INDEX `transaction_payer_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_payer_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'The payer information received in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`broker` (
    `broker_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The transaction for which the broker was received',
    `broker_name` VARCHAR(100) NOT NULL COMMENT 'Broker name received in the transaction',
    `broker_id` VARCHAR(50) NULL COMMENT 'Broker id received in the transaction',
    `agency_name` VARCHAR(100) NULL COMMENT 'Agency name received in the transaction',
    `agency_id` VARCHAR(50) NULL COMMENT 'Agency id received in the transaction',
    `account_number_1` VARCHAR(50) NULL COMMENT 'Account number 1 received in the transaction',
    `account_number_2` VARCHAR(50) NULL COMMENT 'Account number 2 received in the transaction',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the broker record was received in the transaction',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`broker_sk`),
    INDEX `broker_transaction_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `broker_transaction_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Contains the broker information received in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`transaction_status` (
    `transaction_status_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The transaction for which the record holds the status',
    `status_seq` INT NOT NULL COMMENT 'Sequence in which the status was created for the transaction',
    `transaction_status_type_code` VARCHAR(50) NOT NULL COMMENT 'The status of the transaction',
    `processing_status_type_code` VARCHAR(50) NOT NULL COMMENT 'The processing status of the transaction',
    `created_date` DATETIME NULL COMMENT 'The date when the transaction was created',
    `updated_date` DATETIME NULL COMMENT 'The date when the transaction was updated',
    PRIMARY KEY (`transaction_status_sk`),
    INDEX `transaction_status_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_status_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Contains the status of the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`transaction_attributes` (
    `transaction_attribute_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `transaction_sk` VARCHAR(36) NOT NULL COMMENT 'The transaction that the attribute is associated',
    `transaction_attribute_type_code` VARCHAR(50) NOT NULL,
    `transaction_attribute_value` VARCHAR(100) NOT NULL,
    `created_date` DATETIME NULL,
    `updated_date` DATETIME NULL,
    PRIMARY KEY (`transaction_attribute_sk`),
    INDEX `transaction_attr_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_attr_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'This table contains the value received in the reporting categories section of the EDI transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`member` (
    `member_sk` VARCHAR(36) NOT NULL,
    `transaction_sk` VARCHAR(36) NOT NULL,
    `transaction_member_code` VARCHAR(20) NOT NULL COMMENT 'A unique member code assigned for the member during transaction processing',
    `relationship_type_code` VARCHAR(50) NOT NULL COMMENT 'The relationship type of the member with respect to the primary subscriber',
    `transaction_type_code` VARCHAR(50) NOT NULL COMMENT 'The transaction type received for the member',
    `effective_date` DATE NULL COMMENT 'The effective date received for the member',
    `end_date` DATE NULL COMMENT 'The end date received for the member',
    `reason_type_code` VARCHAR(50) NULL COMMENT 'The maintenance reason code received for the member in the transaction',
    `first_name` VARCHAR(100) NOT NULL COMMENT 'The first name of the member',
    `middle_name` VARCHAR(100) NULL COMMENT 'The middle name of the member',
    `last_name` VARCHAR(100) NOT NULL COMMENT 'The last name of the member',
    `member_rate` DECIMAL(10,2) NULL COMMENT 'The member rate received in the transaction',
    `tobacco_ind` VARCHAR(50) NOT NULL COMMENT 'Indicates if the member is a tobacco user',
    `product_catalog_rate` DECIMAL(10,2) NULL COMMENT 'The rate received from product catalog for the member',
    `date_of_birth` DATE NULL COMMENT 'The date of birth of the member',
    `gender_type_code` VARCHAR(50) NULL COMMENT 'The gender of the member',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`member_sk`),
    INDEX `transaction_member_fk_idx` (`transaction_sk` ASC) VISIBLE,
    CONSTRAINT `transaction_member_fk`
    FOREIGN KEY (`transaction_sk`)
    REFERENCES `transactionmanagerdb`.`transaction` (`transaction_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Contains the demographics of the member present in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`member_phone` (
    `member_phone_sk` VARCHAR(36) NOT NULL,
    `member_sk` VARCHAR(36) NOT NULL COMMENT 'The member for whom the phone number is created',
    `phone_type_code` VARCHAR(50) NOT NULL COMMENT 'The type of phone number',
    `phone_number` VARCHAR(20) NOT NULL COMMENT 'The phone number',
    `received_date` DATETIME NOT NULL COMMENT 'The date and time when the phone number was received',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`member_phone_sk`),
    INDEX `member_phone_sk_idx` (`member_sk` ASC) VISIBLE,
    CONSTRAINT `member_phone_sk`
    FOREIGN KEY (`member_sk`)
    REFERENCES `transactionmanagerdb`.`member` (`member_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Contains the phone number received for the member in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`member_address` (
    `member_address_sk` VARCHAR(36) NOT NULL COMMENT 'Primary ket of the table',
    `member_sk` VARCHAR(36) NOT NULL COMMENT 'The member to whom the address record belongs',
    `address_type_code` VARCHAR(50) NOT NULL COMMENT 'Address type of the address',
    `address_line_1` VARCHAR(100) NOT NULL COMMENT 'Address line 1 of the address',
    `address_line_2` VARCHAR(50) NULL COMMENT 'Address line 2 of the address',
    `city` VARCHAR(50) NULL COMMENT 'City of the address',
    `state_type_code` VARCHAR(50) NULL COMMENT 'State type code of the address',
    `zip_code` VARCHAR(50) NULL COMMENT 'Zip code of the address',
    `county_code` VARCHAR(50) NULL COMMENT 'County code associated with the address',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the address record was received',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`member_address_sk`),
    INDEX `member_address_fk_idx` (`member_sk` ASC) VISIBLE,
    CONSTRAINT `member_address_fk`
    FOREIGN KEY (`member_sk`)
    REFERENCES `transactionmanagerdb`.`member` (`member_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'The addresses received for the member in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`member_identifier` (
    `member_identifier_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `member_sk` VARCHAR(36) NOT NULL COMMENT 'The member to whom the identifier is associated',
    `identifier_type_code` VARCHAR(50) NOT NULL COMMENT 'The type of identifier',
    `identifier_value` VARCHAR(50) NOT NULL COMMENT 'The identifier value received in the transaction',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the identifier was received',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`member_identifier_sk`),
    INDEX `member_identifier_sk_idx` (`member_sk` ASC) VISIBLE,
    CONSTRAINT `member_identifier_sk`
    FOREIGN KEY (`member_sk`)
    REFERENCES `transactionmanagerdb`.`member` (`member_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Identifier received for the member in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`member_email` (
    `member_email_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the email',
    `member_sk` VARCHAR(36) NOT NULL COMMENT 'The member for whom the email was received',
    `email` VARCHAR(50) NOT NULL COMMENT 'Email received for the member',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the email was received',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`member_email_sk`),
    INDEX `member_email_fk_idx` (`member_sk` ASC) VISIBLE,
    CONSTRAINT `member_email_fk`
    FOREIGN KEY (`member_sk`)
    REFERENCES `transactionmanagerdb`.`member` (`member_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Contains the email received for the member in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`member_language` (
    `member_language_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `member_sk` VARCHAR(36) NOT NULL COMMENT 'The member for whom the language was received',
    `language_type_code` VARCHAR(50) NOT NULL COMMENT 'The type of language',
    `language_code` VARCHAR(10) NOT NULL COMMENT 'The language code received in the transaction',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the language of the member was received',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`member_language_sk`),
    INDEX `member_language_sk_idx` (`member_sk` ASC) VISIBLE,
    CONSTRAINT `member_language_sk`
    FOREIGN KEY (`member_sk`)
    REFERENCES `transactionmanagerdb`.`member` (`member_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'Language received for the member in the transaction';
CREATE TABLE IF NOT EXISTS `transactionmanagerdb`.`alternate_contact` (
    `alternate_contact_sk` VARCHAR(36) NOT NULL COMMENT 'Primary key of the table',
    `member_sk` VARCHAR(36) NOT NULL COMMENT 'The alternate contact received for the member',
    `alternate_contact_type_code` VARCHAR(50) NOT NULL COMMENT 'The type of alternate contact',
    `first_name` VARCHAR(100) NULL COMMENT 'The first name of the alternate contact',
    `middle_name` VARCHAR(50) NULL COMMENT 'The middle name of the alternate contact',
    `last_name` VARCHAR(100) NOT NULL COMMENT 'The last name of the alternate contact',
    `identifier_type_code` VARCHAR(50) NULL COMMENT 'The type of identifier received for the alternate contact',
    `identifier_value` VARCHAR(50) NULL COMMENT 'The identifier received for the alternate contact',
    `phone_type_code` VARCHAR(50) NULL COMMENT 'The type of phone number',
    `phone_number` VARCHAR(50) NULL COMMENT 'The phone number of the alternate contact',
    `email` VARCHAR(50) NULL,
    `address_line_1` VARCHAR(100) NULL COMMENT 'The address line 1 of the alternate contact address',
    `address_line_2` VARCHAR(50) NULL COMMENT 'The address line 2 of the alternate contact address',
    `city` VARCHAR(50) NULL COMMENT 'The city of the alternate contact address',
    `state_type_code` VARCHAR(50) NULL COMMENT 'The state type code of the alternate contact address',
    `zip_code` VARCHAR(50) NULL COMMENT 'The zip code of the alternate contact address',
    `received_date` DATETIME NOT NULL COMMENT 'Date when the alternate contact was received in the transaction',
    `created_date` DATETIME NULL COMMENT 'Date when the record was created',
    `updated_date` DATETIME NULL COMMENT 'Date when the record was updated',
    PRIMARY KEY (`alternate_contact_sk`),
    INDEX `alternate_member_fk_idx` (`member_sk` ASC) VISIBLE,
    CONSTRAINT `alternate_member_fk`
    FOREIGN KEY (`member_sk`)
    REFERENCES `transactionmanagerdb`.`member` (`member_sk`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    COMMENT = 'The alternate contacts received for the member in the transaction';