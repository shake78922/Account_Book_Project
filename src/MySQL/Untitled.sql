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
INSERT INTO accounts (cash, account1, account2, account3, ID)
VALUES (0, 0, 0, 0, NEW.ID);

show tables;
desc accounts;
select * from account_book;