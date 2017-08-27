INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('1e550825419d47dca306ee9b6db6de43', 'CANCELED', '已取消', 'mtOrderStatusCd', '订货单状态-已取消', 20, '0', '1', '2017-08-21 16:44:19', '1', '2017-08-21 16:44:19', '', '0'),
	('c79960c700524785958beac8e91f466d', 'PENDING_RECEIVE', '待收货', 'mtOrderStatusCd', '订货单状态-待收货', 20, '0', '1', '2017-08-21 16:43:02', '1', '2017-08-21 16:43:02', '', '0'),
	('d9cb14fad5ab462dac27305c94a75868', 'ALREADY_RECEIVE', '待收货', 'mtOrderStatusCd', '订货单状态-已收货', 20, '0', '1', '2017-08-21 16:43:45', '1', '2017-08-21 16:43:45', '', '0'),
	('feddd7195c12492c9cc685718f6a0de8', 'PENDING_AUDIT', '待审核', 'mtOrderStatusCd', '订货单状态-待审核', 10, '0', '1', '2017-08-21 16:40:53', '1', '2017-08-21 16:40:53', '', '0'),
	('ffd9614ca1a3496ab03c698231978fce', 'PEDING_SHIP', '待发货', 'mtOrderStatusCd', '订货单状态-待发货', 20, '0', '1', '2017-08-21 16:42:06', '1', '2017-08-21 16:42:06', '', '0');
