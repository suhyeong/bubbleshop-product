use product;

alter table product_img_mng add column product_img_seq int not null comment '상품 이미지 순번' after img_div_code;
alter table product_img_mng drop primary key, add primary key (product_code, img_div_code, product_img_seq);
