<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>店铺信息</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/easyorder/css/common.css">
	<script type="text/javascript">
		
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>店铺信息 </h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="fa fa-wrench"></i>
					</a>
					<ul class="dropdown-menu dropdown-user">
						<li>
							<a id="supplierEditBtn" data-toggle="modal" data-target="#register">资料变更</a>
						</li>
					</ul>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
		    
		    <div class="ibox-content">
		    	<sys:message content="${message}"></sys:message>
				<div class="row">
					<div class="col-sm-3 col-md-3" style="margin-bottom: 10px;">
						<img alt="image" width="100%" src="${supplier.logoPictureUrl}">
					</div>
					<div class="col-sm-9 col-md-9">
						<div class="table-responsive">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td><strong>店铺名称</strong></td>
										<td>${supplier.name}</td>
									</tr>
									<tr>
										<td><strong>店铺地址</strong></td>
										<td>${supplier.storeAddress}</td>
									</tr>
									<tr>
										<td><strong>店铺管理员名称</strong></td>
										<td>${supplier.managerName}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$("#supplierEditBtn").on('click', function() {
			openDialog('店铺信息变更', '${ctx}/supplier/infoEdit', '700px', '500px');
		});
	</script>
</body>
</html>