use product;

drop table if exists product_stock;

create table product_stock(
    product_code varchar(50) not null comment '상품 코드',
    product_cnt int not null comment '상품 재고 개수',
    crt_dt datetime comment '생성 일시',
    chn_dt datetime comment '수정 일시',
    primary key (product_code)
);