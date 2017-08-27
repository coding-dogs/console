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
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>
					<c:if test="${customer.action eq 'add'}">
						新增订货单
					</c:if>
					<c:if test="${customer.action eq 'edit'}">
						编辑订货单
					</c:if>
				</h5>
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
				<form:form id="inputForm" modelAttribute="customer"
					action="${ctx}/customerManager/customer/save" method="post"
					class="form-horizontal">
					<input type="hidden" id="id" name="id" value="${customer.id}"/>
					<sys:message content="${message}" />
					<!-- 客户基本信息 -->
					<div class="easy-order customer-base-info">
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>