-- Customer Table
CREATE TABLE customers (
                           customer_id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL,
                           id_number VARCHAR(50) NOT NULL UNIQUE,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           phone VARCHAR(30),
                           address VARCHAR(200),
                           password VARCHAR(100) NOT NULL
);

-- Account Table
CREATE TABLE accounts (
                          account_id INT PRIMARY KEY AUTO_INCREMENT,
                          account_number VARCHAR(30) NOT NULL UNIQUE,
                          balance DOUBLE NOT NULL,
                          date_opened DATE,
                          owner_id INT,
                          branch VARCHAR(50),
                          account_type VARCHAR(20) NOT NULL,
                          employer_name VARCHAR(100),      -- Only for cheque accounts
                          employer_address VARCHAR(200),   -- Only for cheque accounts
                          FOREIGN KEY (owner_id) REFERENCES customers(customer_id)
);

-- Transaction Table
CREATE TABLE transactions (
                              transaction_id INT PRIMARY KEY AUTO_INCREMENT,
                              account_number VARCHAR(30) NOT NULL,
                              type VARCHAR(20) NOT NULL,
                              amount DOUBLE NOT NULL,
                              timestamp DATETIME NOT NULL,
                              balance_after DOUBLE NOT NULL,
                              description VARCHAR(200),
                              FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);

-- Some sample data can be added in sample_data.sql!
