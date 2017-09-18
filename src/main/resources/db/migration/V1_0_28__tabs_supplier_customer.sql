ALTER TABLE `supplier_customer`
MODIFY COLUMN `customer_group_id` varchar(50) NULL COMMENT '客户组ID' AFTER `customer_id`;

