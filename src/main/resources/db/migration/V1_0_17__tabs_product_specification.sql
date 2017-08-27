DROP TABLE IF EXISTS `product_specification`;
CREATE TABLE product_specification(
	id varchar(50) PRIMARY KEY,
	product_id varchar(50) NOT NULL COMMENT '商品ID',
	data JSON NOT NULL COMMENT '规格值',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格信息表';

ALTER TABLE `specification`
MODIFY COLUMN `data` JSON NULL DEFAULT NULL COMMENT '规格数据';