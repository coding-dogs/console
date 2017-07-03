<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>品牌信息</title>
<meta name="decorator" content="default" />
<link rel="stylesheet"
	href="${ctxStatic}/easy-selector/easy-selector.css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>品牌信息 </h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
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
							<h2>详细信息</h2>
							<dl class="dl-horizontal">
								<dt>品牌名称</dt>
								<dd>${productBrand.name}</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>品牌简称</dt>
								<dd>${productBrand.simpleName}</dd>
							</dl>
							<%-- <dl class="dl-horizontal">
								<dt>所属类目</dt>
								<dd>${productBrand.productCategoryName}</dd>
							</dl> --%>
							<dl class="dl-horizontal">
								<dt>备注</dt>
								<dd>${productBrand.remarks}</dd>
							</dl>
						</div>
					</div>
				</div>
				<div class="btns">
					<button type="button" class="btn btn-white" onclick="window.location.href='${ctx}/productManager/productBrand/'">返回</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>