<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户组管理</title>
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
		<h5>客户组列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
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
	<form:form id="searchForm" modelAttribute="customerGroup" action="${ctx}/customerManager/customerGroup/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>客户组名称：</span>
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
			<shiro:hasPermission name="customer:customerGroup:add">
				<table:addRow url="${ctx}/customerManager/customerGroup/form" title="客户组"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="customer:customerGroup:edit">
			    <table:editRow url="${ctx}/customerManager/customerGroup/form" title="客户组" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="customer:customerGroup:del">
				<table:delRow url="${ctx}/customerManager/customerGroup/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="customer:customerGroup:import">
				<table:importExcel url="${ctx}/customerManager/customerGroup/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="customer:customerGroup:export">
	       		<table:exportExcel url="${ctx}/customerManager/customerGroup/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">客户组名称</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th  class="sort-column remarks">备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty page or empty page.list }">
					<tr>
						<td colspan="5" align="center">暂无客户组信息</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${page.list}" var="customerGroup">
						<tr>
							<td> <input type="checkbox" id="${customerGroup.id}" class="i-checks"></td>
							<td><a  href="#" onclick="openDialogView('查看客户组', '${ctx}/customerManager/customerGroup/form?id=${customerGroup.id}','800px', '500px')">
								${customerGroup.name}
							</a></td>
							<td>
								<fmt:formatDate value="${customerGroup.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${customerGroup.remarks}
							</td>
							<td>
								<shiro:hasPermission name="customer:customerGroup:view">
									<a href="#" onclick="openDialogView('查看客户组', '${ctx}/customerManager/customerGroup/form?id=${customerGroup.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="customer:customerGroup:edit">
			    					<a href="#" onclick="openDialog('修改客户组', '${ctx}/customerManager/customerGroup/form?id=${customerGroup.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
			    				</shiro:hasPermission>
			    				<shiro:hasPermission name="customer:customerGroup:del">
									<a href="${ctx}/customerManager/customerGroup/delete?id=${customerGroup.id}" onclick="return confirmx('确认要删除该客户组吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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