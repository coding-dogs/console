ALTER TABLE unit
ADD COLUMN `supplier_id` varchar(50) NOT NULL COMMENT '供货商ID' after `unit`;

INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('da93d95b57154265aa550d464cc14908', 'ae5296e8728e4d5b81df1a4caae29172', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,ae5296e8728e4d5b81df1a4caae29172,', '删除', 120, '', '', '', '0', 'product:unit:del', '1', '2017-07-17 12:32:05', '1', '2017-07-17 12:32:05', '', '0'),
	('0d0957fdfda84db3a26e03820c365b3c', 'ae5296e8728e4d5b81df1a4caae29172', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,ae5296e8728e4d5b81df1a4caae29172,', '编辑', 90, '', '', '', '0', 'product:unit:edit', '1', '2017-07-17 12:31:40', '1', '2017-07-17 12:31:40', '', '0'),
	('6ca064ec8b27475684c066b0329ab9bf', 'ae5296e8728e4d5b81df1a4caae29172', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,ae5296e8728e4d5b81df1a4caae29172,', '查看', 60, '', '', '', '0', 'product:unit:view', '1', '2017-07-17 12:31:13', '1', '2017-07-17 12:31:13', '', '0'),
	('039fbc51af724f43b98b85650af12f3e', 'ae5296e8728e4d5b81df1a4caae29172', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,ae5296e8728e4d5b81df1a4caae29172,', '新增', 30, '', '', '', '0', 'product:unit:add', '1', '2017-07-17 12:30:55', '1', '2017-07-17 12:30:55', '', '0'),
	('ae5296e8728e4d5b81df1a4caae29172', '4406f3dab52648baaa7d61ee8c9ce8fa', '0,1,4406f3dab52648baaa7d61ee8c9ce8fa,', '单位管理', 120, '/productManager/unit', '', '', '1', 'product:unit:list', '1', '2017-07-17 12:30:28', '1', '2017-07-17 12:32:32', '', '0');


