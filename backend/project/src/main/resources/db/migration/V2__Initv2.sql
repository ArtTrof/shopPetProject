alter table customer
    add column role_id bigint;

create table roles
(
    id        bigint generated by default as identity primary key ,
    role_name varchar(200) not null
);
insert into roles(role_name)
values ('USER'),('ADMIN');
alter table customer
    add constraint role_fk FOREIGN KEY (role_id) references roles (id);

update customer
set role_id=2
where customer.id = 1;

insert into customer(first_name, last_name, email, phone, activation_code, password, created_at, role_id)
VALUES ('test','test','test2@gmail.com','+2232323','123123132','1231','2022-05-05',2);
insert into customer(first_name, last_name, email, phone, activation_code, password, created_at, role_id)
VALUES ('test','test','test2@gmail.com','+2232323','123123132','1231','2022-05-05',1);
