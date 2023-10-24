BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE role_teller';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE teller';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE role';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE ACCOUNT';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
create table teller(teller_id number(19) primary key, teller_name varchar2(255), teller_pass varchar2(255));
create table role(role_id number(19) primary key, role_name varchar2(255));
create table role_teller(role_id number(19), teller_id number(19), PRIMARY KEY ( role_id, teller_id ), CONSTRAINT fk_role foreign key (role_id) references role(role_id) on delete cascade, CONSTRAINT fk_teller foreign key (teller_id) references teller(teller_id) on delete cascade);
create table Account(account_id number(19) primary key, account_name varchar2(255), email varchar2(255), phone varchar2(255), address varchar2(255), balance float(53), status varchar2(255));

insert into teller(teller_id, teller_name, teller_pass) values(1, 'john','$2a$12$Evxv9k0w1/eWiUl8g6/k5OrjOPREUyBqYJJ8KqBA1qzgxjhArXtwK');
insert into teller(teller_id, teller_name, teller_pass) values(2, 'marcus','$2a$12$Evxv9k0w1/eWiUl8g6/k5OrjOPREUyBqYJJ8KqBA1qzgxjhArXtwK');
insert into role(role_id, role_name) values(1, 'admin');
insert into role(role_id, role_name) values(2, 'relationship manager');
insert into role_teller(role_id, teller_id) values(1, 1);
insert into role_teller(role_id, teller_id) values(2, 2);

insert into account(account_id , account_name , email , phone, address , balance, status) values (1 , 'marcus' , 'marcus@gmail.com' , '88889999' , 'ntuc', 10.59, 'active');
insert into account(account_id , account_name , email , phone, address , balance, status) values (2 , 'hello' , 'hello@gmail.com' , '988' , 'kitty', 10000, 'active');
insert into account(account_id , account_name , email , phone, address , balance, status) values (3 , 'zx' , 'zx@gmail.com' , '7776666' , 'aglio', 666.3, 'inactive');


commit;
