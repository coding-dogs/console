ALTER TABLE `customer`
ADD COLUMN `store_name`  varchar(50) NULL COMMENT '客户店铺名称' AFTER `name`;

ALTER TABLE `order_item`
MODIFY COLUMN `specification_id`  varchar(50) NULL COMMENT '商品规格ID' AFTER `product_id`; 
