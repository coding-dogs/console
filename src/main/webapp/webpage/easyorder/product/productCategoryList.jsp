<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>商品分类管理</title>
<meta name="decorator" content="default" />
<%@include file="/webpage/include/treetable.jsp" %>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>商品分类列表</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					<a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>

			<div class="ibox-content">
				<sys:message content="${message}" />

				<!--查询条件-->
				<div class="row">
					<div class="col-sm-12">
						<form:form id="searchForm" modelAttribute="productCategory"
							action="${ctx}/productManager/productCategory/" method="post"
							class="form-inline">
							<input id="pageNo" name="pageNo" type="hidden"
								value="${page.pageNo}" />
							<input id="pageSize" name="pageSize" type="hidden"
								value="${page.pageSize}" />
							<table:sortColumn id="orderBy" name="orderBy"
								value="${page.orderBy}" callback="sortOrRefresh();" />
							<!-- 支持排序 -->
							<div class="form-group">
								<span>商品分类名称：</span>
								<form:input path="name" htmlEscape="false" maxlength="50"
									class=" form-control input-sm" />
							</div>
						</form:form>
						<br />
					</div>
				</div>

				<!-- 工具栏 -->
				<div class="row">
					<div class="col-sm-12">
						<div class="pull-left">
							<shiro:hasPermission name="product:productCategory:add">
								<table:addRow url="${ctx}/productManager/productCategory/form"
									title="商品分类"></table:addRow>
								<!-- 增加按钮 -->
							</shiro:hasPermission>
							<button class="btn btn-white btn-sm " data-toggle="tooltip"
								data-placement="left" onclick="sortOrRefresh()" title="刷新">
								<i class="glyphicon glyphicon-repeat"></i> 刷新
							</button>

						</div>
						<div class="pull-right">
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="search()">
								<i class="fa fa-search"></i> 查询
							</button>
							<button class="btn btn-primary btn-rounded btn-outline btn-sm "
								onclick="reset()">
								<i class="fa fa-refresh"></i> 重置
							</button>
						</div>
					</div>
				</div>
				<p class="bg-warning mt-10 padding-5">注意添加商品分类时，层级为三级</p>
				<!-- 表格 -->
				<form id="listForm" method="post">
					<table id="treeTable"
						class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
						<thead>
							<tr>
								<th>名称</th>
								<th>排序</th>
								<shiro:hasPermission name="product:productCategory:edit">
									<th>操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty productCategoryList }">
									<tr>
										<td colspan="3" align="center">暂无商品分类信息</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${productCategoryList}" var="productCategory">
										<tr id="${productCategory.id}"
											pId="${not empty productCategory.pid ? productCategory.pid : '0'}">
											
											<td nowrap><i
												class=""></i><a
												href="#"
												onclick="openDialogView('查看分类', '${ctx}/productManager/productCategory/form?id=${productCategory.id}','800px', '500px')">${productCategory.name}</a></td>
											<td>
												${productCategory.sort}
											</td>
											<td nowrap>
												<shiro:hasPermission name="product:productCategory:view">
													<a href="#"
														onclick="openDialogView('查看分类', '${ctx}/productManager/productCategory/form?id=${productCategory.id}','800px', '500px')"
														class="btn btn-info btn-xs"><i
														class="fa fa-search-plus"></i> 查看</a>
												</shiro:hasPermission> 
												<shiro:hasPermission name="product:productCategory:edit">
													<a href="#"
														onclick="openDialog('修改分类', '${ctx}/productManager/productCategory/form?id=${productCategory.id}','800px', '500px')"
														class="btn btn-success btn-xs"><i class="fa fa-edit"></i>
														修改</a>
												</shiro:hasPermission> 
												<shiro:hasPermission name="product:productCategory:del">
													<a href="${ctx}/productManager/productCategory/delete?id=${productCategory.id}"
														onclick="return confirmx('要删除该分类及所有子分类吗？', this.href)"
														class="btn btn-danger btn-xs"><i class="fa fa-trash"></i>
														删除</a>
												</shiro:hasPermission> <shiro:hasPermission name="product:productCategory:add">
													<a href="#"
														onclick="openDialog('添加下级分类', '${ctx}/productManager/productCategory/form?pid=${productCategory.id}&action=add','800px', '500px')"
														class="btn btn-primary btn-xs add-next-level"><i class="fa fa-plus"></i>
														添加下级分类</a>
												</shiro:hasPermission></td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</form>

				<!-- 分页代码 -->
				<table:page page="${page}"></table:page>
				<br /> <br />
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$("#treeTable").treeTable({expandLevel : 1,column:0}).show();
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").submit();
    	}
		function refresh(){//刷新
			
			window.location="${ctx}/productManager/productCategory/";
		}
		// 三级目录禁用添加下级目录
		var $nextLevel = $("#treeTable").find('tr[depth="3"]').find('.add-next-level');
		if($nextLevel.length > 0) {
			$nextLevel.remove();
		}
	</script>
</body>
</html>