truncate products cascade;
truncate products_categories cascade;
alter sequence products_id_seq restart with 1;
alter sequence products_categories_id_seq restart with 1;

INSERT INTO products_categories(category_name)
VALUES ('BOOKS');

INSERT INTO products (sku, name, description, image_url, active, units_in_stock,
                      unit_price, product_category_id, date_created)
VALUES ('BOOK-TECH-1000', 'JavaScript - The Fun Parts', 'Learn JavaScript',
        'assets/images/products/placeholder.png'
           , true, 100, 19.99, 1, NOW());

INSERT INTO products (sku, name, description, image_url, active, units_in_stock,
                      unit_price, product_category_id, date_created)
VALUES ('BOOK-TECH-1001', 'Spring Framework Tutorial', 'Learn Spring',
        'assets/images/products/placeholder.png'
           , true, 100, 29.99, 1, NOW());

INSERT INTO products (sku, name, description, image_url, active, units_in_stock,
                      unit_price, product_category_id, date_created)
VALUES ('BOOK-TECH-1002', 'Kubernetes - Deploying Containers', 'Learn Kubernetes',
        'assets/images/products/placeholder.png'
           , true, 100, 24.99, 1, NOW());

INSERT INTO products (sku, name, description, image_url, active, units_in_stock,
                      unit_price, product_category_id, date_created)
VALUES ('BOOK-TECH-1003', 'Internet of Things (IoT) - Getting Started', 'Learn IoT',
        'assets/images/products/placeholder.png'
           , true, 100, 29.99, 1, NOW());

INSERT INTO products (sku, name, description, image_url, active, units_in_stock,
                      unit_price, product_category_id, date_created)
VALUES ('BOOK-TECH-1004', 'The Go Programming Language: A to Z', 'Learn Go',
        'assets/images/products/placeholder.png'
           , true, 100, 24.99, 1, NOW());
