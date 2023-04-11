create database product;
create user product identified by 'welcome';
grant all privileges on product.* to product;
grant super on *.* to product;