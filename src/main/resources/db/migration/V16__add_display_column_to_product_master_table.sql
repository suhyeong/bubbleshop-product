use product;

-- product_master 테이블 컬럼 추가
alter table product_master add column display_start_dt datetime not null comment '전시 시작일' after sale_yn;
alter table product_master add column display_end_dt datetime not null comment '전시 종료일' after display_start_dt;

-- product_points_master, product_img_mng 테이블명 변경
rename table product_points_master to product_point_master,
             product_img_mng to product_image_master;