DROP TABLE IF EXISTS product_category_brand;
CREATE TABLE product_category_brand(
	`id` VARCHAR(50) NOT NULL PRIMARY KEY,
	`product_category_id` VARCHAR(50) NOT NULL COMMENT '商品类目ID',
	`product_brand_id` VARCHAR(50) NOT NULL COMMENT '商品品牌ID',
	`supplier_id` VARCHAR(50) NOT NULL COMMENT '供应商ID',
	`create_by` VARCHAR(50) NOT NULL COMMENT '创建人',
  	`create_date` DATETIME NOT NULL COMMENT '创建时间',
  	`update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  	`update_date` DATETIME DEFAULT NULL COMMENT '更新时间',
  	`remarks` VARCHAR(300) DEFAULT NULL COMMENT '备注',
  	`del_flag` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
  	`version` SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '版本号'
);

ALTER TABLE product_brand
DROP COLUMN `product_category_id`;

ALTER TABLE product
DROP COLUMN `picture_id`;

ALTER TABLE product
ADD COLUMN `cover_url` VARCHAR(300) NULL DEFAULT NULL COMMENT '封面图片地址';