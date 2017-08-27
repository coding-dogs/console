<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>客户管理</title>
<meta name="decorator" content="default" />
<link rel="stylesheet"
	href="${ctxStatic}/easy-selector/easy-selector.css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>订货单信息 </h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="fa fa-wrench"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
		    
		    <div class="ibox-content">
			
				<div class="row">
					<div class="col-sm-12">
						<div class="easy-order customer-base-info">
							<h2>信息概览</h2>
							<div class="col-sm-4">
								<span>订货单号：${order.orderNo}</span>
							</div>
							<div class="col-sm-4">
								<span>订货单状态：${fns:getDictLabel(order.mtOrderStatusCd, 'mtOrderStatusCd', '-')}</span>
								<c:if test="${order.action eq 'edit' }">
									<form id="statusForm" action="${ctx}/orderManager/order/save" method="POST">
										<input type="hidden" name="id" value="${order.id}"/>
										<input type="hidden" id="mtOrderStatusCd" name="mtOrderStatusCd" value="${order.mtOrderStatusCd}"/>
										<input type="hidden" name="action" value="${order.action}"/>
										<c:choose>
											<c:when test="${order.mtOrderStatusCd eq 'PENDING_AUDIT'}">
												<button type="submit" class="btn btn-sm btn-white" onclick="changeStatus('PENDING_SHIP')">订单核准</button>
											</c:when>
											<c:when test="${order.mtOrderStatusCd eq 'PENDING_SHIP'}">
												<button type="button" class="btn btn-sm btn-white" onclick="shipping()">确认发货</button>
											</c:when>
										</c:choose>
										<c:if test="${order.mtOrderStatusCd ne 'CANCELED'}">
											<button type="submit" class="btn btn-sm btn-white" onclick="changeStatus('CANCELED')">取消订单</button>
										</c:if>
									</form>
								</c:if>
							</div>
							<div class="col-sm-4">
								<span>下单时间：<fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
							</div>
						</div>
					</div>
					
					<div class="col-sm-12">
						<div class="easy-order customer-base-info">
							<h2>订单详情</h2>
							<table class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
								<tr>
									<th>序号</th>
									<th>商品</th>
									<th>数量</th>
									<th>单价</th>
									<th>小计</th>
								</tr>
								<c:forEach items="${order.orderItems}" var="orderItem" varStatus="status">
									<tr>
										<td>${status.index + 1}</td>
										<td>
											<div class="display-ib clearfix">
												<img class="pull-left marginR-10" src="${orderItem.productPicUrl}" width="40" height="40" alt="${orderItem.productTitle}"/>
												<div class="pull-left display-ib">
													<dl>
														<dt>${orderItem.productNo}</dt>
														<dd>${orderItem.productTitle}</dd>
													</dl>
												</div>
											</div>
										</td>
										<td>
											${orderItem.totalCount}
										</td>
										<td>
											${orderItem.price}
										</td>
										<td>
											${orderItem.totalPrice}
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td>合计</td>
									<td>${fn:length(order.orderItems)}种</td>
									<td>${order.totalCount}</td>
									<td></td>
									<td>¥ ${order.totalPrice}</td>
								</tr>
								<tr>
									<td colspan="5" align="right">运费: ¥ ${order.fare}</td>
								</tr>
								<tr>
									<td colspan="5" align="right">订单总额: ¥ ${order.totalPrice}</td>
								</tr>
							</table>
						</div>
					</div>
					
					<div class="col-sm-12">
						<div class="easy-order customer-base-info">
							<h2>订单备注</h2>
							<div class="col-sm-12">
								备注: <if test="${not empty order.remarks}">${order.remarks}</if><if test="${empty order.remarks}">无</if>
							</div>
						</div>
					</div>
					
					<div class="col-sm-12">
						<div class="easy-order customer-base-info">
							<h2>收货地址</h2>
							<div class="col-sm-4">
								<span>收货人姓名:${order.orderShippingAddress.shippingName}</span>
							</div>
							<div class="col-sm-4">
								<span>收货人电话:${order.orderShippingAddress.shippingTel}</span>
							</div>
							<div class="col-sm-4">
								<span>收货人手机号:${order.orderShippingAddress.shippingPhone}</span>
							</div>
							<div class="col-sm-4">
								<span>收货人邮编:${order.orderShippingAddress.zipCode}</span>
							</div>
							<div class="col-sm-8">
								<span>收货人地址: ${order.orderShippingAddress.mtProvinceCd} ${order.orderShippingAddress.mtCityCd} ${order.orderShippingAddress.mtCountyCd} ${order.orderShippingAddress.address}</span>
							</div>
						</div>
					</div>
					
					<c:if test="${not empty order.orderExpress}">
						<div class="col-sm-12">
							<div class="easy-order customer-base-info">
								<h2>发货物流信息</h2>
								<div class="col-sm-4">
									发货时间: <fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</div>
								<div class="col-sm-4">
									物流公司: ${order.orderExpress.companyName}
								</div>
								<div class="col-sm-4">
									物流单号: ${order.orderExpress.expressNo}
								</div>
							</div>
						</div>
					</c:if>					
				</div>
				<div class="btns mt-10">
					<button type="button" class="btn btn-white" onclick="window.location.href='${ctx}/orderManager/order/'">返回</button>
				</div>
			</div>
		</div>
	</div>
	<div id="html" style="display: none;">
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
			<tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><em
							class="required-tag">* </em>物流公司:</label></td>
					<td class="width-35"><input id="companyName" name="companyName"
							maxlength="100" class="form-control required" /></td>
					<td class="width-15 active"><label class="pull-right"><em
							class="required-tag">* </em>物流单号:</label></td>
					<td class="width-35"><input id="expressNo" name="expressNo" maxlength="100" class="form-control " /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<script type="text/javascript">
		function changeStatus(val) {
			$("#mtOrderStatusCd").val(val);
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