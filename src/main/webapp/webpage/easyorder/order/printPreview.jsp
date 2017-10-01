<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>订货单打印预览页</title>
<meta name="decorator" content="default" />
<link rel="stylesheet"
	href="${ctxStatic}/easy-selector/easy-selector.css">
<style type="text/css">
	.print-preview-page {
		padding: 15px;
		font-size: 12px;
	}
	
	.print-preview-detail {
		margin-bottom: 10px;
	}
	
	.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td,
		.table>tbody>tr>td, .table>tfoot>tr>td {
		border-top: 1px solid #e7eaec;
	}
	
	.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th,
		.table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td,
		.table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td {
		border: 1px solid #e7e7e7;
	}
	.table-bordered {
	    border: 1px solid #EBEBEB;
	}
	
	h3 {
		font-size: 24px;
	}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>订货单打印预览</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a> <a class="dropdown-toggle" data-toggle="dropdown" href="#"> <i
						class="fa fa-wrench"></i>
					</a> <a class="close-link"> <i class="fa fa-times"></i>
					</a>
				</div>
			</div>

			<div class="ibox-content clearfix">
				<div class="row">
					<div class="print-preview-page" id="print-preview">
						<h3 class="text-center">${supplier.name}发货单</h3>
						<div class="print-preview-detail">
							<div class="clearfix">
								<span class="pull-left">收货单位：${order.customerStoreName}</span> <span
									class="pull-right">打印日期：${fns:getDate("yyyy-MM-dd") }</span>
							</div>
						</div>
						<div class="print-preview-table">
							<table class="table table-bordered">
								<tr>
									<td>供货商编号</td>
									<td>${supplier.supplierNo}</td>
									<td>订货单号</td>
									<td colspan="2">${order.orderNo}</td>
									<td>联系方式</td>
									<td colspan="2">${order.orderShippingAddress.shippingName}
										${order.orderShippingAddress.shippingPhone}</td>
								</tr>
								<tr>
									<td>备注信息</td>
									<td colspan="7">${order.remarks}</td>
								</tr>
								<tr>
									<td align="center">序号</td>
									<td align="center">商品编号</td>
									<td align="center">条码</td>
									<td align="center">商品名称</td>
									<td align="center">单位</td>
									<td align="center">数量</td>
									<td align="center">单价</td>
									<td align="center">总金额</td>
								</tr>
								<c:forEach items="${order.orderItems}" var="orderItem"
									varStatus="status">
									<tr>
										<td align="center">${status.index + 1}</td>
										<td align="center">${orderItem.productNo}</td>
										<td align="center">${orderItem.barCode}</td>
										<td align="center">${orderItem.productTitle}</td>
										<td align="center">${orderItem.unit}</td>
										<td align="right">${orderItem.totalCount}</td>
										<td align="right">${orderItem.price}</td>
										<td align="right">${orderItem.totalPrice}</td>
									</tr>
								</c:forEach>
								<tr>
									<td>总计</td>
									<td colspan="4"></td>
									<td align="right">${order.totalCount}</td>
									<td colspan="2" align="right">${order.totalPrice}</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="btns mt-10">
					<button type="button" class="btn btn-primary" onclick="print();">确认打印</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${ctxStatic}/jquery/jQuery.print.js"></script>
	<script type="text/javascript">
		function changeStatus(val) {
			$("#mtOrderStatusCd").val(val);
		}
		
		function print() {   
		    $('#print-preview').print();
		}   
		
		function shipping(){
			/* openDialogWithCallback('填写发货单物流信息', '${ctx}/orderManager/orderExpress/form?orderId=${order.id}', '800px', '500px', '', function(index, layero){
				$("#mtOrderStatusCd").val('PENDING_RECEIVE');
				$('#statusForm').submit();
			}); */
			top.layer.open({
			    type: 1,  
			    area: ['800px', '500px'],
			    title: '填写发货单物流信息',
		        maxmin: true, //开启最大化最小化按钮
			    content: $('#html').html() ,
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	var $layer = $(layero);
			    	var companyName = $layer.find("#companyName").val();
			    	var expressNo = $layer.find('#expressNo').val();
			    	if(!companyName) {
			    		top.layer.alert('请输入物流公司名称');
			    		$layer.find('#companyName').focus();
			    		return;
			    	}
			    	if(!expressNo) {
			    		top.layer.alert('请输入物流单号');
			    		$layer.find('#expressNo').focus();
			    		return;
			    	}
			    	$.ajax({
			    		url: '${ctx}/orderManager/orderExpress/save',
			    		type: "POST",
			    		data: {orderId: '${order.id}', companyName: companyName, expressNo: expressNo},
			    		dataType: 'JSON',
			    		success: function(data) {
			    			if(SUCCESS_CODE == data.code) {
			    				$("#mtOrderStatusCd").val('PENDING_RECEIVE');
			    				$('#statusForm').submit();
			    				top.layer.close(index);
			    			}
			    		},
			    		error: function() {
			    			
			    		}
			    	});
			    },
			    cancel: function(index){ 
			    	
			    }
			});
		}
	</script>
</body>
</html>