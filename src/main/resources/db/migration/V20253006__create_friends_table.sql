create table friends
(
    friend_id    varchar(255)       not null,
    email        varchar(255)       not null,
    first_name   varchar(255)       not null,
    last_name    varchar(255)       not null,
    phone_number varchar(255)       not null,

    primary key (friend_id),
    unique (email)
);

