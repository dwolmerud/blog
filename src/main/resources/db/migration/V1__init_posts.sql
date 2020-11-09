create table if not exists posts (
    id bigserial not null primary key,
    title varchar(255) not null,
    body_text varchar(255) not null,
    posted_at timestamp not null default now(),
    header_image_url varchar(255)
);

insert into posts (title, body_text, posted_at, header_image_url) values ('title', 'text äöå', '2020-10-27T19:58:44.474231Z', 'header_image.jpg');

