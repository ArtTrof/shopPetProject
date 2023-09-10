CREATE TYPE role_enum AS ENUM ('USER', 'ADMIN');
create extension if not exists pgcrypto;
create table customer
(
    id         bigint generated by default as identity primary key,
    first_name varchar(200) not null,
    last_name  varchar(200),
    email      varchar(200) not null,
    phone      varchar(20),
    password   varchar(200) not null,
    created_at timestamp
);

create table customer_role
(
    customer_id bigint not null,
    roles       varchar(200)
);

alter table if exists customer_role
    add constraint customer_fk_role_key
        foreign key (customer_id) references customer (id);

CREATE TABLE category
(
    id   bigint generated by default as identity primary key,
    name VARCHAR(255) not null
);

CREATE TABLE producer
(
    id   bigint generated by default as identity primary key,
    name VARCHAR(255) not null
);

create table product
(
    id                bigint generated by default as identity primary key,
    name              varchar(200) not null,
    short_description varchar(200),
    long_description  varchar(2000),
    price             float(2)     not null default 0,
    is_available      bool         not null default true,
    quantity          int4         not null default 0,
    image             bytea,
    content_type      varchar(40),
    category_id       bigint       references category (id) on delete set null,
    producer_id       bigint       references producer (id) on delete set null
);

create table cart
(
    id          bigint generated by default as identity primary key,
    customer_id bigint references customer (id)
);

create table cart_item
(
    cart_id    bigint references cart (id),
    product_id bigint references product (id),
    quantity   int4
);

alter table customer
    add column if not exists cart_id bigint references cart (id);

insert into customer (id,first_name, last_name, email, phone, password, created_at)
VALUES (0,'Admin', 'Admin', 'admin@admin.com', '+48888888888', '00000000', '2023-04-18');

insert into customer_role(customer_id, roles)
values (0, 'ADMIN');

update customer
set password = crypt(password, gen_salt('bf', 8));

insert into producer(id, name)
values (0, 'Apple');

insert into category(id, name)
values (0, 'Smartphone');

insert into product (id, name, short_description, long_description, category_id, producer_id, quantity, price)
values (0, 'Iphone 14 ProMax', 'Cool smartphone', 'Some long desc', 0, 0, 100, 2000);

insert into cart(id, customer_id)
values (0, 0);

insert into cart_item(cart_id, product_id, quantity)
VALUES (0, 0, 10);