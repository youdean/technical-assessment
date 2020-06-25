drop table if exists Roles cascade;

create table Roles (
    id              serial          primary key,
    name            varchar(50)     not null
);