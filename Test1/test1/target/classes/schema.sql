drop table if exists Users;

create table Users(id int identity primary key, avatar nvarchar(100), name nvarchar(255) not null,
    email nvarchar(100) not null, status bit not null, status_last_update timestamp not null);

create unique index ui_email_users on Users(email);