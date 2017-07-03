alter table product_category
modify `category_no` smallint(6) null default null comment '商品分类编号';

alter table product_category
add column `sort` smallint(6) null default null comment '排序值' after `pid`;

alter table product_category
add column `pids` varchar(2000) null default null comment '所有父级分类ID' after `pid`;