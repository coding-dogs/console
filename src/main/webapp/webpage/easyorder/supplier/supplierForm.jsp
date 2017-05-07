<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供货商管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/easyorder/css/common.css">
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
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="supplier" action="${ctx}/supplier/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td  class="width-15 active"><label class="pull-right"><font color="red">*</font>供货商名称:</label></td>
		         	<td class="width-35" >
		         		<form:input path="name" htmlEscaoe="false" maxlength="50" class="form-control required"/>
		         	</td>
		         	<td  class="width-15 active"><label class="pull-right">行业简述:</label></td>
		         	<td  class="width-35" ><form:input path="description" htmlEscape="false" maxlength="50" class="form-control"/></td>
		     	</tr>
		     	<tr>
					<td  class="width-15 active"><label class="pull-right">备注:</label></td>
		         	<td class="width-35" >
		         		<form:textarea path="remarks" htmlEscaoe="false" maxlength="300" class="form-control"/>
		         	</td>
		     	</tr>
		     	<tr>
					<td  class="width-15 active"><label class="pull-right"><font color="red">*</font>分配管理员:</label></td>
		         	<td class="width-35">
		         		<form:hidden path="managerId"/>
		         		<form:input path="managerName" readonly="readonly" class="form-control required"/><button type="button" class="btn btn-primary btn-sm display-n" id="selectManager">选择供应商管理员</button>
		         	</td>
		     	</tr>
			</tbody>
		</table>
	</form:form>
	<script type="text/javascript">
		// 获取进入表单时的动作，添加供应商管理按钮在查看的时候不显示
		var action = "${action}";
		if(action !== "view") {
			$("#selectManager").removeClass("display-n").addClass("display-ib");
			// 点击选择供应商管理员按钮弹出窗体以供选择
			$("#selectManager").on("click", function() {
				openDialogWithCallback("选择供应商管理员", "${ctx}/sys/user/roleUserSelector?enname=GYS_ADMIN", "800px", "500px", "", function(index, layero) {
					var iframeWin = layero.find('iframe')[0];
					var body = top.layer.getChildFrame('body', index);
					var contentTable = body.find("#contentTable");
					// 获取选中的一行数据，并获取其ID和姓名进行传值和显示
					var managerChecked = contentTable.find("tbody").find(".i-checks:checked");
					if(managerChecked.length == 0) {
						top.layer.alert('请选择一位管理员!', {icon: 0, title:'警告'});
						return;
					} else if(managerChecked.length == 1) {
						// 获取所选用户ID
						$("#managerId").val(managerChecked.val());
						var parentTr = managerChecked.parents("tr");
						if(parentTr.length > 0) {
							// 获取所选用户名称
							var userName = parentTr.eq(0).find(".userName").text();
							$("#managerName").val(userName);
						}
						top.layer.close(index);
					}
				});
			});
		}
	</script>
</body>
</html>