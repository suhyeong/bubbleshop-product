use product;

truncate table product_img_mng;
alter table product_img_mng drop primary key, add primary key (product_code, img_div_code);
