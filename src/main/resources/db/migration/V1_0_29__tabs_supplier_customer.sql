ALTER TABLE `supplier_customer`
ADD COLUMN `mt_customer_status_cd`  varchar(50) NULL COMMENT '客户状态' AFTER `customer_group_id`;