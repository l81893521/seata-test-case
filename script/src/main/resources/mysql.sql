create table account_tbl
(
    id          bigint auto_increment comment 'id'
        primary key,
    user_id     varchar(50)                        null comment 'user_id',
    money       bigint                             not null comment 'money',
    sex         tinyint  default 1                 not null comment 'sex',
    information text                               null comment 'information',
    create_time datetime default CURRENT_TIMESTAMP not null comment 'create_time',
    constraint uni_user_id
        unique (user_id)
);

create table order_tbl
(
    id bigint           not null auto_increment primary key comment 'id',
    user_id varchar(50)  not null comment 'user_id'
);

create table account_tbl_multi_pk
(
    id          bigint auto_increment comment 'id',
    user_id     varchar(50)                        not null comment 'user_id',
    money       bigint                             not null comment 'money',
    sex         tinyint  default 1                 not null comment 'sex',
    information text                               null comment 'information',
    create_time datetime default CURRENT_TIMESTAMP not null comment 'create_time',
    constraint primary key (id, user_id)
);