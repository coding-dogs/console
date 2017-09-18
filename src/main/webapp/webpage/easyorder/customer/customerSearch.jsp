<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>客户查询</title>
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
		<h5>客户查询</h5>
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
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<p class="bg-warning mt-10 padding-5">添加前请先搜索客户，未搜索到的客户才可进行添加</p>
	<form:form id="searchForm" modelAttribute="customer" action="${ctx}/customerManager/customer/search" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>客户手机号</span>
				<form:input path="accountNo" htmlEscape="false" maxlength="50"  class=" form-control input-sm"/>
		 </div>	
		 <div class="form-group">
		 	<shiro:hasPermission name="customer:customer:list">
				<button type="submit"  class="btn btn-primary btn-rounded btn-outline btn-sm" ><i class="fa fa-refresh"></i> 搜索</button>
			</shiro:hasPermission>
			<c:if test="${(empty page or empty page.list) and not empty customer.accountNo  }">
				<shiro:hasPermission name="customer:customer:add">
					<a class="btn btn-primary btn-rounded btn-outline btn-sm" href="${ctx}/customerManager/customer/form?action=add"><i class="fa fa-plus"></i> 添加新客户</a>
				</shiro:hasPermission>
			</c:if>
		 </div>
	</form:form>
	<br/>
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
				<th>归属地区</th>
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
							<td>
								${customer.name}
							</td>
							<td>
								${customer.accountNo}
							</td>
							<td>
								${customer.mtCityCd}
							</td>
							<td>
								<fmt:formatDate value="${customer.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								${customer.remarks}
							</td>
							<td>
								<a class="btn btn-info btn-xs" onclick="addCustomer('${customer.id}')"><i class="fa fa-plus"></i> 添加该客户</a>
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
<script type="text/javascript">
	function addCustomer(id) {
		top.layer.confirm('您确定要添加此客户吗', {icon: 3, title: '系统提示'}, function(index) {
			$.ajax(
					{
						url: '${ctx}/customerManager/customer/async/save',
						type: 'POST',
						data: {id: id},
						dataType: 'JSON',
						success: function(data) {
							if(SUCCESS_CODE == data.code) {
								// 此处不做代码合并，留以应对后期可能出现的修改
								top.layer.alert(data.msg);
							} else {
								top.layer.alert(data.msg);
							}
						}
					}
				);
		});
		
	}
</script>
</body>
</html>