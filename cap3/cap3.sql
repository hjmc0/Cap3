/* creating three tellers with different roles */
insert into role(role_name) values('superadmin');
insert into role(role_name) values('admin');
insert into role(role_name) values('teller');
insert into teller(teller_name, teller_pass) values('john', '$2a$12$IFwNku56cjhjFdDe9/6XSe/K9zNJAfcz.VhkU7u0bjy9KHX9cpPlm');
insert into role_teller values(1,1);
insert into teller(teller_name, teller_pass) values('kate', '$2a$12$IFwNku56cjhjFdDe9/6XSe/K9zNJAfcz.VhkU7u0bjy9KHX9cpPlm');
insert into role_teller values(2,2);
insert into teller(teller_name, teller_pass) values('bill', '$2a$12$IFwNku56cjhjFdDe9/6XSe/K9zNJAfcz.VhkU7u0bjy9KHX9cpPlm');
insert into role_teller values(3,3);

/* creating eleven accounts */
insert into account(balance, account_name, address, email, phone, status) values(100.20, 'Sam Smith', 'Yishun 404', 'sam@gmail.com', '90909991','active');
insert into account(balance, account_name, address, email, phone, status) values(1000.20, 'Peter Parker', 'New York City Street 111', 'PeePee@gmail.com', '80901191','active');
insert into account(balance, account_name, address, email, phone, status) values(200.15, 'Superman Batman', 'Gotham Bat Cave', 'iAmBatMan@gmail.com', '999111','active');
insert into account(balance, account_name, address, email, phone, status) values(3560.27, 'Sridhar Turner', 'Somewhere on Planet Earth', 'SriSri@gmail.com', '80809011','active');
insert into account(balance, account_name, address, email, phone, status) values(1010.20, 'Eddie Hall', 'Muscle Beach', 'StrongestMan@gmail.com', '80908011','active');
insert into account(balance, account_name, address, email, phone, status) values(10220.27, 'Marcus', 'Redhill Street 7', 'marcus90@gmail.com', '88911192','active');
insert into account(balance, account_name, address, email, phone, status) values(1100.20, 'ShuRui', 'Somewhere at Topayo', 'shurui99@gmail.com', '84901191','active');
insert into account(balance, account_name, address, email, phone, status) values(7100.20, 'Jin An', 'Alpha Street 11', 'jinanan@gmail.com', '80913012','active');
insert into account(balance, account_name, address, email, phone, status) values(90100.20, 'Chandra', 'Planet Chandra', 'chanchan@gmail.com', '84009011','active');
insert into account(balance, account_name, address, email, phone, status) values(31200.20, 'Meat Head', 'Meat Planet', 'MeatMan@gmail.com', '80114011','active');            
insert into account(balance, account_name, address, email, phone, status) values(31200.20, 'Susan', 'NTUC Learn Hub', 'Susu@gmail.com', '90414011','active');

/* creating three transactions for account_id=1 */
insert into transaction(amount, account_id, trans_date, trans_type) values(9606.20, 1, '2022-10-27 20:43:00', 'deposit');
insert into transaction(amount, account_id, trans_date, trans_type) values(416.00, 1, '2023-10-24 16:52:00', 'withdraw');
insert into transaction(amount, account_id, trans_date, trans_type) values(9090.00, 1, '2023-10-26 20:43:47', 'withdraw');

/* creating three transactions for account_id=2 */
insert into transaction(amount, account_id, trans_date, trans_type) values(101.20, 2, '2023-05-17 19:00:49', 'deposit');
insert into transaction(amount, account_id, trans_date, trans_type) values(10.00, 2, '2023-06-20 08:15:15', 'withdraw');
insert into transaction(amount, account_id, trans_date, trans_type) values(909.00, 2, '2023-10-10 10:10:10', 'deposit');

/* creating three transactions for account_id=3 */
insert into transaction(amount, account_id, trans_date, trans_type) values(10.00, 3, '2023-01-09 05:34:36', 'deposit');
insert into transaction(amount, account_id, trans_date, trans_type) values(150.24, 3, '2023-04-17 10:59:59', 'deposit');
insert into transaction(amount, account_id, trans_date, trans_type) values(39.91, 3, '2023-09-04 02:44:48', 'deposit');