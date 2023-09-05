alter table customer drop column activation_code;
update customer set password = crypt(password, gen_salt('bf', 8));