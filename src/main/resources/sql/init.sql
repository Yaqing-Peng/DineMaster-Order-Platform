create database if not exists db01;

create table db01.tb_menu
(
    id     int auto_increment comment 'dish id'  primary key,
    name   varchar(50)      not null,
    description varchar(500)     not null,
    price       double unsigned  not null comment 'unit price',
    picture     varchar(300)     not null comment 'image url',
    cook_time   tinyint unsigned not null comment 'in minutes',
    status      int              not null comment '1 on 2 off',
    create_time datetime         not null,
    update_time datetime         not null,
    constraint dish_name         unique (name)
    )
    comment 'menu list';


create table db01.tb_user
(
    id     int auto_increment  primary key,
    name   varchar(20) not null,
    gender      tinyint     not null comment '1 male 2 female',
    birthday    date        not null,
    phone       char(20)    not null,
    status      int         not null comment '1 on 2 off',
    create_time datetime    not null,
    update_time datetime    not null,
    constraint user_name    unique (name)
)
    comment 'user list';



create table db01.tb_order
(
    id          int      not null  primary key,
    user_id     int      not null,
    dish_id     int      not null,
    status      int      not null comment'1 on 2 off',
    create_time datetime not null,
    update_time datetime not null
)
    comment 'order list';