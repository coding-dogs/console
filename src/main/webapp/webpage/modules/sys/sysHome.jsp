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
		.search-result {
			margin: 15px 0;
		}
		
		.form-inline {
			margin-top: 10px;
		}
		
		
	</style>
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
			    			<input id="beginDate" name="beginDate" type="text" readonly class="form-control laydate-icon layer-date input-sm"/>
			    			<label>&nbsp;至&nbsp;</label>
			    			<input id="endDate" name="endDate" type="text" readonly class="form-control laydate-icon layer-date input-sm"/>
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
		    			<div class="sr-right pull-right" id="statisticsMode" data-init="true">
		    				<span>统计方式：</span>
		    				<label class="statistics-item"><input type="radio" class="i-checks" name="countMode" value="day"/> 日</label>
		    				<label class="statistics-item"><input type="radio" class="i-checks" name="countMode" value="month"/> 月</label>
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
		WinMove();	
		
		function fun_date(date, diff, split){
			if(!split) {
				split = "-";
			}
	        var date2 = new Date(date);
	        date2.setDate(date.getDate() + diff);
	        return getFormatDate(date2, split);
	    }
		function fun_dates(date, diff, split) {
			if(!split) {
				split = "-";
			}
			var dates = date.split(split);
			var date2 = new Date(dates[0], parseInt(dates[1]) - 1, dates[2]);
	        date2.setDate(parseInt(dates[2]) + diff);
	        return getFormatDate(date2, split);
		}
		
		function getFormatDate(date, split) {
			if(!split) {
				split = "-";
			}
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var day = date.getDate();
			return year + split + digit(month) + split + digit(day);
		}
		
		function digit(num, length) {
			var str = '';
		    num = String(num);
		    length = length || 2;
		    for(var i = num.length; i < length; i++){
		      str += '0';
		    }
		    return num < Math.pow(10, length) ? str + (num|0) : num;
		} 
		
		
		var begin = {
			elem: '#beginDate',
            event: 'focus',
            choose: function(dates) {
            	end.min = dates;
            	var mode = $("input[name=countMode]:checked").val();
            	var diff = (mode == 'day') ? 30 : 365;
            	end.max = fun_dates(dates, diff);
            }
		};
		
		var end = {
            elem: '#endDate',
            event: 'focus',
            choose: function(dates) {
            	
            }
        };
		
		//外部js调用
        laydate(begin);
        laydate(end);
        
        
        $('#statisticsMode').delegate($('input:radio[name="countMode"]'),'ifChecked',function(e) {
        	var target = $(e.target);
        	var mode = target.val();
        	var diff = 0, bigDiff = 0;
        	if(mode == 'day') {
                diff = -7;
                bigDiff = 30;
        	} else if(mode == 'month') {
               	diff = -30;
               	bigDiff = 365;
        	}
        	var now = new Date();
            var beginDate = fun_date(now, diff);
            $("#beginDate").val(beginDate);
            end.min = beginDate;
            end.max = fun_dates(beginDate, bigDiff);
            $("#endDate").val(getFormatDate(now));
            $("#customerSelector").easySelector("select", '');
            execQuery();
        });
        
        $("input[name=countMode]").eq(0).iCheck('check');
        
		
		var requestData = {
			'pageNo' : 1,
			'pageSize' : 10
		};
		
		/**
		 * 从数据中抽取指定的属性拼装成一个集合
		 */
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
		
		/**
		 * 执行查询
		 * init 只是是否初始化时调用，初始化时无需重新请求
		 */
		function execQuery() {
			getStatistics();
			var init = $("#statisticsMode").attr('data-init');
			if(init != 'true') {
				var rd = getRequestData();
				// 重置查询条件
				table.easyPageRecords('setOptions', 'requestData', rd);
				// 执行请求
				table.easyPageRecords('request');
			} else {
				$("#statisticsMode").attr('data-init', 'false');
			}
		}
		
		/**
		 * 组装查询时需要的参数
		 */
		function getRequestData() {
			var customerId = $('#customerSelector').find('input[name="customerId"]').val();
			requestData['customerId'] = customerId;
			
			var beginDate = $("#beginDate").val();
			var endDate = $("#endDate").val();
			requestData['beginDate'] = beginDate;
			requestData['endDate'] = endDate;
			
			var mode = $("#statisticsMode").find("input:radio[name=countMode]:checked").val();
			requestData['mode'] = mode;
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
			listProp: 'list',							// 分页数据在异步返回结果中的属性名
			type: 'sequence',							// 开启序号列
			sequenceText: '',							// 序号列表头名称
			sizeArray: [10, 20, 30],					// 可选每页显示数列表，必须是数组
			requestData : getRequestData(),				// 请求参数
			url: '${ctx}/orderManager/order/statisticsDetail',		// 数据请求URL 
			receiveData: function(data) {							// 请求成功的数据处理函数
				var statisticsData = data.result.list;
				easyorder.charts.lineColumnar({
					ele: '#container',
					title: '    ',
					xAxisData: getArrayFormData(statisticsData, 'groupAccordance'),
			        yAxisSetting: [
			        	{
			        		text: '订单金额',
			        		suffix: '元',
			        		y: -20,
			        		x: -10
			        	}, 
			        	{
			        		text: '订单数量',
			        		suffix: '单',
			        		y: -20
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
		
		/**
		 * 获取订单统计数据
		 */
		function getStatistics() {
			var customerId = $('#customerSelector').find('input[name="customerId"]').val();
			var beginDate = $("#beginDate").val();
			var endDate = $("#endDate").val();
			var mode = $("#statisticsMode").find("input:radio[name=countMode]:checked").val();
			var data = {
				customerId: customerId,
				beginDate: beginDate,
				endDate: endDate,
				mode: mode
			};
			easyorder.get("orderManager/order/statistics", data, false, function(data) {
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
	
		// 页签组件的使用，一次性加载
		$('#tabs-content').easyTabs({
			selectedId: '#orderPreview',			// 指定要选中"小页面"的id属性
			targetElement: '#ibox-content'			// 页签元素们的父元素
		});
		
		// 获取客户列表，作为客户下拉列表
		var customers = [], customerSelector;
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
					
					customerSelector = $("#customerSelector").easySelector({
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