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
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>
					<c:if test="${customer.action eq 'add'}">
						新增客户信息
					</c:if>
					<c:if test="${customer.action eq 'edit'}">
						编辑客户信息
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
						<h2>基本信息</h2>
						<div class="form-group">
							<label for="accountNo" class="col-sm-2 control-label"><em class="required-tag">* </em>登录账号</label>
							<div class="col-sm-10">
								<input type="text" id="accountNo" name="accountNo" value="${customer.accountNo}" placeholder="请输入登录账号" maxlength="50" class="form-control required"/>
							</div>
						</div>
						<c:if test="${customer.action ne 'edit' and customer.action ne 'view'}">
							<div class="form-group">
								<label for="password" class="col-sm-2 control-label"><em class="required-tag">* </em>密码</label>
								<div class="col-sm-10">
									<input type="password" id="password" name="password" value="" placeholder="请输入密码" maxlength="50" class="form-control required"/>
								</div>
							</div>
						</c:if>
						<div class="form-group">
							<label for="mtCustomerStatusCd" class="col-sm-2 control-label"><em class="required-tag">* </em>账号状态</label>
							<div class="col-sm-10 easy-form-ichecks">
								<c:choose>
									<c:when test="${customer.action eq 'view'}">
										<span class="easy-form-span">${fns:getDictLabel(customer.mtCustomerStatusCd,"mtCustomerStatusCd", "-")}</span>
									</c:when>
									<c:when test="${customer.action eq 'edit'}">
										<form:radiobuttons path="mtCustomerStatusCd" items="${fns:getDictList('mtCustomerStatusCd')}" itemLabel="label" itemValue="value" class="required i-checks"/>
									</c:when>
									<c:when test="${customer.action eq 'add'}">
										<input type="hidden" id="mtCustomerStatusCd" name="mtCustomerStatusCd" maxlength="50" class="form-control required" value="ACTIVATION"/>
					         			<span class="easy-form-span">待激活</span>
									</c:when>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label"><em class="required-tag">* </em>客户名称</label>
							<div class="col-sm-10">
								<input type="text" id="name" name="name" value="${customer.name}" placeholder="请输入客户名称" maxlength="50" class="form-control required"/>
							</div>
						</div>
						<div class="form-group">
							<label for="customerNo" class="col-sm-2 control-label"><em class="required-tag">* </em>客户编号</label>
							<div class="col-sm-10">
								<input type="text" id="customerNo" name="customerNo" value="${customer.customerNo }" maxlength="50" class="form-control required"/>
							</div>
						</div>
						<div class="form-group">
							<label for="customerGroup" class="col-sm-2 control-label"><em class="required-tag">* </em>所属客户组</label>
							<div class="col-sm-10">
								<div id="customerGroup" data-text="" data-required="true" data-value="${customer.customerGroupId}" data-name='customerGroupId'></div>
							</div>
						</div>
						<div class="form-group">
							<label for="mtCityCd" class="col-sm-2 control-label"><em class="required-tag">* </em>归属地区</label>
							<div class="col-sm-10">
								<input type="text" id="mtCityCd" name="mtCityCd" value="${customer.mtCityCd}" maxlength="50" class="form-control required"/>
							</div>
						</div>
						<div class="form-group">
							<label for="remarks" class="col-sm-2 control-label">备注</label>
							<div class="col-sm-10">
								<textarea id="remarks" name="remarks" maxlength="300" class="form-control">${customer.remarks}</textarea>
							</div>
						</div>
					</div>
					
					<!-- 联系人信息 -->
					<div class="easy-order customer-contact">
						<h2>联系人</h2>
						<input type="hidden" id="contactId" name="contactId" value="${customer.contactId}"/>
						<div class="form-group">
							<label for="contactName" class="col-sm-2 control-label">联系人</label>
							<div class="col-sm-10">
								<input type="text" id="contactName" name="contactName" value="${customer.contactName}" placeholder="请输入客户联系人" maxlength="50" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<label for="contactPhone" class="col-sm-2 control-label">电话号码</label>
							<div class="col-sm-10">
								<input type="text" id="contactPhone" name="contactPhone" value="${customer.contactPhone}" placeholder="请输入联系人电话号码" maxlength="50" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<label for="contactEmail" class="col-sm-2 control-label">电子邮箱</label>
							<div class="col-sm-10">
								<input type="text" id="contactEmail" name="contactEmail" placeholder="请输入电子邮箱" maxlength="50" class="form-control" value="${customer.contactEmail}"/>
							</div>
						</div>
						<div class="form-group">
							<label for="contactAddress" class="col-sm-2 control-label">联系地址</label>
							<div class="col-sm-10">
								<input type="text" id="contactAddress" name="contactAddress" placeholder="详细地址，如四川省成都市天府三街12099号" value="${customer.contactAddress}" maxlength="150" class="form-control"/>
							</div>
						</div>
					</div>
					<div class="text-center btns">
						<c:if test="${customer.action eq 'add' or customer.action eq 'edit' }">
							<button type="submit" class="btn btn-success">提交</button>			
						</c:if>
						<button type="button" class="btn btn-white" onclick="window.location.href='${ctx}/customerManager/customer/'">返回</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	
	
	
	<script type="text/javascript" src="${ctxStatic}/easy-selector/easy-selector.js"></script>
	<script type="text/javascript">
		
		function getCustomerGroups() {
			var items = [];
			$.ajax({
				url : "${ctx}/customerManager/customerGroup/all",
				async : false,
				type : "GET",
				dataType : "json",
				success : function(data) {
					var _item = [];
					if(SUCCESS_CODE === data.code) {
						if(data.result && data.result.length > 0) {
							$.each(data.result, function(index, group) {
								var item = {};
								item.value = group.id;
								item.text = group.name;
								items.push(item);
							});
						}
					}
				}
			});	
			return items;
		}
		var items = getCustomerGroups();
		// 构建客户组选择器
		$("#customerGroup").easySelector({
			maxHeight : 250,
			hasMore : true,
			moreText : "<span style='text-weight:bold'> + </span> 新增客户组",
			items : items,
			moreCallback : function() {
				openDialogWithCallback('新增客户组', '${ctx}/customerManager/customerGroup/form','800px', '500px', null, function(index, layero) {
					var body = top.layer.getChildFrame('body', index);
					var inputForm = body.find("#inputForm");
					var name = inputForm.find("#name").val();
					var remarks = inputForm.find("#remarks").val();
					// 保存客户组
					$.ajax({
						url : "${ctx}/customerManager/customerGroup/save",
						type : "POST",
						data : {"name" : name, "remarks" : remarks},
						dataType : "JSON",
						success : function(data) {
							if(SUCCESS_CODE === data.code) {
								// 更新选择项
								$("#customerGroup").easySelector("setOptions", "items", getCustomerGroups());
								// 选择当前值
								$("#customerGroup").easySelector("select", data.result);
								top.layer.close(index);
							}
						},
						error : function() {
							top.layer.alert("新增失败");
							top.layer.close(index);
						}
					});
					
				});
			}
		});
	</script>
</body>
</html>