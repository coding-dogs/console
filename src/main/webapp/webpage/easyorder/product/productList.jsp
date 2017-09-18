<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/easy-selector/easy-selector.css">
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>商品列表 </h5>
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
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/productManager/product/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<label>商品名称：</label>
			<form:input path="name" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		</div>
		<div class="form-group">
			<label>商品标题：</label>
			<form:input path="title" htmlEscape="false" maxlength="100"  class=" form-control input-sm"/>
		</div>
		<div class="form-group">
			<label>商品编号：</label>
			<form:input path="productNo" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		</div>
		<div class="form-group clearfix">
			<label>商品分类：</label>
			<div class="display-ib">
				<div id="productCategory" data-text="请选择商品分类" data-value="${product.productCategoryId}" data-name='productCategoryId'></div>
			</div>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="product:product:add">
				<table:easyAddRow url="${ctx}/productManager/product/form?action=add" title="商品"></table:easyAddRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:product:edit">
			    <table:easyEditRow url="${ctx}/productManager/product/form?action=edit" title="商品" id="contentTable"></table:easyEditRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:product:del">
				<table:easyDelRow url="${ctx}/productManager/product/deleteAll" id="contentTable"></table:easyDelRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:product:import">
				<table:importExcel url="${ctx}/productManager/product/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="product:product:export">
	       		<table:exportExcel url="${ctx}/productManager/product/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th>商品主图</th>
				<th  class="sort-column name">商品名称</th>
				<th  class="sort-column title">商品标题</th>
				<th  class="sort-column productNo">商品编号</th>
				<th  class="sort-column modelNumber">型号</th>
				<th  class="sort-column mtProductUpdownCd">商品上下架标识</th>
				<th  class="sort-column orderPrice">订货价格</th>
				<th  class="sort-column buyPrice">进货价格</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty page or empty page.list }">
					<tr>
						<td colspan="12" align="center">暂无商品信息</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${page.list}" var="product">
						<tr>
							<td> <input type="checkbox" id="${product.id}" class="i-checks"></td>
							<td>
								<c:choose>
									<c:when test="${not empty product.coverUrl}">
										<img src="${product.coverUrl}" width="40" height="40">
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</td>
							<td><a href="javascript:void(0);" onclick="openTab('${ctx}/productManager/product/form?action=view&id=${product.id}', '${product.name}', false)">
								${product.name}
							</a></td>
							<td>
								<a href="javascript:void(0);" onclick="openTab('${ctx}/productManager/product/form?action=view&id=${product.id}', '${product.name}', false)">
									${product.title}
								</a>
							</td>
							<td>
								${product.productNo}
							</td>
							<td>
								${product.modelNumber}
							</td>
							<td>
								${fns:getDictLabel(product.mtProductUpdownCd, 'mtProductUpdownCd', '-') }
							</td>
							<td>
								${product.orderPrice}
							</td>
							<td>
								${product.buyPrice}
							</td>
							<td>
								<fmt:formatDate value="${product.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<shiro:hasPermission name="product:product:view">
									<a href="javascript:void(0);" onclick="openTab('${ctx}/productManager/product/form?action=view&id=${product.id}', '${product.name}', false)" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="product:product:edit">
			    					<a href="${ctx}/productManager/product/form?action=edit&id=${product.id}" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
			    				</shiro:hasPermission>
			    				<shiro:hasPermission name="product:product:del">
									<a href="${ctx}/productManager/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
	<script type="text/javascript" src="${ctxStatic}/common/contabs.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-selector/easy-selector.js"></script>
	<script type="text/javascript">
	/**
	 * 获取商品分类数据
	 */
	function getProductCategory() {
		var items = [];
		$.ajax({
			url : ctx + "/productManager/productCategory/tree",
			async : false,
			type : "GET",
			data : {"hasRoot" : "N"},
			dataType : "json",
			success : function(data) {
				var _item = [];
				if(SUCCESS_CODE === data.code) {
					if(data.result && data.result.length > 0) {
						$.each(data.result, function(index, cate) {
							var item = {};
							item.value = cate.id;
							item.text = cate.name;
							item.pvalue = cate.pId;
							items.push(item);
						});
					}
				}
			}
		});	
		return items;
	}
	var items = getProductCategory();
	// 构建商品分类选择器
	$("#productCategory").easySelector({
		type : 'tree',
		maxHeight : 250,
		width: 200,
		defaults: {
			value : '',
			text : '请选择商品分类',
			selected: true
		},
		items : items
	});
	</script>
</body>
</html>