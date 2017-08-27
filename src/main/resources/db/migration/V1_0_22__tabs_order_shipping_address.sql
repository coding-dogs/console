DROP TABLE IF EXISTS `order_shipping_address`;
CREATE TABLE order_shipping_address(
	id VARCHAR(50) PRIMARY KEY COMMENT 'ID',
	order_id VARCHAR(50) NOT NULL COMMENT '订单ID',
	shipping_name VARCHAR(50) NOT NULL COMMENT '收货人名称',
	shipping_phone VARCHAR(20) NOT NULL COMMENT '收货人手机号',
	shipping_tel VARCHAR(20) NULL COMMENT '收货人电话',
	mt_province_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在省份',
	mt_city_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在城市',
	mt_county_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在区县',
	is_default CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否为默认收货地址',
	address VARCHAR(50) NOT NULL COMMENT '收货详细地址',
	zip_code VARCHAR(20) NULL COMMENT '邮政编码',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户收货地址';