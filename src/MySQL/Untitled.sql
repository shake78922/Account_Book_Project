show databases;

create database account;

use account;

set SQL_SAFE_UPDATES=1;
create table account_book(
	ID varchar(8) not null primary key,
    PW varchar(12) not null,
    Name varchar(20) not null
);
create table accounts(
	cash int not null,
    account1 int not null,
    account2 int,
	account3 int,
    ID varchar(8),
	CONSTRAINT fk FOREIGN KEY(ID) REFERENCES account_book(ID) ON DELETE CASCADE
    );
CREATE TRIGGER add_account_after_insert
AFTER INSERT ON account_book
FOR EACH ROW
INSERT INTO accounts (cash, account1,ID)
VALUES (0, 0, NEW.ID); 

CREATE TABLE deposits (
    deposit_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(8) NOT NULL,
    deposit_date DATE NOT NULL,
    deposit_amount INT NOT NULL,
    deposit_type VARCHAR(50) NOT NULL,
    payment_type VARCHAR(20), -- Add a new column for payment type
    description VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES account_book(ID) ON DELETE CASCADE
);

CREATE TABLE expenses (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(8) NOT NULL,
    expense_date DATE NOT NULL,
    expense_amount INT NOT NULL,
    expense_type VARCHAR(50) NOT NULL,
    payment_type VARCHAR(20),
    description VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES account_book(ID) ON DELETE CASCADE
);


show tables;
desc accounts;
select * from account_book;
select * from accounts;
select * from deposits;
select * from expenses;