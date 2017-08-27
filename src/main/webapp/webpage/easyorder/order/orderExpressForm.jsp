<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>客户管理</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/easy-selector/easy-selector.css">
<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				ignore: '',
				rules: {
				},
				messages: {
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else if(element.parents('.easy-selector').length > 0){
						element.parents('.easy-selector').addClass('error');
						error.insertAfter(element.parents('.easy-selector'));
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="orderExpress"
		action="${ctx}/orderManager/orderExpress/save" method="post"
		class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="orderId" value="${orderExpress.orderId}"/>
		<sys:message content="${message}" />
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><em
							class="required-tag">* </em>物流公司:</label></td>
					<td class="width-35"><form:input path="companyName"
							htmlEscape="false" maxlength="100" class="form-control required" /></td>
					<td class="width-15 active"><label class="pull-right">物流单号:</label></td>
					<td class="width-35"><form:textarea path="expressNo"
							htmlEscape="false" rows="3" maxlength="100" class="form-control " /></td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>