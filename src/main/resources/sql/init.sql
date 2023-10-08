create table if not exists tb_menu
(
    dish_id     int auto_increment comment 'dish id'
    primary key,
    dish_name   varchar(50)      not null,
    description varchar(500)     not null,
    price       double unsigned  not null comment 'unit price',
    picture     varchar(300)     not null comment 'image url',
    cook_time   tinyint unsigned not null comment 'in minutes',
    status      char             null,
    create_time datetime         not null,
    update_time datetime         not null,
    constraint dish_name
    unique (dish_name)
    )
    comment 'menu list';


create table tb_user
(
    user_id     int auto_increment
        primary key,
    user_name   varchar(20) not null,
    gender      tinyint     not null comment '1 male 2 female',
    birthday    date        not null,
    phone       char(20)    not null,
    create_time datetime    not null,
    update_time datetime    not null,
    constraint user_name
        unique (user_name)
)
    comment 'user list';



create table tb_order
(
    order_id    int      not null
        primary key,
    user_id     int      not null,
    dish_id     int      not null,
    status      char(10) not null,
    create_time datetime not null,
    update_time datetime not null
)
    comment 'order list';