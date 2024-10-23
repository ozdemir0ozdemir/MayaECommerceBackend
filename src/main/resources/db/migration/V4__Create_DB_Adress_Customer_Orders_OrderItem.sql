create sequence addresses_id_seq start with 1 increment by 1;
create sequence customers_id_seq start with 1 increment by 1;
create sequence orders_id_seq start with 1 increment by 1;
create sequence order_items_id_seq start with 1 increment by 1;

create table addresses
(
    address_id bigint       not null default nextval('addresses_id_seq'),
    city       varchar(255)          default null,
    country    varchar(255) not null,
    state      varchar(255) not null,
    street     varchar(255)          default null,
    zip_code   varchar(255)          default null,
    primary key (address_id)
);

create table customers
(
    customer_id bigint       not null default nextval('customers_id_seq'),
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    email       varchar(255) not null,
    primary key (customer_id)
);

create table orders
(
    order_id              bigint                      not null default nextval('orders_id_seq'),
    order_tracking_number varchar(255)                not null,
    total_price           decimal(19, 2)              not null,
    billing_address_id    bigint                      not null,
    shipping_address_id   bigint                      not null,
    customer_id           bigint                      not null,
    status                varchar(128)                not null,
    date_created          timestamp without time zone not null,
    last_updated          timestamp without time zone not null,
    primary key (order_id),
    constraint fk_customer_id foreign key (customer_id) references customers (customer_id),
    constraint fk_billing_address_id foreign key (billing_address_id) references addresses (address_id),
    constraint fk_shipping_address_id foreign key (shipping_address_id) references addresses (address_id),
    constraint uc_billing_address_id unique (billing_address_id),
    constraint uc_shipping_address_id unique (shipping_address_id)
);

create table order_items
(
    order_item_id bigint         not null default nextval('order_items_id_seq'),
    image_url     varchar(255)            default null,
    quantity      int            not null,
    unit_price    decimal(19, 2) not null,
    order_id      bigint         not null,
    product_id    bigint         not null,
    primary key (order_item_id),
    constraint fk_order_id foreign key (order_id) references orders (order_id),
    constraint fk_product_id foreign key (product_id) references products (product_id)
);