<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>供应商管理</title>
<meta name="decorator" content="default" />
<link rel="stylesheet"
	href="${ctxStatic}/easy-selector/easy-selector.css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>客户详情 </h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="fa fa-wrench"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
		    
		    <div class="ibox-content">
			
				<div class="row">
					<div class="col-sm-12">
						<div class="easy-order customer-base-info">
							<h2>基本资料</h2>
							<dl class="dl-horizontal">
								<dt>登录账号</dt>
								<dd>${customer.accountNo}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>账号状态</dt>
								<dd>${fns:getDictLabel(customer.mtCustomerStatusCd,"mtCustomerStatusCd", "-")}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>客户名称</dt>
								<dd>${customer.name}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>客户编号</dt>
								<dd>${customer.customerNo}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>所属客户组</dt>
								<dd>${customer.customerGroupName}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>归属地区</dt>
								<dd>${customer.mtCityCd}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>备注</dt>
								<dd>${customer.remarks}</dd>
							</dl>
						</div>
						<!-- 联系人信息 -->
						<div class="easy-order customer-contact">
							<h2>联系人</h2>
							<dl class="dl-horizontal">
								<dt>联系人</dt>
								<dd>${customer.contactName}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>电话号码</dt>
								<dd>${customer.contactPhone}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>电子邮箱</dt>
								<dd>${customer.contactEmail}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>联系地址</dt>
								<dd>${customer.contactAddress}</dd>
							</dl>
						</div>
					</div>
				</div>
				<div class="btns">
					<button type="button" class="btn btn-white" onclick="window.location.href='${ctx}/customerManager/customer/'">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>