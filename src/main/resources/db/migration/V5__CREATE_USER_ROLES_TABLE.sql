create table IF NOT EXISTS user_roles(
users_id int8,
roles_id int8,

foreign key (users_id) references users(id),
foreign key (roles_id) references roles(id)
);