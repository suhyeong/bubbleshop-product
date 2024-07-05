use product;

drop table if exists product_stock;

create table product_option_master(
    product_code varchar(50) not null comment '상품 코드',
    product_opt_seq int not null comment '옵션 순번',
    product_stock_cnt int not null default 0 comment '상품 재고 개수',
    product_opt_name varchar(200) not null comment '옵션명',
    product_opt_default_yn varchar(1) not null comment '디폴트 옵션 여부',
    crt_dt datetime comment '생성 일시',
    chn_dt datetime comment '수정 일시',
    primary key (product_code, product_opt_seq)
)