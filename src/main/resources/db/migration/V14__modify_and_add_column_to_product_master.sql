use product;

alter table product_master modify product_features text comment '상품 태그, 특징';
alter table product_master add column product_points text not null comment '상품 지급 포인트' after product_features;