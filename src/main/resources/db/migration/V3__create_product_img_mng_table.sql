use product;

drop table if exists product_img_mng;

create table product_img_mng(
    product_code varchar(50) not null comment '상품 코드',
    product_img_seq int not null comment '상품 이미지 순번',
    img_div_code varchar(1) not null comment '이미지 구분 코드',
    img_path varchar(500) not null comment '이미지 경로',
    crt_dt datetime comment '생성 일시',
    chn_dt datetime comment '수정 일시',
    primary key (product_code, product_img_seq)
);