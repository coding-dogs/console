ALTER TABLE `supplier`
ADD COLUMN `store_address`  varchar(100) NULL COMMENT '店铺地址' AFTER `manager_id`;