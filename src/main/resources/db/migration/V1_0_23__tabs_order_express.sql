DROP TABLE IF EXISTS `order_express`;
CREATE TABLE order_express(
	id VARCHAR(50) PRIMARY KEY COMMENT 'ID',
	order_id VARCHAR(50) NOT NULL COMMENT '订单ID',
	company_name VARCHAR(100) NOT NULL COMMENT '物流公司名称',
	express_no VARCHAR(100) NOT NULL COMMENT '物流单号',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单发货物流信息';


ALTER TABLE order_shipping_address COMMENT '订单发货地址';