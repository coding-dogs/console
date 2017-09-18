<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/easy-tabs/easy-tabs.css">
	<link rel="stylesheet" href="${ctxStatic}/easy-selector/easy-selector.css">
	<style type="text/css">
		.ibox-content {
			padding: 15px 0;
		}
		
		.form-horizontal {
			margin: 0;
		}
		
		.srl-ul li{
			float: left;
			list-style: none;
			margin: 10px 15px 10px 0;
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			WinMove();
	        //外部js调用
	        laydate({
	            elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });

	       
	    })
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>信息统计</h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
	    
		    <div id="ibox-content" class="ibox-content">
		    	<ul id="tabs-content">
		    		<li>
		    			<a href="#orderPreview">订单数据</a>
		    		</li>
		    	</ul>
		    
		    	<div id="orderPreview">
		    		<form class="form-inline">
		    			<div class="form-group">
			    			<span>日期：</span>
			    			<input id="beginDate" name="beginDate" type="text" class="form-control laydate-icon layer-date input-sm"/>
			    			<label>&nbsp;至&nbsp;</label>
			    			<input id="endDate" name="endDate" type="text" class="form-control laydate-icon layer-date input-sm"/>
			    		</div>
			    		<div class="form-group">
			    			<span>客户：</span>
			    			<div class="display-ib">
			    				<div id="customerSelector" date-text="请选择客户" data-value="" data-name="customerId"></div>
			    			</div>
			    		</div>
			    		<div class="form-group">
			    			<a class="btn btn-primary" href="javascript:void(0);" onclick="execQuery();">查询</a>
			    		</div>
		    		</form>
		    		<div class="search-result clearfix">
		    			<div class="sr-left pull-left">
		    				<ul class="clearfix srl-ul">
		    					<li>客户数：<label class="statistics-text" id="customerCountLabel">0</label></li>
		    					<li>订单数：<label class="statistics-text" id="orderCountLabel">0</label></li>
		    					<li>订单金额：<label class="statistics-text" id="orderTotalPriceLabel">0</label></li>
		    					
		    				</ul>
		    			</div>
		    			<div class="sr-right pull-right">
		    				<span>统计方式：</span>
		    				<input type="radio" class="i-checks" name="countMode" checked/> 日
		    				<input type="radio" class="i-checks" name="countMode"/> 月
		    			</div>
		    		</div>
		    		
		    		<div id="container"></div>
		    		
		    		<!-- 统计数据 -->
		    		<table id="statisticsTable" class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
					
					</table>
		    	</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctxStatic}/easy-selector/easy-selector.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-tabs/easy-tabs.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easyorder/js/common/easy-request.js"></script>
	<script type="text/javascript" src="${ctxStatic}/highcharts/code/highcharts.js"></script>
	<script type="text/javascript" src="${ctxStatic}/highcharts/code/modules/exporting.js"></script>
	<script type="text/javascript" src="${ctxStatic}/highcharts/plugins/highcharts-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easyorder/js/common/easy-charts.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-page-records/easy-page-records.js"></script>
	<script type="text/javascript">
		var requestData = {
			'pageNo' : 1,
			'pageSize' : 30
		};
		
		function getArrayFormData(data, key) {
			var array = [];
			if(data && data.length > 0) {
				$.each(data, function(i, item) {
					if(item[key]) {
						array.push(item[key]);
					}
				});
			}
			return array;
		}
		
		function execQuery(init) {
			getStatistics();
			if(init != false) {
				var rd = getRequestData();
				// 重置查询条件
				table.easyPageRecords('setOptions', 'requestData', rd);
				// 执行请求
				table.easyPageRecords('request');
			}
		}
		
		function getRequestData() {
			var customerId = $('#customerSelector').find('input[name="customerId"]').val();
			requestData['customerId'] = customerId;
			return requestData;
		}
		
		var table = $("#statisticsTable").easyPageRecords({
			titles: [
				{
					name : 'groupAccordance',
					title: '月/日'
				},
				{
					name: 'orderCount',
					title: '订单数'
				},
				{
					name : 'orderTotalPrice',
					title : '订单金额'
				}
			],
			listProp: 'list',
			requestData : getRequestData(),
			url: '${ctx}/orderManager/order/statisticsDetail',
			receiveData: function(data) {
				var statisticsData = data.result.list;
				easyorder.charts.lineColumnar({
					ele: '#container',
					title: '订单数据报表',
					xAxisData: getArrayFormData(statisticsData, 'groupAccordance'),
			        yAxisSetting: [
			        	{
			        		text: '订单金额',
			        		suffix: '元'
			        	}, 
			        	{
			        		text: '订单数量',
			        		suffix: '单'
			        	}
			        ],
			        series: [
			        	{
			        		name: '订单金额',
			        		suffix: '元',
			        		data: getArrayFormData(statisticsData, 'orderTotalPrice')
			        	},
			        	{
			        		name: '订单数量',
			        		suffix: '单',
			        		data: getArrayFormData(statisticsData, 'orderCount'),
			        		yAxis: 1
			        	}
			        ]
				});
			}
		});
		
		execQuery(false);
		
		function getStatistics() {
			var customerId = $('#customerSelector').find('input[name="customerId"]').val();
			easyorder.get("orderManager/order/statistics", {customerId: customerId}, false, function(data) {
				if(data.code = SUCCESS_CODE) {
					var customerCount = 0, orderCount = 0, orderTotalPrice = 0;
					var result = data.result;
					if(result) {
						customerCount = result.customerCount ? result.customerCount : 0;
						orderCount = result.orderCount ? result.orderCount : 0;
						orderTotalPrice = result.orderTotalPrice ? result.orderTotalPrice : 0;
					}
					$("#customerCountLabel").text(customerCount);
					$("#orderCountLabel").text(orderCount);
					$("#orderTotalPriceLabel").text(orderTotalPrice);
				} else {
					top.layer.alert(data.msg);
				}
			}, function() {
				
			});
		}
	
		// 页签组件的使用
		$('#tabs-content').easyTabs({
			selectedId: '#orderPreview',			// 指定要选中"小页面"的id属性
			targetElement: '#ibox-content'			// 页签元素们的父元素
		});
		
		var customers = [];
		function getCustomers() {
			easyorder.get("customerManager/customer/async/listAll", {}, true, function(data) {
				if(data.code = SUCCESS_CODE) {
					var result = data.result;
					if(result && result.length != 0) {
						$.each(result, function(index, resultItem) {
							var customer = {};
							customer.value = resultItem.id;
							customer.text = resultItem.name;
							customers.push(customer);
						});
					}
					
					$("#customerSelector").easySelector({
						type: 'select',
						maxHeight: 250,
						width: 150,
						defaults: {
							value : '',
							text : '请选择客户',
							selected: true
						},
						items : customers
					});
				} else {
					top.layer.alert(data.msg);
				}
			}, function() {
				
			});
		}
		
		getCustomers();
	</script>
</body>
</html>