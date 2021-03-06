<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品品牌管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>商品品牌列表 </h5>
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
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="productBrand" action="${ctx}/productManager/productBrand/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>品牌名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="product:productBrand:add">
				<table:addRow url="${ctx}/productManager/productBrand/form?action=add" title="商品品牌"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:productBrand:edit">
			    <table:editRow url="${ctx}/productManager/productBrand/form?action=edit" title="商品品牌" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:productBrand:del">
				<table:delRow url="${ctx}/productManager/productBrand/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:productBrand:import">
				<table:importExcel url="${ctx}/productManager/productBrand/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:productBrand:export">
	       		<table:exportExcel url="${ctx}/productManager/productBrand/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th>品牌LOGO</th>
				<th  class="sort-column name">品牌名称</th>
				<th  class="sort-column simpleName">品牌简称</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th  class="sort-column remarks">备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${empty page or empty page.list }">
				<tr>
					<td colspan="6" align="center">暂无商品品牌信息</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${page.list}" var="productBrand">
					<tr>
						<td> <input type="checkbox" id="${productBrand.id}" class="i-checks"></td>
						<td>
							<img width="50" height="50" alt="${productBrand.name}" title="${productBrand.name}" src="${productBrand.logoUrl}"/>
						</td>
						<td><a  href="#" onclick="openDialogView('查看品牌', '${ctx}/productManager/productBrand/form?action=view&id=${productBrand.id}', '800px', '500px')">
							${productBrand.name}
						</a></td>
						<td>
							${productBrand.simpleName}
						</td>
						<td>
							<fmt:formatDate value="${productBrand.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${productBrand.remarks}
						</td>
						<td>
							<shiro:hasPermission name="product:productBrand:view">
								<a href="#" onclick="openDialogView('查看品牌', '${ctx}/productManager/productBrand/form?action=view&id=${productBrand.id}', '800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="product:productBrand:edit">
		    					<a href="#" onclick="openDialog('修改品牌', '${ctx}/productManager/productBrand/form?action=edit&id=${productBrand.id}', '800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
		    				</shiro:hasPermission>
		    				<shiro:hasPermission name="product:productBrand:del">
								<a href="${ctx}/productManager/productBrand/delete?id=${productBrand.id}" onclick="return confirmx('确认要删除该商品品牌吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
							</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>