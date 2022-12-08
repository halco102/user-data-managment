create table IF NOT EXISTS user_follow(
person_id int8,
follow_id int8,

foreign key (person_id) references users(id),
foreign key (follow_id) references users(id)
);