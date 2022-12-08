create table IF NOT EXISTS roles(
id int8 DEFAULT nextval('roles_sequence') primary key,
name varchar(30) unique
);