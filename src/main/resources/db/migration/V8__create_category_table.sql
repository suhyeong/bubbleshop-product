use product;

drop table if exists category_master;

create table category_master(
   cate_code varchar(50) not null comment '카테고리 코드',
   cate_name varchar(500) not null comment '카테고리명',
   cate_type varchar(10) not null comment '카테고리 타입',
   show_yn varchar(1) not null default 'N' comment '노출 여부',
   crt_dt datetime comment '생성 일시',
   chn_dt datetime comment '수정 일시',
   primary key (cate_code)
);