ALTER TABLE `product`
MODIFY COLUMN `order_price` decimal(10,2) DEFAULT NULL COMMENT '订货价格' AFTER `mt_product_updown_cd`;