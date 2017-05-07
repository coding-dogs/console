-- 添加部门-供应商，供应商用户全部录入到此部门中
INSERT INTO `sys_office` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `area_id`, `code`, `type`, `grade`, `address`, `zip_code`, `master`, `phone`, `fax`, `email`, `USEABLE`, `PRIMARY_PERSON`, `DEPUTY_PERSON`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('5a3d24a35d4445a486dff0ba1aa91d96', '1', '0,1,', '供应商', 30, '19298dc65ecd45cc803a6da294d1ff60', 'GYS', '2', '1', '', '', '', '', '', '', '1', '1', '', '1', '2017-05-01 11:45:54', '1', '2017-05-01 11:45:54', '', '0');


-- 添加供应商菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('09f2a4e029d14a8398941ce3e7e0631c', '3e7e14f4ba264f42b9c99cd7bb1409ad', '0,1,3e7e14f4ba264f42b9c99cd7bb1409ad,', '查看供应商', 150, '', '', '', '0', 'supplier:supplier:view', '1', '2017-05-07 15:33:22', '1', '2017-05-07 15:33:22', '', '0'),
	('3e7e14f4ba264f42b9c99cd7bb1409ad', '1', '0,1,', '供应商管理', 2030, '', '', 'fa-cubes', '1', '', '1', '2017-05-07 15:23:32', '1', '2017-05-07 15:23:32', '', '0'),
	('65c79843fc254ecb8a1b904ff95d2d3e', '3e7e14f4ba264f42b9c99cd7bb1409ad', '0,1,3e7e14f4ba264f42b9c99cd7bb1409ad,', '添加供应商', 60, '', '', '', '0', 'supplier:supplier:add', '1', '2017-05-07 15:32:10', '1', '2017-05-07 15:32:18', '', '0'),
	('8962ef3d70414229a39b1caa74e62ba6', '3e7e14f4ba264f42b9c99cd7bb1409ad', '0,1,3e7e14f4ba264f42b9c99cd7bb1409ad,', '供应商列表', 30, '/supplier', '', '', '1', 'supplier:supplier:list', '1', '2017-05-07 15:24:54', '1', '2017-05-07 15:25:46', '', '0'),
	('91bda29e7d6f46598423490b60b00d6b', '3e7e14f4ba264f42b9c99cd7bb1409ad', '0,1,3e7e14f4ba264f42b9c99cd7bb1409ad,', '修改供应商', 90, '', '', '', '0', 'supplier:supplier:edit', '1', '2017-05-07 15:32:41', '1', '2017-05-07 15:32:41', '', '0'),
	('928832a46944463b83ea1637abb03098', '3e7e14f4ba264f42b9c99cd7bb1409ad', '0,1,3e7e14f4ba264f42b9c99cd7bb1409ad,', '删除供应商', 120, '', '', '', '0', 'supplier:supplier:del', '1', '2017-05-07 15:33:05', '1', '2017-05-07 18:53:28', '', '0');

-- 添加供应商管理员角色
INSERT INTO `sys_role` (`id`, `office_id`, `name`, `enname`, `role_type`, `data_scope`, `is_sys`, `useable`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('6a7c017f244a4b598e09a99bc4eec0dc', '', '供应商管理员', 'GYS_ADMIN', 'assignment', '8', '1', '1', '1', '2017-05-07 15:21:42', '1', '2017-05-07 15:33:46', '', '0');


-- 配置供应商管理员菜单
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
VALUES
	('6a7c017f244a4b598e09a99bc4eec0dc', '1'),
	('6a7c017f244a4b598e09a99bc4eec0dc', '27'),
	('6a7c017f244a4b598e09a99bc4eec0dc', '29'),
	('6a7c017f244a4b598e09a99bc4eec0dc', '56e274e0ec1c41298e19ab46cf4e001f'),
	('6a7c017f244a4b598e09a99bc4eec0dc', 'e3b80fae59ab4b4099d8349ecaf8c4dc'),
	('6a7c017f244a4b598e09a99bc4eec0dc', 'e4e64e24aa134deaa9d69c3b9495c997');


-- 添加供应商管理员用户
INSERT INTO `sys_user` (`id`, `company_id`, `office_id`, `login_name`, `password`, `no`, `name`, `email`, `phone`, `mobile`, `user_type`, `photo`, `login_ip`, `login_date`, `login_flag`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `qrcode`, `sign`)
VALUES
	('d6b98fb41bd94261848de73e91f9139b', '1', '5a3d24a35d4445a486dff0ba1aa91d96', 'gys', '9de00526e2afb125797d92ea4c17e802dc08c535a2d4f9876eefbf0e', 'GYS20170507', '测试供应商', 'qiudequan624@163.com', '', '13756523523', '', '', '0:0:0:0:0:0:0:1', '2017-05-07 15:30:26', '1', '1', '2017-05-07 15:29:55', 'd6b98fb41bd94261848de73e91f9139b', '2017-05-07 15:30:59', '', '0', '/console/userfiles//qrcode/.png', NULL);

-- 为供应商管理员用户分配供货商
INSERT INTO `sys_user_role` (`user_id`, `role_id`)
VALUES
	('d6b98fb41bd94261848de73e91f9139b', '6a7c017f244a4b598e09a99bc4eec0dc');

-- 添加一个供应商
INSERT INTO `supplier` (`id`, `name`, `supplier_no`, `logo_picture_url`, `manager_id`, `description`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `version`)
VALUES
	('609c84a0df3740ea95a01992a7b0ae1f', '测试供货商', 'GHS1494166268223', NULL, 'd6b98fb41bd94261848de73e91f9139b', '小吃 零食', '1', '2017-05-07 22:11:08', '1', '2017-05-07 22:11:08', '小吃 零食', '0', 0);

-- 用户-供应商
INSERT INTO `sys_user_supplier` (`id`, `sys_user_id`, `supplier_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `version`)
VALUES
	('e1a378f54f3340a2a47ace0470f297d5', 'd6b98fb41bd94261848de73e91f9139b', '609c84a0df3740ea95a01992a7b0ae1f', '1', '2017-05-07 22:11:08', '1', '2017-05-07 22:11:08', NULL, '0', 0);

