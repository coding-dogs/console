ALTER TABLE `supplier`
ADD COLUMN `mobile`  varchar(20) NULL COMMENT '商家联系电话' AFTER `manager_id`;