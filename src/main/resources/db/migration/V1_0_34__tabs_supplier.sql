ALTER TABLE `easyorder`.`product_specification`   
  ADD COLUMN `order_price` DECIMAL(10,2) NULL COMMENT '订货价格' AFTER `specification_no`,
  ADD COLUMN `market_price` DECIMAL(10,2) NULL COMMENT '市场价格' AFTER `order_price`,
  ADD COLUMN `buy_price` DECIMAL(10,2) NULL COMMENT '进货价格' AFTER `market_price`,
  ADD COLUMN `bar_code` VARCHAR(50) NULL COMMENT '商品条形码' AFTER `buy_price`;

CREATE TABLE `product_specification_customer_group_price` (
  `id` VARCHAR(50) NOT NULL COMMENT 'ID',
  `customer_group_id` VARCHAR(50) NOT NULL COMMENT '客户组ID',
  `product_specification_id` VARCHAR(50) NOT NULL COMMENT '商品某规格ID',
  `order_price` DECIMAL(10,2) DEFAULT NULL COMMENT '客户组指定价格',
  `create_by` VARCHAR(50) NOT NULL COMMENT '创建人',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `update_date` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remarks` VARCHAR(300) DEFAULT NULL COMMENT '备注',
  `del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
  `version` SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
);
CREATE TABLE `product_specification_customer_price` (
  `id` VARCHAR(50) NOT NULL COMMENT 'ID',
  `customer_id` VARCHAR(50) DEFAULT NULL COMMENT '客户ID',
  `product_specification_id` VARCHAR(50) DEFAULT NULL COMMENT '商品某规格ID',
  `order_price` DECIMAL(10,2) DEFAULT NULL COMMENT '客户指定价格',
  `create_by` VARCHAR(50) NOT NULL COMMENT '创建人',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `update_date` DATETIME DEFAULT NULL COMMENT '更新时间',
  `remarks` VARCHAR(300) DEFAULT NULL COMMENT '备注',
  `del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
  `version` SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
);