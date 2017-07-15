<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>商品分类管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	$(document).ready(
			function() {
				validateForm = $("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

			});
</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="productCategory"
		action="${ctx}/productManager/productCategory/save" method="post"
		class="form-horizontal">
		<c:choose>
			<c:when test="${not empty productCategory.id}">
				<form:hidden path="id" />
			</c:when>
			<c:otherwise>
				<input type="hidden" name="id" id="id" />
			</c:otherwise>
		</c:choose>

		<sys:message content="${message}" />
		<table
			class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><em class="required-tag">* </em>上级分类:</label></td>
					<td class="width-35"><sys:easyTreeSelect id="p" name="pid"
							value="${productCategory.parent.id}" labelName="parent.name"
							labelValue="${productCategory.parent.name}" title="父级分类名称"
							url="/productManager/productCategory/tree"
							cssClass="form-control required" allowMaxLevel="2"/></td>
					<td class="width-15" class="active"><label class="pull-right"><em class="required-tag">* </em>分类名称:</label></td>
					<td class="width-35"><form:input path="name"
							htmlEscape="false" maxlength="50" class="form-control required" /></td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><em class="required-tag">* </em>排序值:</label></td>
					<td class="width-35">
						<form:input path="sort" class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">备注:</label></td>
					<td class="width-35"><form:textarea path="remarks"
							htmlEscape="false" rows="3" maxlength="300" class="form-control " /></td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>