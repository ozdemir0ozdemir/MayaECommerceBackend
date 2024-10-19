create sequence products_categories_id_seq start with 1 increment by 1;

create table products_categories
(
    product_category_id bigint       not null default nextval('products_categories_id_seq'),
    category_name       varchar(255) not null,
    primary key (product_category_id)
);

create sequence products_id_seq start with 1 increment by 1;

create table products
(
    product_id          bigint         not null default nextval('products_id_seq'),
    sku                 varchar(255)   not null,
    name                varchar(255)   not null,
    description         varchar(255)   not null,
    unit_price          decimal(13, 2) not null,
    image_url           varchar(255)   not null,
    active              boolean        not null,
    units_in_stock      integer        not null,
    product_category_id bigint         not null references products_categories (product_category_id),
    date_created        timestamp      not null,
    last_updated        timestamp,
    primary key (product_id)
);
