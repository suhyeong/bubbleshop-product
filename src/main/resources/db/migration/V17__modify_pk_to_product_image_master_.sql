use product;

-- column 추가
alter table product_image_master add column product_image_id bigint not null comment '상품 이미지 아이디' first;
-- pk 제거 & 새 pk 추가
alter table product_image_master drop primary key, add primary key (product_image_id);

-- pk auto increment 추가
alter table product_image_master modify product_image_id bigint not null auto_increment comment '상품 이미지 아이디';

-- column 제거
alter table product_image_master drop column product_img_seq;