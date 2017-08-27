INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('f0bd0504e3d74daf9cec63acd8c47df2', '1', '0,1,', '订单管理', 2120, '', '', 'fa-map', '1', '', '1', '2017-08-21 14:33:00', '1', '2017-08-21 14:33:00', '', '0'),
	('412d8b0bae6a45b4a0b24fa7e3616093', 'f0bd0504e3d74daf9cec63acd8c47df2', '0,1,f0bd0504e3d74daf9cec63acd8c47df2,', '订货单', 30, '/orderManager/order', '', '', '1', 'order:order:list', '1', '2017-08-21 14:34:25', '1', '2017-08-21 14:34:25', '', '0'),
	('ef5c484dae954ae2a977ab1171c5d608', '412d8b0bae6a45b4a0b24fa7e3616093', '0,1,f0bd0504e3d74daf9cec63acd8c47df2,412d8b0bae6a45b4a0b24fa7e3616093,', '新增', 30, '', '', '', '0', 'order:order:add', '1', '2017-08-21 14:35:17', '1', '2017-08-21 14:35:17', '', '0'),
	('057aca9e8fa64f43a67919e713205c1e', '412d8b0bae6a45b4a0b24fa7e3616093', '0,1,f0bd0504e3d74daf9cec63acd8c47df2,412d8b0bae6a45b4a0b24fa7e3616093,', '编辑', 60, '', '', '', '0', 'order:order:edit', '1', '2017-08-21 14:35:47', '1', '2017-08-21 14:35:47', '', '0'),
	('52f72d74e06c481aa78d08c59bfd171a', '412d8b0bae6a45b4a0b24fa7e3616093', '0,1,f0bd0504e3d74daf9cec63acd8c47df2,412d8b0bae6a45b4a0b24fa7e3616093,', '查看', 90, '', '', '', '0', 'order:order:view', '1', '2017-08-21 14:36:13', '1', '2017-08-21 14:36:13', '', '0'),
	('98ad976683444c90becdfb5f5d42330b', '412d8b0bae6a45b4a0b24fa7e3616093', '0,1,f0bd0504e3d74daf9cec63acd8c47df2,412d8b0bae6a45b4a0b24fa7e3616093,', '删除', 120, '', '', '', '0', 'order:order:del', '1', '2017-08-21 14:36:34', '1', '2017-08-21 14:36:34', '', '0');


INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
VALUES
	('6a7c017f244a4b598e09a99bc4eec0dc', 'f0bd0504e3d74daf9cec63acd8c47df2'),
	('6a7c017f244a4b598e09a99bc4eec0dc', '412d8b0bae6a45b4a0b24fa7e3616093'),
	('6a7c017f244a4b598e09a99bc4eec0dc', 'ef5c484dae954ae2a977ab1171c5d608'),
	('6a7c017f244a4b598e09a99bc4eec0dc', '057aca9e8fa64f43a67919e713205c1e'),
	('6a7c017f244a4b598e09a99bc4eec0dc', '52f72d74e06c481aa78d08c59bfd171a'),
	('6a7c017f244a4b598e09a99bc4eec0dc', '98ad976683444c90becdfb5f5d42330b');



