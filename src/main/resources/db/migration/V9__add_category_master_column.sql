use product;

alter table `category_master` add column `cate_eng_name` varchar(255) not null comment '카테고리 영문명' after `cate_name`;