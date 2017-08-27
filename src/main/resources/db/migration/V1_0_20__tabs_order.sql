ALTER TABLE `order`
ADD COLUMN `supplier_id` varchar(50) NOT NULL COMMENT '供应商ID' AFTER `close_time`;