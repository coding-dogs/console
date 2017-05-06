DROP TABLE IF EXISTS `supplier`;
-- ---------------------
-- 供应商表
-- ---------------------
CREATE TABLE supplier(
	id VARCHAR(50) PRIMARY KEY COMMENT '供货商ID',
	name varchar(50) NOT NULL COMMENT '供货商名称',
	supplier_no varchar(50) NOT NULL COMMENT '供货商编号',
	logo_picture_url varchar(255) COMMENT '供货商logo图片地址',
	description varchar(300) COMMENT '简述',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商表';

-- ---------------------
-- 用户-供应商关联表
-- ---------------------
DROP TABLE IF EXISTS `sys_user_supplier`;
CREATE TABLE sys_user_supplier(
	id VARCHAR(50) PRIMARY KEY COMMENT 'id',
	sys_user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
	supplier_id VARCHAR(50) NOT NULL COMMENT '供应商ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-供应商关联表';

-- ---------------------
-- 客户表
-- ---------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE customer(
	id VARCHAR(50) PRIMARY KEY COMMENT 'id',
	name VARCHAR(50) NOT NULL COMMENT '客户名称',
	customer_no VARCHAR(50) NOT NULL COMMENT '客户编号',
	account_no VARCHAR(50) NOT NULL COMMENT '登录账号',
	`password` VARCHAR(50) NOT NULL COMMENT '登录密码',
	mt_cust_type_cd VARCHAR(50) NULL COMMENT '客户类型代码CD',
	mt_customer_status_cd VARCHAR(50) COMMENT '客户状态代码CD',
	mt_city_cd VARCHAR(50) NULL COMMENT '客户所在城市代码CD',
	mt_customer_source_cd VARCHAR(50) NULL COMMENT '客户来源代码CD',
	supplier_id VARCHAR(50) NOT NULL COMMENT '供货商ID',
	customer_group_id VARCHAR(50) NULL COMMENT '客户组ID',
	tel VARCHAR(20) COMMENT '座机号',
	phone VARCHAR(20) COMMENT '手机号',
	email VARCHAR(50) COMMENT '邮箱号',
	head_pic_url VARCHAR(50) COMMENT '用户头像地址',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户表';


-- ---------------------
-- 客户收货地址
-- ---------------------
DROP TABLE IF EXISTS `customer_shipping_address`;
CREATE TABLE customer_shipping_address(
	id VARCHAR(50) PRIMARY KEY COMMENT 'ID',
	customer_id VARCHAR(50) NOT NULL COMMENT '客户ID',
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

-- ---------------------
-- 客户组
-- ---------------------
DROP TABLE IF EXISTS `customer_group`;
CREATE TABLE customer_group(
	id VARCHAR(50) PRIMARY KEY COMMENT '客户组ID',
	name VARCHAR(50) NOT NULL COMMENT '客户组名称',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户组';

-- ---------------------
-- 供应商-客户组关系表
-- ---------------------
DROP TABLE IF EXISTS `supplier_customer_group`;
CREATE TABLE supplier_customer_group (
	id VARCHAR(50) PRIMARY KEY COMMENT 'ID',
	supplier_id VARCHAR(50) NOT NULL COMMENT '供应商ID',
	customer_group_id VARCHAR(50) NOT NULL COMMENT '客户组ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商-客户组关系表';


-- ---------------------
-- 商品客户组指定价
-- ---------------------
DROP TABLE IF EXISTS `product_customer_group_price`;
CREATE TABLE product_customer_group_price(
	id VARCHAR(50) PRIMARY KEY COMMENT 'ID',
	customer_group_id varchar(50) NOT NULL COMMENT '客户组ID',
	product_id varchar(50) NOT NULL COMMENT '商品ID',
	price decimal(10, 2) NOT NULL COMMENT '客户组指定价格',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品客户组指定价';

-- ---------------------
-- 商品客户指定价
-- ---------------------
DROP TABLE IF EXISTS `product_customer_price`;
CREATE TABLE product_customer_price(
	id VARCHAR(50) PRIMARY KEY COMMENT 'ID',
	customer_id varchar(50) COMMENT '客户ID',
	product_id varchar(50) COMMENT '商品ID',
	price decimal(10, 2) COMMENT '给客户的价格',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品客户指定价';

-- ---------------------
-- 联系人信息
-- ---------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE contact(
	id VARCHAR(50) PRIMARY KEY COMMENT '联系人ID',
	name VARCHAR(50) NOT NULL COMMENT '联系人名称',
	tel VARCHAR(50) NOT NULL COMMENT '联系人号码',
	phone VARCHAR(50) NOT NULL COMMENT '联系人手机号',
	email VARCHAR(50) NULL COMMENT '联系人邮箱',
	mt_province_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在省份',
	mt_city_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在城市',
	mt_county_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在区县',
	address VARCHAR(150) NOT NULL COMMENT '联系人地址',
	customer_id VARCHAR(50) NOT NULL COMMENT '客户ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人信息';


-- ---------------------
-- 商品表
-- ---------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE product(
	id varchar(50) PRIMARY KEY COMMENT '商品ID',
	name varchar(50) NOT NULL COMMENT '商品名称',
	title varchar(100) NOT NULL COMMENT '商品标题',
	product_no varchar(50) NOT NULL COMMENT '商品编号',
	product_category_id varchar(50) NOT NULL COMMENT '商品分类ID',
	repository_id varchar(50) COMMENT '仓库ID，仓库表获取',
	repository_no varchar(20) COMMENT '仓库位号，手动填写',
	product_brand_id varchar(50) COMMENT '品牌ID',
	mt_product_label_cd varchar(50) COMMENT '标签',
	sort bigint(15) COMMENT '排序号',
	model_number varchar(100) COMMENT '型号',
	`key` varchar(300) COMMENT '关键字',
	minimum_order_number smallint(6) NOT NULL DEFAULT 1 COMMENT '起订量',
	mt_product_updown_cd varchar(50) NOT NULL COMMENT '商品上下架标识',
	order_price decimal(10, 2) NOT NULL COMMENT '订货价格',
	market_price decimal(10, 2) COMMENT '市场价格',
	buy_price decimal(10, 2) COMMENT '进货价格',
	picture_id varchar(50) COMMENT '主图图片ID',
	unit_id varchar(50) COMMENT '单位ID',
	description text COMMENT '商品描述',
	bar_code varchar(50) COMMENT '商品条形码',
	supplier_id varchar(50) NOT NULL COMMENT '所属供货商ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';


-- ---------------------
-- 商品类目单位
-- ---------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE unit(
	id varchar(50) PRIMARY KEY COMMENT '单位ID',
	unit varchar(50) COMMENT '单位',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类目单位';

-- ---------------------
-- 商品类目-单位关联表
-- ---------------------
DROP TABLE IF EXISTS `product_category_unit`;
CREATE TABLE product_category_unit(
	id varchar(50) PRIMARY KEY COMMENT 'ID',
	unit_id varchar(50) COMMENT '单位ID',
	product_category_id varchar(50) COMMENT '商品类目ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类目-单位关联表';


-- ---------------------
-- 仓库
-- ---------------------
DROP TABLE IF EXISTS `repository`;
CREATE TABLE repository(
	id varchar(50) PRIMARY KEY COMMENT '仓库ID',
	no varchar(50) NOT NULL COMMENT '仓库编号',
	name varchar(50) NOT NULL COMMENT '仓库名',
	area decimal(10, 2) COMMENT '面积(单位：平方米)',
	mt_province_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在省份',
	mt_city_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在城市',
	mt_county_cd VARCHAR(50) NOT NULL COMMENT '收货地址所在区县',
	address varchar(50) COMMENT '详细地址',
	manager_id varchar(50) COMMENT '管理人(sys_user表id)',
	supplier_id varchar(50) NOT NULL COMMENT '供货商ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库';

-- ---------------------
-- 商品分类
-- ---------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE product_category(
	id VARCHAR(50) PRIMARY KEY COMMENT '商品分类ID',
	supplier_id VARCHAR(50) NOT NULL COMMENT '供货商ID',
	name VARCHAR(50) NOT NULL COMMENT '商品分类名称',
	category_no smallint(6) NOT NULL COMMENT '分类编号',
	pid VARCHAR(50) COMMENT '商品父分类ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品分类';

-- ---------------------
-- 商品品牌
-- ---------------------
DROP TABLE IF EXISTS `product_brand`;
CREATE TABLE product_brand(
	id VARCHAR(50) PRIMARY KEY COMMENT '商品品牌ID',
	name varchar(50) NOT NULL COMMENT '品牌名称',
	logo_url varchar(255) COMMENT '商品品牌LOGO地址',
	simple_name varchar(50) COMMENT '商品品牌简称',
	supplier_id varchar(50) NOT NULL COMMENT '供货商ID',
	sort bigint(15) COMMENT '排序号',
	product_category_id varchar(50) COMMENT '商品分类ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品品牌';



-- ---------------------
-- 商品属性(自定义属性，用于详情中展示参数)：
-- ---------------------
DROP TABLE IF EXISTS `product_property`;
CREATE TABLE product_property(
	id VARCHAR(50) PRIMARY KEY COMMENT '商品属性ID',
	product_id VARCHAR(50) NOT NULL COMMENT '商品ID',
	name VARCHAR(50) NOT NULL COMMENT '属性名',
	`value` VARCHAR(200) NOT NULL COMMENT '属性值',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性';


-- ---------------------
-- 商品规格组
-- ---------------------
DROP TABLE IF EXISTS `specification_group`;
CREATE TABLE specification_group(
	id VARCHAR(50) PRIMARY KEY COMMENT '规格组ID',
	group_name VARCHAR(50) NOT NULL COMMENT '规格组名称',
	group_no VARCHAR(50) NOT NULL COMMENT '规格组编号',
	product_category_id VARCHAR(50) NOT NULL COMMENT '商品分类ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格组';

-- ---------------------
-- 商品规格
-- ---------------------
DROP TABLE IF EXISTS `specification`;
CREATE TABLE specification(
	id varchar(50) PRIMARY KEY COMMENT '规格ID',
	name varchar(50) NOT NULL COMMENT '规格名称',
	no varchar(50) NOT NULL COMMENT '规格编号',
	pecification_group_id varchar(50) NOT NULL COMMENT '规格组ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格';

-- ---------------------
-- 商品规格关联表
-- ---------------------
DROP TABLE IF EXISTS `product_specification`;
CREATE TABLE product_specification(
	id varchar(50) PRIMARY KEY COMMENT '商品规格关联ID',
	specification_id varchar(50) COMMENT '规格id',
	price decimal(10, 2) COMMENT '规格价格',
	product_id varchar(50) COMMENT '商品ID',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格关联表';
	
-- ---------------------
-- 商品图片表
-- ---------------------
DROP TABLE IF EXISTS `product_picture`;
CREATE TABLE product_picture(
	id varchar(50) PRIMARY KEY COMMENT 'ID',
	url varchar(255) NOT NULL COMMENT '图片地址',
	product_id VARCHAR(50) NOT NULL COMMENT '商品ID',
	is_main CHAR(1) NOT NULL DEFAULT 'N' COMMENT '是否为关联的主要图片',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片表';

-- ---------------------
-- 订单
-- ---------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`(
	id varchar(50) PRIMARY KEY COMMENT '订单ID',
	order_no varchar(50) NOT NULL COMMENT '订单号',
	total_price decimal(10, 2) NOT NULL COMMENT '订单总价',
	reduction_fee decimal(10, 2) COMMENT '减免费用',
	actual_price decimal(10, 2) NOT NULL COMMENT '实际付款金额',
	total_count BIGINT(15) NOT NULL COMMENT '商品总数量',
	fare decimal(10, 2) NOT NULL DEFAULT 0 COMMENT '运费，默认值0.00',
	pay_time datetime COMMENT '订单支付时间',
	ship_time datetime COMMENT '发货时间',
	mt_order_status_cd varchar(50) COMMENT '订单状态',
	shipping_address_id varchar(50) COMMENT '客户收货地址ID',
	customer_id varchar(50) COMMENT '客户ID',
	mt_payment_method_cd varchar(50) COMMENT '支付方式',
	completed_time datetime COMMENT '订单完成时间',
	close_time  datetime COMMENT '订单关闭时间',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单';


-- ---------------------
-- 订单项
-- ---------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE order_item(
	id VARCHAR(50) PRIMARY KEY COMMENT '订单项ID',
	order_id VARCHAR(50) NOT NULL COMMENT '订单ID',
	product_id VARCHAR(50) NOT NULL COMMENT '商品ID',
	specification_id VARCHAR(50) NOT NULL COMMENT '商品规格ID',
	total_count BIGINT(15) NOT NULL COMMENT '商品总数量',
	price decimal(10, 2) NOT NULL COMMENT '商品单价(最终价)',
	total_price decimal(10, 2) NOT NULL COMMENT '商品总价',
	product_title VARCHAR(100) NOT NULL COMMENT '商品标题',
	product_pic_url VARCHAR(255) NOT NULL COMMENT '商品主图图片',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单项';

-- ---------------------
-- 购物车
-- ---------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE shopping_cart(
	id varchar(50) PRIMARY KEY COMMENT '购物车ID',
	customer_id varchar(50) NOT NULL COMMENT '客户ID',
	total_type smallint(6) COMMENT '商品总类总数',
	total_count smallint(6) COMMENT '总数量',
	total_price varchar(50) COMMENT '总价',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车';

-- ---------------------
-- 购物车子项
-- ---------------------
DROP TABLE IF EXISTS `shopping_cart_item`;
CREATE TABLE shopping_cart_item(
	id varchar(50) COMMENT '购物车子项ID',
	shopping_cart_id varchar(50) COMMENT '购物车ID',
	specifications json COMMENT '商品选购明细(支持多规格)',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车子项';
