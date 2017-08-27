-- ---------------------
-- 商品规格
-- ---------------------
DROP TABLE IF EXISTS `specification_item`;
CREATE TABLE specification_item(
	id varchar(50) PRIMARY KEY COMMENT '规格项ID',
	specification_id varchar(50) NOT NULL COMMENT '多规格ID',
	supplier_id varchar(50) NOT NULL COMMENT '供应商ID',
	name varchar(50) NOT NULL COMMENT '规格项名称',
	no varchar(50) NOT NULL COMMENT '规格项编号',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格';

ALTER TABLE specification
DROP COLUMN data;

ALTER TABLE product_specification
DROP COLUMN data;

ALTER TABLE `product_specification`
ADD COLUMN `supplier_id` VARCHAR(50) NOT NULL COMMENT '供应商ID' AFTER `product_id`;

ALTER TABLE `product_specification`
ADD COLUMN `specification_item_path` VARCHAR(300) NOT NULL COMMENT '规格项ID组合，用于组合多规格' AFTER `supplier_id`;

ALTER TABLE `product_specification`
ADD COLUMN `specification_no` VARCHAR(100) NOT NULL COMMENT '规格项编号' AFTER `specification_item_path`;

DROP TABLE specification_group;