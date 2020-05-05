----roles------
insert into finally.role (role_id, "role")
values (1, 'ADMIN');

insert into finally.role (role_id, "role")
values (2, 'USER');
-----users------
insert into finally.profiles (id, email, last_name, login, name, password)

VALUES (1, 'biuro@netpaper.pl', 'Szewczyk', 'admin', 'Piotr',
        '$2a$10$vApVofk7Z.uhXjuLeqbPxed2dIRwgqUgsnmKa0FXmqXtjClw3pEuW'),

       (2, 'kyliexdiamond@gmail.com', 'Szewczyk', 'user', 'Paulina',
        '$2a$10$RuTzrS6fJTGXCeJILK5BledicEQ02.QKYXnWLpls.h.THP.gJcqx6');
---add roles into users----
insert into finally.profiles_role (profile_id, role_id)
VALUES (1, 1);

insert into finally.profiles_role (profile_id, role_id)
VALUES (1, 2);

insert into finally.profiles_role (profile_id, role_id)
VALUES (2, 2);
