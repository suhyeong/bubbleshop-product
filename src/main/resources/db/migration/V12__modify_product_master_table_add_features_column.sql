use product;

alter table product_master add column product_features varchar(255) not null comment '상품 태그, 특징';