use product;

drop table if exists product_master;

create table product_master(
    product_code varchar(50) not null comment '상품 코드',
    product_nm varchar(500) not null comment '상품 이름',
    main_cate_code varchar(15) not null comment '상품 메인 카테고리 코드',
    sub_cate_code varchar(15) not null comment '상품 서브 카테고리 코드',
    cost integer not null comment '상품 원가',
    disc_rate double comment '할인율',
    sale_yn varchar(1) not null comment '판매 여부',
    crt_dt datetime comment '생성 일시',
    chn_dt datetime comment '수정 일시',
    primary key (product_code)
);