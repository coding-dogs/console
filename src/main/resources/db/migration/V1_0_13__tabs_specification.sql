-- ---------------------
-- 商品规格
-- ---------------------
DROP TABLE IF EXISTS `specification`;
CREATE TABLE specification(
	id varchar(50) PRIMARY KEY COMMENT '规格ID',
	name varchar(50) NOT NULL COMMENT '规格名称',
	no varchar(50) NOT NULL COMMENT '规格编号',
	data json NOT NULL COMMENT '规格数据',
	create_by VARCHAR(50) NOT NULL COMMENT '创建人',
	create_date DATETIME NOT NULL COMMENT '创建时间',
	update_by VARCHAR(50) NULL COMMENT '更新人',
	update_date DATETIME NULL COMMENT '更新时间',
	remarks VARCHAR(300) NULL COMMENT '备注',
	del_flag CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)',
	version SMALLINT(6) NOT NULL DEFAULT 0 COMMENT '版本号'
)  ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格';

INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('197a6bb2f1424692af7d80ae53a49a36', '4406f3dab52648baaa7d61ee8c9ce8fa', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,', '多规格', 150, '/productManager/specification', '', '', '1', 'product:specification:list', '1', '2017-07-28 19:53:19', '1', '2017-07-28 19:53:19', '', '0'),
	('04e6529713284adf84ddd5a084b9e11a', '197a6bb2f1424692af7d80ae53a49a36', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,197a6bb2f1424692af7d80ae53a49a36,', '查看', 90, '', '', '', '0', 'product:specification:view', '1', '2017-07-28 19:54:38', '1', '2017-07-28 19:54:48', '', '0'),
	('5b621befb9be47e897bcc5e71228f0dd', '197a6bb2f1424692af7d80ae53a49a36', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,197a6bb2f1424692af7d80ae53a49a36,', '删除', 120, '', '', '', '0', 'product:specification:del', '1', '2017-07-28 19:55:04', '1', '2017-07-28 19:55:16', '', '0'),
	('b022884f957c41fab16dbfdd4fbaa30b', '197a6bb2f1424692af7d80ae53a49a36', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,197a6bb2f1424692af7d80ae53a49a36,', '编辑', 60, '', '', '', '0', 'product:specification:edit', '1', '2017-07-28 19:54:00', '1', '2017-07-28 19:54:00', '', '0'),
	('ddc41069bd9e4413990a167e451847fb', '197a6bb2f1424692af7d80ae53a49a36', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,197a6bb2f1424692af7d80ae53a49a36,', '新增', 30, '', '', '', '0', 'product:specification:add', '1', '2017-07-28 19:53:43', '1', '2017-07-28 19:53:43', '', '0');


