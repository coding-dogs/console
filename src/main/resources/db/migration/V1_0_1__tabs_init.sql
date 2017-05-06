USE easyorder;

DROP TABLE IF EXISTS `calendar`;

CREATE TABLE `calendar` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `title` varchar(64) DEFAULT NULL COMMENT '事件标题',
  `starttime` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '事件开始时间',
  `endtime` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '事件结束时间',
  `allday` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '是否为全天时间',
  `color` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '时间的背景色',
  `userid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日历';


DROP TABLE IF EXISTS `gen_scheme`;

CREATE TABLE `gen_scheme` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) DEFAULT NULL COMMENT '分类',
  `package_name` varchar(500) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `sub_module_name` varchar(30) DEFAULT NULL COMMENT '生成子模块名',
  `function_name` varchar(500) DEFAULT NULL COMMENT '生成功能名',
  `function_name_simple` varchar(100) DEFAULT NULL COMMENT '生成功能名（简写）',
  `function_author` varchar(100) DEFAULT NULL COMMENT '生成功能作者',
  `gen_table_id` varchar(200) DEFAULT NULL COMMENT '生成表编号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_scheme_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='生成方案';


INSERT INTO `gen_scheme` (`id`, `name`, `category`, `package_name`, `module_name`, `sub_module_name`, `function_name`, `function_name_simple`, `function_author`, `gen_table_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('000fbb2eef694532ab9df8c83632cba6',NULL,'curd','com.thinkgem.jeesite.modules','test','test','test','test','test','547be5f2504d4da8bf971ee0db0b625f','8b454376c0434e2792a1dc57edf80dbd','2016-01-03 20:30:28','8b454376c0434e2792a1dc57edf80dbd','2016-01-03 20:30:51',NULL,'0'),
	('505b3e23c7234df79c14c0dce24af75a',NULL,'curd','com.thinkgem.jeesite.modules','test','test','test','test','test','7612365ac0c645008661aaf479c45b82','8b454376c0434e2792a1dc57edf80dbd','2016-01-03 20:27:12','8b454376c0434e2792a1dc57edf80dbd','2016-01-03 20:28:48',NULL,'0');


DROP TABLE IF EXISTS `gen_table`;

