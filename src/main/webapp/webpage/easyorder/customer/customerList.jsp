<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户管理</title>
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
		<h5>客户列表 </h5>
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
	<form:form id="searchForm" modelAttribute="customer" action="${ctx}/customerManager/customer/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>客户名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>	
		 <div class="form-group">
			<span>登录账号：</span>
				<form:input path="accountNo" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="customer:customer:add">
				<table:easyAddRow url="${ctx}/customerManager/customer/search?action=add" title="客户"></table:easyAddRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="customer:customer:del">
				<table:easyDelRow url="${ctx}/customerManager/customer/deleteAll" id="contentTable" label="移除"></table:easyDelRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="customer:customer:import">
				<table:importExcel url="${ctx}/customerManager/customer/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="customer:customer:export">
	       		<table:exportExcel url="${ctx}/customerManager/customer/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th class="sort-column customerNo">客户编号</th>
				<th class="sort-column name">客户名称</th>
				<th class="sort-column accountNo">登录账号</th>
				<th>所属客户组</th>
				<th>归属地区</th>
				<th>客户状态</th>
				<th class="sort-column updateDate">更新时间</th>
				<th class="sort-column remarks">备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty page or empty page.list }">
					<tr>
						<td colspan="10" align="center">暂无客户信息</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${page.list}" var="customer">
						<tr>
							<td> <input type="checkbox" id="${customer.id}" class="i-checks"></td>
							<td>
								${customer.customerNo}
							</td>
							<td><a  href="${ctx}/customerManager/customer/form?id=${customer.id}&action=view">
								${customer.name}
							</a></td>
							<td>
								${customer.accountNo}
							</td>
							<td>
								<c:choose>
									<c:when test="${not empty customer.customerGroupName }">
										${customer.customerGroupName}
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
								
							</td>
							<td>
								${customer.mtCityCd}
							</td>
							<td>
								${fns:getDictLabel(customer.mtCustomerStatusCd, "mtCustomerStatusCd", "-") }
							</td>
							<td>
								<fmt:formatDate value="${customer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${customer.remarks}
							</td>
							<td>
								<shiro:hasPermission name="customer:customer:view">
									<a href="${ctx}/customerManager/customer/form?id=${customer.id}&action=view" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
								</shiro:hasPermission>
								<shiro:hasPermission name="customer:customer:edit">
									<a class="btn btn-warning btn-xs" href="javascript:void(0);" onclick="giveGroup('${customer.id}')"><i class="fa fa-refresh"> 分配客户组</i></a>
									<a class="btn btn-primary btn-xs" href="javascript:void(0);" onclick="updateStatus('${customer.id}')"><i class="fa fa-edit"> 修改状态</i></a>
								</shiro:hasPermission>
			    				<shiro:hasPermission name="customer:customer:del">
									<a href="${ctx}/customerManager/customer/delete?id=${customer.id}" onclick="return confirmx('确认要移除该客户吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 移除</a>
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
<script type="text/javascript" src="${ctxStatic}/easy-page-records/easy-page-records.js"></script>
<script type="text/javascript">
	function giveGroup(customerId) {
		openDialogWithCallback('选择客户组', ctx + '/customerManager/customerGroup/selector?type=radio', '800px', '600px', null, function(index, layero) {
			var body = top.layer.getChildFrame('body', index);
			var iframeWin = layero.find('iframe')[0];
	     	var $table = iframeWin.contentWindow.table;
	     	var records = $table.easyPageRecords('getRecords');
	     	if(!records || records.length == 0) {
	     		top.layer.close(index);
	     		return;
	     	}
	     	var record = records[0];
	     	$.ajax({
	     		url: '${ctx}/customerManager/customerGroup/allocate',
	     		data: {customerGroupId: record.id, customerId: customerId},
	     		type: 'POST',
	     		dataType: 'JSON',
	     		success: function(data) {
	     			if(SUCCESS_CODE == data.code) {
	     				top.layer.close(index);
	     				location.href = '${ctx}/customerManager/customer/';
	     				top.layer.alert('分配成功');
	     			} else {
	     				top.layer.close(index);
	     				top.layer.alert(data.msg);
	     			}
	     		}
	     	});
		});
	}
	
	function updateStatus(customerId) {
		openDialogWithCallback('修改状态', ctx + '/customerManager/customer/toUpdateStatus?id=' + customerId, '500px', '300px', null, function(index, layero) {
			var body = top.layer.getChildFrame('body', index);
			var iframeWin = layero.find('iframe')[0];
	     	var doc = iframeWin.contentWindow.document;
	     	var customerId = $(doc).find('#customerId').val();
	     	var mtCustomerStatusCd = $(doc).find('input[name=mtCustomerStatusCd]:checked').val();
	     	$.ajax({
	     		url: "${ctx}/customerManager/customer/updateStatus",
	     		data: {id: customerId, mtCustomerStatusCd: mtCustomerStatusCd},
	     		type: "POST",
	     		dataType: "JSON",
	     		success: function(data) {
	     			if(data.code == SUCCESS_CODE) {
	     				top.layer.close(index);
	     				top.layer.alert(data.msg);
	     				window.location.href = "${ctx}/customerManager/customer";
	     			} else {
	     				top.layer.close(index);
	     				top.layer.alert(data.msg);
	     			}
	     		}
	     	});
		});
	}
</script>
</body>
</html>