INSERT INTO `easyorder`.`sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('54a2acb8ce2c4e41bec69d84ec63f7d3', '27', '0,1,27,', '店铺信息', '10030', '/supplier/store', '', '', '1', 'supplier:supplier:view', '1', '2017-09-13 10:32:34', '1', '2017-09-13 10:41:36', '', '0');
INSERT INTO `easyorder`.`sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`) VALUES ('db5654c669534b32aa3a02204d8658d2', '54a2acb8ce2c4e41bec69d84ec63f7d3', '0,1,27,54a2acb8ce2c4e41bec69d84ec63f7d3,', '修改', '30', '', '', '', '0', 'supplier:supplier:edit', '1', '2017-09-13 16:28:32', '1', '2017-09-13 16:28:32', '', '0');


INSERT INTO `easyorder`.`sys_role_menu` (`role_id`, `menu_id`) VALUES ('6a7c017f244a4b598e09a99bc4eec0dc', '54a2acb8ce2c4e41bec69d84ec63f7d3');
INSERT INTO `easyorder`.`sys_role_menu` (`role_id`, `menu_id`) VALUES ('6a7c017f244a4b598e09a99bc4eec0dc', 'db5654c669534b32aa3a02204d8658d2');

ALTER TABLE `customer`
DROP COLUMN `supplier_id`,
DROP COLUMN `customer_group_id`;


ALTER TABLE `customer_group`
DROP COLUMN `supplier_id`,
ADD COLUMN `supplier_id`  varchar(50) NOT NULL AFTER `name`;

CREATE TABLE `supplier_customer` (
`id`  varchar(50) NOT NULL ,
`supplier_id`  varchar(50) NOT NULL COMMENT '供应商ID' ,
`customer_id`  varchar(50) NOT NULL COMMENT '客户ID' ,
`customer_group_id`  varchar(50) NOT NULL COMMENT '客户组ID' ,
`create_by`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人' ,
`create_date`  datetime NOT NULL COMMENT '创建时间' ,
`update_by`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人' ,
`update_date`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`remarks`  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`del_flag`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '删除标识(0：正常，1:删除)' ,
`version`  smallint(6) NOT NULL DEFAULT 0 COMMENT '版本号' ,
PRIMARY KEY (`id`)
)
;
