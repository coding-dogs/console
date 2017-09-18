ALTER TABLE `supplier_customer`
ADD COLUMN `customer_score`  bigint(15) NULL DEFAULT 0 COMMENT '客户积分' AFTER `mt_customer_status_cd`;

ALTER TABLE `supplier`
ADD COLUMN `boss_name`  varchar(50) NULL COMMENT '老板姓名' AFTER `name`;

ALTER TABLE `product_category`
ADD COLUMN `picture_url`  varchar(300) NULL COMMENT '类目预览图片' AFTER `pids`;