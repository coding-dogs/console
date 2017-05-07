<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>分配角色</title>
<meta name="decorator" content="default" />
</head>
<body>

	<div class="wrapper wrapper-content">
	<table id="contentTable"
		class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th>选取</th>
				<th>登录名</th>
				<th>姓名</th>
				<th>电话</th>
				<th>手机</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="status">
				<tr>
					<td><input type="radio" value="${user.id}" class="i-checks <c:if test="${status.index == 0}">checked</c:if>" /></td>
					<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
					<td class="userName">${user.name}</td>
					<td>${user.phone}</td>
					<td>${user.mobile}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
</body>
</html>
