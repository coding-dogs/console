<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>客户选择器</title>
<meta name="decorator" content="default" />
<link rel="stylesheet"
	href="${ctxStatic}/easy-selector/easy-selector.css">
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
	
	<div class="easy-order customer-list col-sm-12 col-md-12 col-xs-12">
		<div class="form-inline">
			<div class="form-group first">
				<span class="">客户名称：</span>
				<input id="name" name="name" maxlength="50"
					class="form-control input-sm" />
			</div>
			<div class="form-group">
				<span>客户地区：</span>
				<input id="mtCityCd" name="mtCityCd" maxlength="50"
					class="form-control input-sm" />
			</div>
			<div class="form-group">
				<span>客户组：</span>
				<div class="max-width-300" id="customerGroup" data-text=""
					data-required="true" data-value="${customer.customerGroupId}"
					data-name='customerGroupId'></div>
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-rounded btn-outline btn-sm "
					onclick="execQuery();">
					<i class="fa fa-search"></i> 查询
				</button>
			</div>
		</div>
		
		<table id="customerTable"
		class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		
		</table>
	</div>


	<script type="text/javascript" src="${ctxStatic}/easy-selector/easy-selector.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-page-records/easy-page-records.js"></script>
	<script type="text/javascript" src="${ctxStatic}/common/contabs.js"></script>

	<script type="text/javascript">
		var requestData = {
			'pageNo' : 1,
			'pageSize' : 5
		};
		function execQuery() {
			var rd = getRequestData();
			// 重置查询条件
			table.easyPageRecords('setOptions', 'requestData', rd);
			// 执行请求
			table.easyPageRecords('request');
		}
		var disabledIds = [];
		var customerIds = $(getActiveIframe()[0].contentWindow.document).find('#customerIds').val();
		if(customerIds) {
			disabledIds = customerIds.split(',');
		}
		
		// 页面选择元素记录组件
		var table = $('#customerTable').easyPageRecords({
			titles: [
				{
					name : 'name',
					title: '客户名称'
				},
				{
					name: 'customerNo',
					title: '客户编号'
				},
				{
					name : 'customerGroupName',
					title : '客户组'
				},
				{
					name : 'mtCityCd',
					title : '归属地区'
				}
			],
			listProp: 'list',
			requestData : getRequestData(),
			type: '${type}',
			url: '${ctx}/customerManager/customer/async/list',
			disabled: disabledIds
		});
		
		function getRequestData() {
			var name = $('#name').val();
			var mtCityCd = $('#mtCityCd').val();
			var customerGroupId = $('#customerGroup').find('input[name="customerGroupId"]').val();
			requestData['name'] = name;
			requestData['mtCityCd'] = mtCityCd;
			requestData['customerGroupId'] = customerGroupId;
			return requestData;
		}
		
		var records = [];

		function getCustomerGroups() {
			var items = [];
			$.ajax({
				url : "${ctx}/customerManager/customerGroup/all",
				async : false,
				type : "GET",
				dataType : "json",
				success : function(data) {
					var _item = [];
					if (SUCCESS_CODE === data.code) {
						if (data.result && data.result.length > 0) {
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
			width: 200,
			items : items,
			defaults: {
				text: '请选择客户组',
				value: '',
				selected: true
			}
		});
	</script>
</body>
</html>