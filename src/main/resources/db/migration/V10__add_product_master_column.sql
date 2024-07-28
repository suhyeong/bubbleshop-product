use product;

alter table `product_master` add column `product_eng_nm` varchar(255) not null comment '상품 영문명' after `product_nm`;