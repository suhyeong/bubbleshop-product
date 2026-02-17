use product;

-- column 추가
alter table product_master add column order_deadline_dt datetime comment '주문 마감 일자' after `display_end_dt`;
alter table product_master add column order_cnt bigint default 0 not null comment '주문 수' after `order_deadline_dt`;