CREATE TABLE `gen_table` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `class_name` varchar(100) DEFAULT NULL COMMENT '实体类名称',
  `parent_table` varchar(200) DEFAULT NULL COMMENT '关联父表',
  `parent_table_fk` varchar(100) DEFAULT NULL COMMENT '关联父表外键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  `issync` varchar(45) DEFAULT NULL COMMENT '同步',
  `table_type` varchar(45) DEFAULT NULL COMMENT '表类型',
  PRIMARY KEY (`id`),
  KEY `gen_table_name` (`name`),
  KEY `gen_table_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表';


DROP TABLE IF EXISTS `gen_table_column`;

CREATE TABLE `gen_table_column` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `gen_table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `comments` varchar(500) DEFAULT NULL COMMENT '描述',
  `jdbc_type` varchar(100) DEFAULT NULL COMMENT '列的数据类型的字节长度',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键',
  `is_null` char(1) DEFAULT NULL COMMENT '是否可为空',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段',
  `query_type` varchar(200) DEFAULT NULL COMMENT '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）',
  `show_type` varchar(200) DEFAULT NULL COMMENT '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）',
  `dict_type` varchar(200) DEFAULT NULL COMMENT '字典类型',
  `settings` varchar(2000) DEFAULT NULL COMMENT '其它设置（扩展字段JSON）',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序（升序）',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  `is_form` varchar(45) DEFAULT NULL COMMENT '是否表单显示',
  `tableName` varchar(45) DEFAULT NULL COMMENT '管理的查询表名',
  `fieldLabels` varchar(512) DEFAULT NULL,
  `fieldKeys` varchar(512) DEFAULT NULL,
  `searchLabel` varchar(45) DEFAULT NULL,
  `searchKey` varchar(45) DEFAULT NULL,
  `validateType` varchar(45) DEFAULT NULL,
  `min_length` varchar(45) DEFAULT NULL,
  `max_length` varchar(45) DEFAULT NULL,
  `min_value` varchar(45) DEFAULT NULL,
  `max_value` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `gen_table_column_table_id` (`gen_table_id`),
  KEY `gen_table_column_name` (`name`),
  KEY `gen_table_column_sort` (`sort`),
  KEY `gen_table_column_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表字段';



DROP TABLE IF EXISTS `gen_template`;

CREATE TABLE `gen_template` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `category` varchar(2000) DEFAULT NULL COMMENT '分类',
  `file_path` varchar(500) DEFAULT NULL COMMENT '生成文件路径',
  `file_name` varchar(200) DEFAULT NULL COMMENT '生成文件名',
  `content` text COMMENT '内容',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除）',
  PRIMARY KEY (`id`),
  KEY `gen_template_del_falg` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码模板表';


INSERT INTO `gen_template` (`id`, `name`, `category`, `file_path`, `file_name`, `content`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('0','0',',,','0','0','0',NULL,NULL,'1','2017-04-16 11:39:57',NULL,'0');


DROP TABLE IF EXISTS `group_user`;

CREATE TABLE `group_user` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注信息',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户',
  `group_id` varchar(64) DEFAULT NULL COMMENT '群组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='群组成员';

DROP TABLE IF EXISTS `iim_chat_history`;

CREATE TABLE `iim_chat_history` (
  `id` varchar(64) NOT NULL,
  `userid1` varchar(64) DEFAULT NULL,
  `userid2` varchar(64) DEFAULT NULL,
  `msg` varchar(1024) CHARACTER SET utf8 DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `iim_mail`;

CREATE TABLE `iim_mail` (
  `id` varchar(64) NOT NULL,
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `overview` varchar(128) DEFAULT NULL COMMENT '内容概要',
  `content` longblob COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件';


DROP TABLE IF EXISTS `iim_mail_box`;

CREATE TABLE `iim_mail_box` (
  `id` varchar(64) NOT NULL,
  `readstatus` varchar(45) DEFAULT NULL COMMENT '状态 0 未读 1 已读',
  `senderId` varchar(64) DEFAULT NULL COMMENT '发件人',
  `receiverId` varchar(6400) DEFAULT NULL COMMENT '收件人',
  `sendtime` datetime DEFAULT NULL COMMENT '发送时间',
  `mailid` varchar(64) DEFAULT NULL COMMENT '邮件外键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收件箱';


DROP TABLE IF EXISTS `iim_mail_compose`;

CREATE TABLE `iim_mail_compose` (
  `id` varchar(64) NOT NULL,
  `status` varchar(45) DEFAULT NULL COMMENT '状态 0 草稿 1 已发送',
  `readstatus` varchar(45) DEFAULT NULL COMMENT '状态 0 未读 1 已读',
  `senderId` varchar(64) DEFAULT NULL COMMENT '发送者',
  `receiverId` varchar(6400) DEFAULT NULL COMMENT '接收者',
  `sendtime` datetime DEFAULT NULL COMMENT '发送时间',
  `mailId` varchar(64) DEFAULT NULL COMMENT '邮件id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发件箱 草稿箱';


DROP TABLE IF EXISTS `monitor`;

CREATE TABLE `monitor` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `cpu` varchar(64) DEFAULT NULL COMMENT 'cpu使用率',
  `jvm` varchar(64) DEFAULT NULL COMMENT 'jvm使用率',
  `ram` varchar(64) DEFAULT NULL COMMENT '内存使用率',
  `toemail` varchar(64) DEFAULT NULL COMMENT '警告通知邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='系统监控';


INSERT INTO `monitor` (`id`, `cpu`, `jvm`, `ram`, `toemail`)
VALUES
	('1','99','99','99','qiudequan624@163.com');

DROP TABLE IF EXISTS `sys_area`;

CREATE TABLE `sys_area` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';


INSERT INTO `sys_area` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `code`, `type`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('1','0','0,','中国',10,'100000','1','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('17e8e72326574a0ea94b15d6eeddbb6d','364a832bee8a42afa333e338813330de','0,1,c11497c3311a4be29253f29bd59ab485,364a832bee8a42afa333e338813330de,','软件大道',30,'','4','1','2016-01-11 23:09:38','1','2016-01-11 23:09:38','','0'),
	('19298dc65ecd45cc803a6da294d1ff60','1','0,1,','北京',30,'','2','1','2016-01-15 19:55:09','1','2016-01-15 19:55:09','','0'),
	('364a832bee8a42afa333e338813330de','c11497c3311a4be29253f29bd59ab485','0,1,c11497c3311a4be29253f29bd59ab485,','南京',30,'','3','1','2015-12-24 21:38:02','1','2016-01-11 23:09:25','','0'),
	('90ecd439eb3845db97a627d9242145e8','1','0,1,','上海',30,'','2','1','2016-01-15 19:54:55','1','2016-01-15 19:54:55','','0'),
	('c11497c3311a4be29253f29bd59ab485','1','0,1,','江苏',30,'','2','1','2015-11-11 17:37:48','1','2015-11-11 17:38:14','','0'),
	('e418dc99691d4e29961d53ca218ece8b','1','0,1,','福建',30,'0100','2','1','2016-01-15 19:54:30','1','2016-01-15 19:54:30','','0'),
	('ee38f91df4444529b00f093be5a07b7a','e418dc99691d4e29961d53ca218ece8b','0,1,e418dc99691d4e29961d53ca218ece8b,','福州',30,'0200','1','1','2016-01-15 19:54:44','1','2016-01-15 19:54:44','','0');


DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';


INSERT INTO `sys_dict` (`id`, `value`, `label`, `type`, `description`, `sort`, `parent_id`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('0a22f3278a624882a822e0820f27efcb','1','主表','table_type','表类型',20,'0','1','2016-01-05 21:47:14','1','2016-01-05 21:53:34','','0'),
	('1','0','正常','del_flag','删除标记',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('10','yellow','黄色','color','颜色值',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('100','java.util.Date','Date','gen_java_type','Java类型\0\0',50,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('101','com.thinkgem.jeesite.modules.sys.entity.User','User','gen_java_type','Java类型\0\0',60,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('102','com.thinkgem.jeesite.modules.sys.entity.Office','Office','gen_java_type','Java类型\0\0',70,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('103','com.thinkgem.jeesite.modules.sys.entity.Area','Area','gen_java_type','Java类型\0\0',80,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('104','Custom','Custom','gen_java_type','Java类型\0\0',90,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('105','1','会议通告\0\0\0\0','oa_notify_type','通知通告类型',10,'0','1','2013-11-08 08:00:00','1','2013-11-08 08:00:00',NULL,'0'),
	('106','2','奖惩通告\0\0\0\0','oa_notify_type','通知通告类型',20,'0','1','2013-11-08 08:00:00','1','2013-11-08 08:00:00',NULL,'0'),
	('107','3','活动通告\0\0\0\0','oa_notify_type','通知通告类型',30,'0','1','2013-11-08 08:00:00','1','2013-11-08 08:00:00',NULL,'0'),
	('108','0','草稿','oa_notify_status','通知通告状态',10,'0','1','2013-11-08 08:00:00','1','2013-11-08 08:00:00',NULL,'0'),
	('109','1','发布','oa_notify_status','通知通告状态',20,'0','1','2013-11-08 08:00:00','1','2013-11-08 08:00:00',NULL,'0'),
	('11','orange','橙色','color','颜色值',50,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('110','0','未读','oa_notify_read','通知通告状态',10,'0','1','2013-11-08 08:00:00','1','2013-11-08 08:00:00',NULL,'0'),
	('111','1','已读','oa_notify_read','通知通告状态',20,'0','1','2013-11-08 08:00:00','1','2013-11-08 08:00:00',NULL,'0'),
	('12','default','默认主题','theme','主题方案',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('13','cerulean','天蓝主题','theme','主题方案',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('14','readable','橙色主题','theme','主题方案',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('17','1','国家','sys_area_type','区域类型',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('18','2','省份、直辖市','sys_area_type','区域类型',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('19','3','地市','sys_area_type','区域类型',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('1c0ac576c33d41fcb4c16602bf4fad5d','post','post','interface_type','接口类型',20,'0','1','2015-11-30 15:52:25','1','2015-11-30 15:52:39','','0'),
	('2','1','删除','del_flag','删除标记',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('20','4','区县','sys_area_type','区域类型',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('21','1','公司','sys_office_type','机构类型',60,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('22','2','部门','sys_office_type','机构类型',70,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('221a918bd1e149239a17ab0fdeaf5ecd','get','get','interface_type','接口类型',10,'0','1','2015-11-30 15:51:56','1','2015-11-30 15:51:56','','0'),
	('23','3','小组','sys_office_type','机构类型',80,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('24','4','其它','sys_office_type','机构类型',90,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('25','1','综合部','sys_office_common','快捷通用部门',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('26','2','开发部','sys_office_common','快捷通用部门',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('27','3','人力部','sys_office_common','快捷通用部门',50,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('28','1','一级','sys_office_grade','机构等级',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('29','2','二级','sys_office_grade','机构等级',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('3','1','显示','show_hide','显示/隐藏',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('30','3','三级','sys_office_grade','机构等级',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('31','4','四级','sys_office_grade','机构等级',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('32','1','所有数据','sys_data_scope','数据范围',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('33','2','所在公司及以下数据','sys_data_scope','数据范围',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('34','3','所在公司数据','sys_data_scope','数据范围',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('35','4','所在部门及以下数据','sys_data_scope','数据范围',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('36','5','所在部门数据','sys_data_scope','数据范围',50,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('37','8','仅本人数据','sys_data_scope','数据范围',90,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('38','9','按明细设置','sys_data_scope','数据范围',100,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('39','1','系统管理','sys_user_type','用户类型',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('4','0','隐藏','show_hide','显示/隐藏',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('40','2','部门经理','sys_user_type','用户类型',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('41','3','普通用户','sys_user_type','用户类型',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('42','basic','基础主题','cms_theme','站点主题',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('43','blue','蓝色主题','cms_theme','站点主题',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('43c9472f411c4d3eafb3bf5319ffe499','2','异常报告','report_type','异常的报告',20,'0','1','2015-12-08 17:49:57','1','2015-12-08 17:49:57','','0'),
	('44','red','红色主题','cms_theme','站点主题',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('45','article','文章模型','cms_module','栏目模型',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('46','picture','图片模型','cms_module','栏目模型',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('47','download','下载模型','cms_module','栏目模型',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('48','link','链接模型','cms_module','栏目模型',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('49','special','专题模型','cms_module','栏目模型',50,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('5','1','是','yes_no','是/否',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('50','0','默认展现方式','cms_show_modes','展现方式',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('51','1','首栏目内容列表','cms_show_modes','展现方式',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('52','2','栏目第一条内容','cms_show_modes','展现方式',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('53','0','发布','cms_del_flag','内容状态',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('54','1','删除','cms_del_flag','内容状态',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('55','2','审核','cms_del_flag','内容状态',15,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('56','1','首页焦点图','cms_posid','推荐位',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('57','2','栏目页文章推荐','cms_posid','推荐位',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('58','1','咨询','cms_guestbook','留言板分类',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('59','2','建议','cms_guestbook','留言板分类',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('5b899603552c48519e7ba8eb9da0b41f','0','单表','table_type','表类型',10,'0','1','2016-01-05 21:46:39','1','2016-01-05 21:53:50','','0'),
	('6','0','否','yes_no','是/否',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('60','3','投诉','cms_guestbook','留言板分类',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('61','4','其它','cms_guestbook','留言板分类',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'1'),
	('62','1','公休','oa_leave_type','请假类型',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('63','2','病假','oa_leave_type','请假类型',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('64','3','事假','oa_leave_type','请假类型',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('65','4','调休','oa_leave_type','请假类型',40,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('66','5','婚假','oa_leave_type','请假类型',60,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('67','1','接入日志','sys_log_type','日志类型',30,'0','1','2013-06-03 08:00:00','1','2013-06-03 08:00:00',NULL,'0'),
	('68','2','异常日志','sys_log_type','日志类型',40,'0','1','2013-06-03 08:00:00','1','2013-06-03 08:00:00',NULL,'0'),
	('680ddd8c91fe43588a7bb7aafe816633','1','正常报告','report_type','正常的报告',10,'0','1','2015-12-08 17:49:28','1','2015-12-08 17:49:28','正常的报告','0'),
	('69','leave','请假流程','act_type','流程类型',10,'0','1','2013-06-03 08:00:00','1','2013-06-03 08:00:00',NULL,'0'),
	('7','red','红色','color','颜色值',10,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('70','test_audit','审批测试流程','act_type','流程类型',20,'0','1','2013-06-03 08:00:00','1','2013-06-03 08:00:00',NULL,'0'),
	('71','1','常用流程','act_category','流程分类',10,'0','1','2013-06-03 08:00:00','1','2016-06-19 22:15:01','','0'),
	('71804c6b820048b09c9f6f2c11121113','ace','ACE风格','theme','主题方案',15,'0','1','2016-04-20 21:57:21','1','2016-04-20 21:57:21','','0'),
	('72','2','办公流程','act_category','流程分类',20,'0','1','2013-06-03 08:00:00','1','2016-06-19 22:15:09','','0'),
	('73','crud','增删改查','gen_category','代码生成分类',10,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('74','crud_many','增删改查（包含从表）','gen_category','代码生成分类',20,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('75','tree','树结构','gen_category','代码生成分类',30,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('76','=','=','gen_query_type','查询方式',10,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('77','!=','!=','gen_query_type','查询方式',20,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('78','&gt;','&gt;','gen_query_type','查询方式',30,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('79','&lt;','&lt;','gen_query_type','查询方式',40,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('8','green','绿色','color','颜色值',20,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('80','between','Between','gen_query_type','查询方式',50,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('81','like','Like','gen_query_type','查询方式',60,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('82','left_like','Left Like','gen_query_type','查询方式',70,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('83','right_like','Right Like','gen_query_type','查询方式',80,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('84','input','文本框','gen_show_type','字段生成方案',10,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('85','textarea','文本域','gen_show_type','字段生成方案',20,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('86','select','下拉框','gen_show_type','字段生成方案',30,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('87','checkbox','复选框','gen_show_type','字段生成方案',40,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('88','radiobox','单选框','gen_show_type','字段生成方案',50,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('89','dateselect','日期选择','gen_show_type','字段生成方案',60,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('9','blue','蓝色','color','颜色值',30,'0','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('90','userselect','人员选择\0','gen_show_type','字段生成方案',70,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('91','officeselect','部门选择','gen_show_type','字段生成方案',80,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('92','areaselect','区域选择','gen_show_type','字段生成方案',90,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('93','String','String','gen_java_type','Java类型',10,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('94','Long','Long','gen_java_type','Java类型',20,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('95','dao','仅持久层','gen_category','代码生成分类\0\0\0\0\0\0',40,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('96','1','男','sex','性别',10,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'0'),
	('97','2','女','sex','性别',20,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'0'),
	('98','Integer','Integer','gen_java_type','Java类型\0\0',30,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('99','Double','Double','gen_java_type','Java类型\0\0',40,'0','1','2013-10-28 08:00:00','1','2013-10-28 08:00:00',NULL,'1'),
	('bde6043665ef4571b85d0edab667cd15','3','树结构表','table_type','表类型',40,'0','1','2016-01-06 19:48:50','1','2016-01-06 19:48:50','','0'),
	('cc94b0c5df554a46894991210a5fc486','2','附表','table_type','表类型',30,'0','1','2016-01-05 21:47:38','1','2016-01-05 21:53:44','','0');


DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';


DROP TABLE IF EXISTS `sys_mdict`;

CREATE TABLE `sys_mdict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_mdict_parent_id` (`parent_id`),
  KEY `sys_mdict_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='多级字典表';


DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';


INSERT INTO `sys_menu` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('0787b03f8a7d46d2b398ddcc23545d31','7ec930fe50fb41d0a9c7aeaa01804d16','0,1,56e274e0ec1c41298e19ab46cf4e001f,4d463414bc974236941a6a14dc53c73a,7ec930fe50fb41d0a9c7aeaa01804d16,','增加',120,'','','','0','tools:testInterface:add','1','2016-01-07 13:42:33','1','2016-01-12 23:52:06','','0'),
	('0aa3712414d049a6a24e8bcddeae509a','27','0,1,27,','我的日程',100,'/iim/myCalendar','','','1','','1','2016-04-21 21:52:06','1','2016-04-21 21:52:06','','0'),
	('1','0','0,','功能菜单',0,NULL,NULL,NULL,'1',NULL,'1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('10','3','0,1,3,','字典管理',60,'/sys/dict/','','th-list','1','sys:dict:list','1','2013-05-27 08:00:00','1','2015-12-24 22:23:07','','0'),
	('11','10','0,1,3,10,','查看',30,NULL,NULL,NULL,'0','sys:dict:view','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('12','10','0,1,3,10,','修改',40,NULL,NULL,NULL,'0','sys:dict:edit','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('14','3','0,1,3,','区域管理',50,'/sys/area/','','th','1','sys:area:list','1','2013-05-27 08:00:00','1','2015-12-24 21:35:11','','0'),
	('15','14','0,1,3,14,','查看',30,NULL,NULL,NULL,'0','sys:area:view','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('16','14','0,1,3,14,','修改',40,NULL,NULL,NULL,'0','sys:area:edit','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('17','3','0,1,3,','机构管理',40,'/sys/office/','','th-large','1','sys:office:index','1','2013-05-27 08:00:00','1','2015-12-20 21:32:26','','0'),
	('18','17','0,1,3,17,','查看',30,NULL,NULL,NULL,'0','sys:office:view','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('19','17','0,1,3,17,','修改',40,NULL,NULL,NULL,'0','sys:office:edit','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('20','3','0,1,3,','用户管理',30,'/sys/user/index','','icon-adjust','1','sys:user:index','1','2013-05-27 08:00:00','1','2015-12-19 21:46:20','','0'),
	('21','20','0,1,3,20,','查看',30,NULL,NULL,NULL,'0','sys:user:view','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('22','20','0,1,3,20,','修改',40,NULL,NULL,NULL,'0','sys:user:edit','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('27','1','0,1,','我的面板',100,'','','fa-columns','1','','1','2013-05-27 08:00:00','1','2015-12-13 20:18:41','','0'),
	('29','27','0,1,27,','个人信息',30,'/sys/user/info','','icon-adjust','1','','1','2013-05-27 08:00:00','1','2016-03-27 22:43:46','','0'),
	('2a0f940fbe304a05a6b4040ddf6df279','20','0,1,3,20,','增加',70,'','','','0','sys:user:add','1','2015-12-19 21:47:00','1','2015-12-19 21:47:00','','0'),
	('3','1','0,1,','系统设置',500,'','','fa-cog','1','','1','2013-05-27 08:00:00','1','2015-11-04 17:27:37','','0'),
	('3c1c639c76f14f6f9903b0143371ea09','7','0,1,3,7,','添加',70,'','','','0','sys:role:add','1','2015-12-23 21:35:08','1','2015-12-23 21:36:18','','0'),
	('4','3','0,1,3,','菜单管理',30,'/sys/menu/','','list-alt','1','sys:menu:list','1','2013-05-27 08:00:00','1','2015-12-20 18:59:32','','0'),
	('4d463414bc974236941a6a14dc53c73a','56e274e0ec1c41298e19ab46cf4e001f','0,1,56e274e0ec1c41298e19ab46cf4e001f,','接口测试',15030,'','','fa-venus','1','','1','2015-11-10 18:49:21','1','2016-03-05 10:11:25','','0'),
	('5','4','0,1,3,4,','增加',30,'','','','0','sys:menu:add','1','2013-05-27 08:00:00','1','2015-12-20 19:00:22','','0'),
	('5239527958e94d418997b584b85d8b80','14','0,1,3,14,','删除',100,'','','','0','sys:area:del','1','2015-12-24 21:37:13','1','2015-12-24 21:37:13','','0'),
	('56','27','0,1,27,','文件管理',90,'/../static/ckfinder/ckfinder.html','','icon-zoom-out','1','','1','2013-05-27 08:00:00','1','2015-11-02 16:17:05','','0'),
	('56e274e0ec1c41298e19ab46cf4e001f','1','0,1,','常用工具',2000,'','','fa-anchor','1','','1','2016-03-03 16:30:04','1','2016-03-05 10:06:16','','0'),
	('57','56','0,1,27,40,56,','查看',30,NULL,NULL,NULL,'0','cms:ckfinder:view','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('57f1f00d6cb14819bef388acd10e6f5a','68','0,1,67,68,','删除',60,'','','','0','sys:log:del','1','2015-12-25 20:25:55','1','2015-12-25 20:25:55','','0'),
	('58','56','0,1,27,40,56,','上传',40,NULL,NULL,NULL,'0','cms:ckfinder:upload','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('59','56','0,1,27,40,56,','修改',50,NULL,NULL,NULL,'0','cms:ckfinder:edit','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('6','4','0,1,3,4,','修改',40,NULL,NULL,NULL,'0','sys:menu:edit','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('6509eed6eb634030a46723a18814035c','7','0,1,3,7,','分配用户',100,'','','','0','sys:role:assign','1','2015-12-23 21:35:37','1','2015-12-23 21:53:23','','0'),
	('67','1','0,1,','系统监控',985,'','','fa-video-camera','1','','1','2013-06-03 08:00:00','1','2016-03-05 10:18:41','','0'),
	('68','67','0,1,67,','日志查询',30,'/sys/log','','pencil','1','sys:log:list','1','2013-06-03 08:00:00','1','2015-12-25 20:26:16','','0'),
	('68f9151151174868ab436e11e03bf548','4','0,1,3,4,','删除',70,'','','','0','sys:menu:del','1','2015-12-20 19:01:16','1','2015-12-20 19:03:05','','0'),
	('6c672b854d2b4821b89297640df5fc26','82','0,1,79,82,','同步数据库',180,'','','','0','gen:genTable:synchDb','1','2016-01-07 11:31:00','1','2016-01-07 11:32:23','','0'),
	('6d3a6777693f47c98e9b3051cacbcfdb','10','0,1,3,10,','增加',70,'','','','0','sys:dict:add','1','2015-12-24 22:23:39','1','2015-12-24 22:24:22','','0'),
	('7','3','0,1,3,','角色管理',50,'/sys/role/','','lock','1','sys:role:list','1','2013-05-27 08:00:00','1','2015-12-23 21:33:46','','0'),
	('70de7eac2f3e4632b1b7aa3cd536b35a','4d463414bc974236941a6a14dc53c73a','0,1,56e274e0ec1c41298e19ab46cf4e001f,4d463414bc974236941a6a14dc53c73a,','接口测试',200,'/tools/testInterface/test','','','1','tools:testInterface:test','1','2015-11-30 15:21:14','1','2016-01-13 00:05:29','','0'),
	('7601161b0f204feea5cb285083ad8c29','56e274e0ec1c41298e19ab46cf4e001f','0,1,56e274e0ec1c41298e19ab46cf4e001f,','短信工具',60,'/tools/sms','','fa-commenting-o','1','','1','2016-03-04 00:19:43','1','2016-03-05 10:09:42','','0'),
	('79','1','0,1,','代码生成',20,'','','fa-codepen','1','','1','2013-10-16 08:00:00','1','2016-05-31 08:56:25','','0'),
	('79f0ffa47dbe43ffa8824d97612d344f','4','0,1,3,4,','保存排序',100,'','','','0','sys:menu:updateSort','1','2015-12-20 19:02:08','1','2015-12-20 19:02:08','','0'),
	('79fca849d3da4a82a4ade3f6b9f45126','20','0,1,3,20,','删除',100,'','','','0','sys:user:del','1','2015-12-19 21:47:44','1','2015-12-19 21:48:52','','0'),
	('7ec930fe50fb41d0a9c7aeaa01804d16','4d463414bc974236941a6a14dc53c73a','0,1,56e274e0ec1c41298e19ab46cf4e001f,4d463414bc974236941a6a14dc53c73a,','接口列表',30,'/tools/testInterface','','','1','tools:testInterface:list','1','2015-11-30 15:15:06','1','2016-01-12 23:50:52','','0'),
	('8','7','0,1,3,7,','查看',30,NULL,NULL,NULL,'0','sys:role:view','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('82','79','0,1,79,','表单配置',20,'/gen/genTable','','','1','gen:genTable:list','1','2013-10-16 08:00:00','1','2016-01-07 20:48:49','','0'),
	('84','67','0,1,67,','连接池监视',40,'/../druid',NULL,NULL,'1',NULL,'1','2013-10-18 08:00:00','1','2013-10-18 08:00:00',NULL,'0'),
	('8926112d6acd4a18b5e5dcf99a1f7ff3','7ec930fe50fb41d0a9c7aeaa01804d16','0,1,56e274e0ec1c41298e19ab46cf4e001f,4d463414bc974236941a6a14dc53c73a,7ec930fe50fb41d0a9c7aeaa01804d16,','删除',150,'','','','0','tools:testInterface:del','1','2016-01-07 13:43:13','1','2016-01-12 23:52:21','','0'),
	('8930e4aad1ba4a1c958d303968d8c442','17','0,1,3,17,','删除',100,'','','','0','sys:office:del','1','2015-12-20 21:19:16','1','2015-12-20 21:19:16','','0'),
	('8e01a74a9ca94a26a5263769c354afb9','67','0,1,67,','系统配置',100,'/sys/systemConfig','','','1','sys:systemConfig:index','1','2016-02-07 16:25:22','1','2016-02-07 16:25:22','','0'),
	('9','7','0,1,3,7,','修改',40,NULL,NULL,NULL,'0','sys:role:edit','1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('91aa429a6cdc4a9b954d84ff1456934b','68','0,1,67,68,','查看',30,'','','','0','sys:log:view','1','2015-12-25 20:25:25','1','2015-12-25 20:25:25','','0'),
	('95a6a82dc5fc4d07b46e5df57a0606a3','27','0,1,27,','信箱',10000,'/iim/mailBox/list?orderBy=sendtime desc','','','1','','1','2015-11-14 11:14:30','1','2015-11-24 18:01:46','','0'),
	('9bc1aa1053144a608b73f6fbd841d1c6','10','0,1,3,10,','删除',100,'','','','0','sys:dict:del','1','2015-12-24 22:24:04','1','2015-12-24 22:24:31','','0'),
	('a4c3dcee6cbc4fc6a0bf617d8619edf3','17','0,1,3,17,','增加',70,'','','','0','sys:office:add','1','2015-12-20 21:18:52','1','2015-12-20 21:18:52','','0'),
	('a4cfc713c3c04c32a274fb59c71e34ed','7ec930fe50fb41d0a9c7aeaa01804d16','0,1,56e274e0ec1c41298e19ab46cf4e001f,4d463414bc974236941a6a14dc53c73a,7ec930fe50fb41d0a9c7aeaa01804d16,','编辑',90,'','','','0','tools:testInterface:edit','1','2015-11-30 16:08:40','1','2016-01-12 23:51:51','','0'),
	('af0a174b4f424bc09a8cc0db83a64105','27','0,1,27,','通讯录',120,'/iim/contact/index','','','1','','1','2015-11-11 16:49:02','1','2015-11-11 16:49:02','','0'),
	('b9a776f5d7194406bb466388e3af9d08','20','0,1,3,20,','导出',160,'','','','0','sys:user:export','1','2015-12-19 21:48:34','1','2015-12-19 21:48:34','','0'),
	('c3de25a76785419b8a6820db3935941d','82','0,1,79,82,','导入',150,'','','','0','gen:genTable:importDb','1','2016-01-07 11:30:25','1','2016-01-07 11:30:25','','0'),
	('c6e0080e06014abd9240f870aadf3200','14','0,1,3,14,','增加',70,'','','','0','sys:area:add','1','2015-12-24 21:35:39','1','2015-12-24 21:35:39','','0'),
	('d3f1b6f292904ef5b95f7800cc777a48','82','0,1,79,82,','查看',30,'','','','0','gen:genTable:view,gen:genTableColumn:view','1','2016-01-07 11:26:42','1','2016-01-07 11:26:42','','0'),
	('d64d25d7b3014f9ba7736867cb2ffc43','82','0,1,79,82,','生成代码',210,'','','','0','gen:genTable:genCode','1','2016-01-07 11:31:24','1','2016-01-07 11:31:24','','0'),
	('d75f64438d994fc4830b1b3d138cde32','82','0,1,79,82,','删除',120,'','','','0','gen:genTable:del','1','2016-01-07 11:29:23','1','2016-01-07 11:29:23','','0'),
	('de7c50d276454f80881c41a096ecf55c','7','0,1,3,7,','删除',160,'','','','0','sys:role:del','1','2015-12-23 21:59:46','1','2015-12-23 21:59:46','','0'),
	('e03f8b6a5e454addb04fc08033b6f60b','82','0,1,79,82,','增加',90,'','','','0','gen:genTable:add','1','2016-01-07 11:28:59','1','2016-01-07 11:28:59','','0'),
	('e3b80fae59ab4b4099d8349ecaf8c4dc','29','0,1,27,29,','test',30,'/sys/user/info','','','1','','1','2016-03-27 21:10:29','1','2016-03-27 22:43:57','','0'),
	('e46555e269b84e2697857bdbb73f6676','56e274e0ec1c41298e19ab46cf4e001f','0,1,56e274e0ec1c41298e19ab46cf4e001f,','表单构建器',90,'/tools/beautifyhtml','','fa-building-o','1','','1','2016-03-05 10:00:01','1','2016-03-05 10:10:09','','0'),
	('e4e64e24aa134deaa9d69c3b9495c997','56e274e0ec1c41298e19ab46cf4e001f','0,1,56e274e0ec1c41298e19ab46cf4e001f,','二维码测试',15060,'/tools/TwoDimensionCodeController','','fa-qrcode','1','','1','2015-12-10 13:03:43','1','2016-03-05 10:04:53','','0'),
	('e824b7c20bb34c9ca9ad023e8873e67b','82','0,1,79,82,','编辑',60,'','','','0','gen:genTable:edit,gen:genTableColumn:edit','1','2016-01-07 11:27:55','1','2016-01-07 11:31:46','','0'),
	('ec7cf7a144a440cab217aabd4ffb7788','4','0,1,3,4,','查看',130,'','','','0','sys:menu:view','1','2015-12-20 19:02:54','1','2015-12-20 19:02:54','','0'),
	('f07b7ea555f84116b5390d2a73740817','56e274e0ec1c41298e19ab46cf4e001f','0,1,56e274e0ec1c41298e19ab46cf4e001f,','外部邮件',30,'/tools/email','','fa-envelope-o','1','','1','2016-03-03 20:39:12','1','2016-04-10 21:21:18','','0'),
	('f18fac5c4e6145528f3c1d87dbcb37d5','67','0,1,67,','系统监控管理',70,'/monitor/info','','','1','','1','2016-02-02 22:49:07','1','2016-02-02 23:15:07','','0'),
	('f34887a78fa245c1977603ca7dc98e11','20','0,1,3,20,','导入',130,'','','','0','sys:user:import','1','2015-12-19 21:48:13','1','2015-12-19 21:48:44','','0'),
	('f93f9a3a2226461dace3b8992cf055ba','7','0,1,3,7,','权限设置',130,'','','','0','sys:role:auth','1','2015-12-23 21:36:06','1','2015-12-23 21:36:06','','0'),
	('f9fe62186f0a4d7eada6c6827de79b26','7ec930fe50fb41d0a9c7aeaa01804d16','0,1,56e274e0ec1c41298e19ab46cf4e001f,4d463414bc974236941a6a14dc53c73a,7ec930fe50fb41d0a9c7aeaa01804d16,','查看',30,'','','','0','tools:testInterface:view','1','2015-11-30 16:08:14','1','2016-04-10 21:21:33','','0');


DROP TABLE IF EXISTS `sys_office`;

CREATE TABLE `sys_office` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `area_id` varchar(64) NOT NULL COMMENT '归属区域',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) NOT NULL COMMENT '机构类型',
  `grade` char(1) NOT NULL COMMENT '机构等级',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `USEABLE` varchar(64) DEFAULT NULL COMMENT '是否启用',
  `PRIMARY_PERSON` varchar(64) DEFAULT NULL COMMENT '主负责人',
  `DEPUTY_PERSON` varchar(64) DEFAULT NULL COMMENT '副负责人',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_del_flag` (`del_flag`),
  KEY `sys_office_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';


INSERT INTO `sys_office` (`id`, `parent_id`, `parent_ids`, `name`, `sort`, `area_id`, `code`, `type`, `grade`, `address`, `zip_code`, `master`, `phone`, `fax`, `email`, `USEABLE`, `PRIMARY_PERSON`, `DEPUTY_PERSON`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('1','0','0,','总公司',10,'4d8ec70b0e0c4c97af07b97c9a906c40','100000','1','1','','','','','','','1','','','1','2013-05-27 08:00:00','1','2015-11-11 17:40:49','','0'),
	('4','1','0,1,','财务部',30,'17e8e72326574a0ea94b15d6eeddbb6d','100003','2','1','','','','','','','1','','','1','2013-05-27 08:00:00','1','2016-01-11 23:10:27','','0'),
	('5','1','0,1,','技术部',40,'2','100004','2','1',NULL,NULL,NULL,NULL,NULL,NULL,'1',NULL,NULL,'1','2013-05-27 08:00:00','1','2013-05-27 08:00:00',NULL,'0'),
	('6a642e140e40496a8d467c646b8e935e','1','0,1,','市场部',30,'17e8e72326574a0ea94b15d6eeddbb6d','1000','2','1','','','','','','','1','','','1','2015-12-03 18:10:13','1','2016-01-11 23:10:10','','0'),
	('e0ef8af9cae6416f8bb359714a1b4244','1','0,1,','行政部',30,'4d8ec70b0e0c4c97af07b97c9a906c40','','2','1','','','','','','','1','','','1','2015-11-11 17:41:41','1','2015-11-11 17:41:55','','0');


DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属机构',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enname` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `role_type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `data_scope` char(1) DEFAULT NULL COMMENT '数据范围',
  `is_sys` varchar(64) DEFAULT NULL COMMENT '是否系统数据',
  `useable` varchar(64) DEFAULT NULL COMMENT '是否可用',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`),
  KEY `sys_role_enname` (`enname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';


INSERT INTO `sys_role` (`id`, `office_id`, `name`, `enname`, `role_type`, `data_scope`, `is_sys`, `useable`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`)
VALUES
	('1c54e003c1fc4dcd9b087ef8d48abac3','','管理员','system','assignment','1','1','1','1','2015-11-11 15:59:43','1','2016-08-14 15:31:42','','0'),
	('5','5','本部门管理员1','c2','security-role','8','0','0','1','2013-05-27 08:00:00','1','2015-12-23 20:18:16','111111111','0');


DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `menu_id` varchar(64) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';


INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
VALUES
	('1c54e003c1fc4dcd9b087ef8d48abac3','0787b03f8a7d46d2b398ddcc23545d31'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','07f90eb4ad5848cba282a2e19bbebf60'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','081ea57964844135b1e7890b9a081612'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','0aa3712414d049a6a24e8bcddeae509a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','0ca004d6b1bf4bcab9670a5060d82a55'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','0fc22e838cb54627b31bd467e91f7c84'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','1'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','10'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','11'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','12'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','14'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','15'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','16'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','17'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','18'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','1808931841bd4b439403b7c5756d4077'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','18936f565c32460a8ac7450a852921ba'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','19'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','1c3a60d9cbd4494b961ea2f814ab5bd8'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','20'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','20b1147d34e14580b88ed00dd5d3ef78'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','21'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','21c626fc496b49f8880bb47836507b50'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','22'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','2209119856094953ba6ebc339b415887'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','2350cacaa5ad4fe1ba9d69dc78d641b1'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','23664b155b004c0ca9f79c2aec21ef04'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','247bcf278dfc40cca447f5c736c6d849'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','27'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','2799370c7a974843b3a3fb017ae3e6d6'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','28abe4b0cb3b4c8480f84c07a5dfaf06'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','29'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','2977e5b927cd41428b8f8d90359e0839'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','2a0f940fbe304a05a6b4040ddf6df279'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','2c6838e2f935453485342836efb63c1f'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','33b1c8dc1cf94c1c867d2f8347da7f29'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3407c5ea8b63475f8ec3691711c9608b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3518c0cfa3b645b09a6fa979ac641f41'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3c112afa4e7740eb83737b1490032ab1'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3c1c639c76f14f6f9903b0143371ea09'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3c92c17886944d0687e73e286cada573'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3cc4a9a5880d40fbbad499dcdfa54e51'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','3d3767db110d451ab2f86dcb961e834a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','4'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','456fea6a94114987b3f4d5b973a4f46b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','45fdc4de59eb4fb48653d7ac6a553eac'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','471f032f65384b4f81dc60b0671c7ce0'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','4855cf3b25c244fb8500a380db189d97'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','487af8d5d1034c9d852007f1b3a27342'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','4d463414bc974236941a6a14dc53c73a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','5'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','5239527958e94d418997b584b85d8b80'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','52ac2158c7ab4272a3469855de8bd78e'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','55a33ae3ec2d466682ba2d701bea6fd0'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','55f7306aa1ee41ae9f93fc45d1497046'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','56'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','568331ca5a24411b96f5b4a4d0498170'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','56e274e0ec1c41298e19ab46cf4e001f'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','57'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','57f1f00d6cb14819bef388acd10e6f5a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','58'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','59'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','59f304d5312e4502930fef5dd8117b29'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','5b1ca1d4aa5b4a56a6682093fafda4f5'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','5ca66750233b46699593dc083f87cd60'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','62'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','624c57fb83a54ff686c1e686bebde4c5'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6509eed6eb634030a46723a18814035c'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','65272697795745c5beca02f6b295d1b4'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','65c294631cea43f3bf57929cb42899a3'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','67'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','68'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6890f329854d41bd92d4857e18ce641d'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','68bc2a43d65948519dbbd0446d5121f9'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','68f9151151174868ab436e11e03bf548'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6abf277477e943b5b20ce205f30810cb'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6c5e9866895e46548def82200c41a747'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6c672b854d2b4821b89297640df5fc26'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6d3a6777693f47c98e9b3051cacbcfdb'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6eec88c2803b458b8f11463317f22a37'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','6fa1a6734aeb4237a22b61b10f0affb8'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','7'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','70de7eac2f3e4632b1b7aa3cd536b35a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','70f867cb6f23489d8427e295e0e63a6f'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','7402c1aad68f46d99f3a0868737ca582'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','7601161b0f204feea5cb285083ad8c29'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','78bd8a7958a84617a06bcf72837eae0b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','79'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','7995618784734ef59e45183a4681d8ca'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','79f0ffa47dbe43ffa8824d97612d344f'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','79fca849d3da4a82a4ade3f6b9f45126'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','7a01ad47a4be4bc48a33c62180275ee8'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','7ec930fe50fb41d0a9c7aeaa01804d16'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','7fe0397a90214f49adc9bbbe48e5ab69'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','82'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8211dbd824ca422fb5baf1bade46696b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','84'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','843fdb78b8854bd48e503287f795a7d0'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8815c9a95cf54b19a39b0ce0ff4c32f8'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','89'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8926112d6acd4a18b5e5dcf99a1f7ff3'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8930e4aad1ba4a1c958d303968d8c442'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8ae2282ef26347cbb3540151a2b8612b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8d369ea660e04241a6c88d75c49a68ce'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8e01a74a9ca94a26a5263769c354afb9'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8f6d10c69f0247a0806f0f1db4caef16'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','8ff255c054e5467aac19eb9d5abf8859'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','9'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','90'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','900f410b044542eeba2382d18f5d2704'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','91aa429a6cdc4a9b954d84ff1456934b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','93635a11ad63482daca0291579c9b65e'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','95a6a82dc5fc4d07b46e5df57a0606a3'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','9630060e648c4534aa0567b0c7df5567'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','9bc1aa1053144a608b73f6fbd841d1c6'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','9df2344158e34694bf3303cf871ee250'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','9fdf5971a9e64fac8bbffec2825a5f97'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','9fe9475cccba4e6fa3371cc5f023c32a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','a25268095cee41da822a6ed5c0c8614e'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','a28d551fc2f945e5b9aed595d45dfb62'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','a4c3dcee6cbc4fc6a0bf617d8619edf3'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','a4cfc713c3c04c32a274fb59c71e34ed'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','a734592d272a4859b8f76db16443296a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','a8cf23d117bb439bb5954860a25e87bb'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','a90ba79ffbcb40dfa705fe4992e29d22'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','aab54af57cbc4e4687223cc01a53d0af'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','ae4627d9a17c4593a171349a61ab6fcf'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','af0a174b4f424bc09a8cc0db83a64105'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','afab2db430e2457f9cf3a11feaa8b869'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b03b375aeb1b40bc89e31f24730b6b25'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b051156518fd413794cf0316830aaf07'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b13ca6f1eb834afb82dc1f0bde511eff'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b1f6d1b86ba24365bae7fd86c5082317'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b2823c41e84c4a11bc36f3367c7327ec'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b354fd05254d48259525e4ac3cb667f7'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b86770f3e02b46148da7aa68d285835a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b92414f4c2de4a468e0b0ced34d8f76c'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','b9a776f5d7194406bb466388e3af9d08'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','be16a41c89be47cfa070f694e5e782da'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','c176ad1dee4a45be83acef60cf6f46cc'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','c2e4d9082a0b4386884a0b203afe2c5c'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','c3de25a76785419b8a6820db3935941d'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','c63e8a6e5ae3401e9583662f9e8442b6'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','c6e0080e06014abd9240f870aadf3200'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','c7fa36c7142f481397c2cc12e2bc828a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','c824b5c244cc4098853671e75858ca55'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','cac8d5931531427ca0ba7e55927dcdf2'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','cc910e0351eb4f218bd052c847dd5bf7'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','d15ec45a4c5449c3bbd7a61d5f9dd1d2'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','d3032f23f0604b78a1857cf8185ae179'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','d3f1b6f292904ef5b95f7800cc777a48'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','d64d25d7b3014f9ba7736867cb2ffc43'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','d694c6dedef04cd9ab257cee10dc094f'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','d75f64438d994fc4830b1b3d138cde32'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','d78a0f0a6c8c48699e5d081f64014b99'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','dae12251f0234673bd7b21fdd47a02b9'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','de7c50d276454f80881c41a096ecf55c'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e03f8b6a5e454addb04fc08033b6f60b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e1055d1d229a4172ac4a4f664030b64d'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e2565667b9a745eb870debfd0830c46f'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e26688ed891240e8bd383431c35e76c4'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e2efcf80fc3747329fe02d489dbfda95'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e3b80fae59ab4b4099d8349ecaf8c4dc'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e46555e269b84e2697857bdbb73f6676'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e4e64e24aa134deaa9d69c3b9495c997'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e824b7c20bb34c9ca9ad023e8873e67b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','e9b4c72ca64b455b8f8f8e0db85da5c3'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','ea1046197b724b71b98c9eabf238f0af'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','ec7cf7a144a440cab217aabd4ffb7788'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','ecd805be31dd4fa5b8c1b76a9a5ac85f'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','ed9f6c6dbc334d0fb9607a52a818e102'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','eda782d5db444d09ada20f0a95496b3b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f0214f8481134eb7a5528c638c3a2b76'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f02317b6b08941d8ba9834f15b44ada0'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f07b7ea555f84116b5390d2a73740817'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f18fac5c4e6145528f3c1d87dbcb37d5'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f34887a78fa245c1977603ca7dc98e11'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f5b2028cfad9470085c7c846de33193a'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f87f68e686974c12b4923a81df0dfa7c'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f910a9e29c414ed887906a3c9fa1625b'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f93f9a3a2226461dace3b8992cf055ba'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','f9fe62186f0a4d7eada6c6827de79b26'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','fdb06de8261a4d4a9bef2922a2de4666'),
	('1c54e003c1fc4dcd9b087ef8d48abac3','ff5af1be6f7544e2893b1c79dd6e0745'),
	('5','0b2ebd4d639e4c2b83c2dd0764522f24'),
	('5','0ca004d6b1bf4bcab9670a5060d82a55'),
	('5','1'),
	('5','10'),
	('5','11'),
	('5','12'),
	('5','14'),
	('5','15'),
	('5','16'),
	('5','17'),
	('5','18'),
	('5','19'),
	('5','20'),
	('5','21'),
	('5','22'),
	('5','27'),
	('5','29'),
	('5','2a0f940fbe304a05a6b4040ddf6df279'),
	('5','3'),
	('5','30'),
	('5','3c92c17886944d0687e73e286cada573'),
	('5','4'),
	('5','4855cf3b25c244fb8500a380db189d97'),
	('5','4d463414bc974236941a6a14dc53c73a'),
	('5','5'),
	('5','56'),
	('5','57'),
	('5','58'),
	('5','59'),
	('5','6'),
	('5','62'),
	('5','63'),
	('5','64'),
	('5','65'),
	('5','66'),
	('5','67'),
	('5','68'),
	('5','68f9151151174868ab436e11e03bf548'),
	('5','69'),
	('5','7'),
	('5','70'),
	('5','70de7eac2f3e4632b1b7aa3cd536b35a'),
	('5','72'),
	('5','73'),
	('5','74'),
	('5','75'),
	('5','79'),
	('5','79f0ffa47dbe43ffa8824d97612d344f'),
	('5','79fca849d3da4a82a4ade3f6b9f45126'),
	('5','7ec930fe50fb41d0a9c7aeaa01804d16'),
	('5','8'),
	('5','80'),
	('5','81'),
	('5','82'),
	('5','83'),
	('5','84'),
	('5','86'),
	('5','87'),
	('5','88'),
	('5','89'),
	('5','8930e4aad1ba4a1c958d303968d8c442'),
	('5','9'),
	('5','90'),
	('5','95a6a82dc5fc4d07b46e5df57a0606a3'),
	('5','a4c3dcee6cbc4fc6a0bf617d8619edf3'),
	('5','a4cfc713c3c04c32a274fb59c71e34ed'),
	('5','af0a174b4f424bc09a8cc0db83a64105'),
	('5','afab2db430e2457f9cf3a11feaa8b869'),
	('5','b1f6d1b86ba24365bae7fd86c5082317'),
	('5','b9a776f5d7194406bb466388e3af9d08'),
	('5','ba8092291b40482db8fe7fc006ea3d76'),
	('5','c2e4d9082a0b4386884a0b203afe2c5c'),
	('5','d15ec45a4c5449c3bbd7a61d5f9dd1d2'),
	('5','df7ce823c5b24ff9bada43d992f373e2'),
	('5','e2565667b9a745eb870debfd0830c46f'),
	('5','e4e64e24aa134deaa9d69c3b9495c997'),
	('5','ec7cf7a144a440cab217aabd4ffb7788'),
	('5','f34887a78fa245c1977603ca7dc98e11'),
	('5','f9fe62186f0a4d7eada6c6827de79b26');


DROP TABLE IF EXISTS `sys_role_office`;

CREATE TABLE `sys_role_office` (
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `office_id` varchar(64) NOT NULL COMMENT '机构编号',
  PRIMARY KEY (`role_id`,`office_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-机构';


DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `company_id` varchar(64) DEFAULT NULL COMMENT '归属公司',
  `office_id` varchar(64) DEFAULT NULL COMMENT '归属部门',
  `login_name` varchar(100) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_type` char(1) DEFAULT NULL COMMENT '用户类型',
  `photo` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` varchar(64) DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `qrcode` varchar(1000) DEFAULT NULL COMMENT '二维码',
  `sign` varchar(450) DEFAULT NULL COMMENT '个性签名',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`office_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_company_id` (`company_id`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


INSERT INTO `sys_user` (`id`, `company_id`, `office_id`, `login_name`, `password`, `no`, `name`, `email`, `phone`, `mobile`, `user_type`, `photo`, `login_ip`, `login_date`, `login_flag`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`, `del_flag`, `qrcode`, `sign`)
VALUES
	('1','1','5','admin','d6a33a2456338a88acec133487d38ccf163abb4575b368b074e327ee','13815874603','admin','admin@console.com','010-888822222','18927364758','','/console/userfiles/1/images/photo/2017/04/logo.png','0:0:0:0:0:0:0:1','2017-04-16 12:04:28','1','1','2013-05-27 08:00:00','1','2017-04-16 11:45:24','test','0','/console/userfiles/1/qrcode/test.png','你好啊');


DROP TABLE IF EXISTS `sys_user_friend`;

CREATE TABLE `sys_user_friend` (
  `id` varchar(64) NOT NULL,
  `userId` varchar(64) NOT NULL,
  `friendId` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';


INSERT INTO `sys_user_role` (`user_id`, `role_id`)
VALUES
	('1','1c54e003c1fc4dcd9b087ef8d48abac3');


DROP TABLE IF EXISTS `systemconfig`;

CREATE TABLE `systemconfig` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `smtp` varchar(64) DEFAULT NULL COMMENT '邮箱服务器地址',
  `port` varchar(64) DEFAULT NULL COMMENT '邮箱服务器端口',
  `mailname` varchar(64) DEFAULT NULL COMMENT '系统邮箱地址',
  `mailpassword` varchar(64) DEFAULT NULL COMMENT '系统邮箱密码',
  `smsname` varchar(64) DEFAULT NULL COMMENT '短信用户名',
  `smspassword` varchar(64) DEFAULT NULL COMMENT '短信密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='系统配置';


INSERT INTO `systemconfig` (`id`, `smtp`, `port`, `mailname`, `mailpassword`, `smsname`, `smspassword`)
VALUES
	('1','smtp.qq.com','465','842276463@qq.com','','','');


DROP TABLE IF EXISTS `t_group`;

CREATE TABLE `t_group` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `groupname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '群组名',
  `avatar` varchar(256) DEFAULT NULL COMMENT '群头像',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注信息',
  `del_flag` varchar(64) DEFAULT NULL COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='群组';

DROP TABLE IF EXISTS `test_interface`;

CREATE TABLE `test_interface` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键',
  `type` varchar(16) DEFAULT NULL COMMENT '接口类型',
  `url` varchar(256) DEFAULT NULL COMMENT '请求URL',
  `body` varchar(2048) DEFAULT NULL COMMENT '请求body',
  `successmsg` varchar(512) DEFAULT NULL COMMENT '成功时返回消息',
  `errormsg` varchar(512) DEFAULT NULL COMMENT '失败时返回消息',
  `remarks` varchar(512) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET latin1 DEFAULT '0' COMMENT '删除标记',
  `name` varchar(1024) DEFAULT NULL COMMENT '接口名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口列表';
