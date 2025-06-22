use product;

alter table product_master drop column product_features;
alter table product_master drop column product_points;

create table product_features_master (
   product_code varchar(50) not null comment '상품 코드',
   feature_code varchar(1) not null comment '상품 태그 코드',
   crt_dt datetime comment '생성 일시',
   chn_dt datetime comment '수정 일시',
   primary key (product_code, feature_code)
);

create table product_points_master (
    product_code varchar(50) not null comment '상품 코드',
    point_code varchar(1) not null comment '상품 포인트 코드',
    save_points tinyint not null comment '적립 포인트 금액',
    crt_dt datetime comment '생성 일시',
    chn_dt datetime comment '수정 일시',
    primary key (product_code, point_code)
);