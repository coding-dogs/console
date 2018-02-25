<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>多规格客户价格设置页面</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/easy-selector/easy-selector.css">
<link rel="stylesheet" href="${ctxStatic}/easyorder/css/product.css">
<style type="text/css">
	.ibox-content {
		padding: 15px 0;
	}
	
	.form-horizontal {
		margin: 0;
	}
</style>
<script type="text/javascript">
	var validateForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	$(document).ready(function () {
		validateForm = $("#inputForm").validate({
			ignore: '',
			submitHandler : function(form) {
				// 不提价表单
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
				<h5>多规格客户价格设置</h5>
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
		    	<div id="baseInfo">
		    		<!-- 不提交表单,但保留表单做表单校验用 -->
		    		<form:form id="inputForm" modelAttribute="product"
					action="${ctx}/productManager/productSpecificationCustomerPrice/save" method="post"
					class="form-horizontal">
						<input type="hidden" id="customerGroupPrices" name="customerGroupPrices">
						<input type="hidden" id="productSpecificationId" name="productSpecificationId" value="${productSpecificationCustomerPrice.productSpecificationId}"/>
						<sys:message content="${message}" />
						<!-- 商品多规格开启与设置 end -->
						
						<div class="easy-order product-price-info">
							<!-- start 客户指定价 -->
							<div class="form-group paddingLR-10">
								<div class="col-sm-12">
									<label class="">客户价</label>
									<input id="customerIds" type="hidden"/>
									<div class="display-ib">
										<button type="button" class="btn btn-white btn-sm" id="customerSelector">选择客户</button>
									</div>
								</div>
								<div class="col-sm-12">
									<table id="customerPrice" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
										<thead>
											<tr>
												<th>客户名称</th>
												<th>客户编号</th>
												<th>所属客户组</th>
												<th>客户地区</th>
												<th>订货价格</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${productSpecificationCustomerPrice.customerPrices}" var="cp">
												<tr data-id="${cp.customerId}">
													<td data-type="customerName">${cp.customerName}</td>
													<td data-type="customerNo">${cp.customerNo}</td>
													<td data-type="customerGroupName">${cp.customerGroupName}</td>
													<td data-type="mtCityCd">${cp.mtCityCd}</td>
													<td data-type="orderPrice">
														<input name="customerPrice[${cp.customerId}]" type="number" value="${cp.price}" maxlength="20" class="form-control required">
													</td>
													<td>
														<span class="fa fa-times"></span>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<!-- 客户指定价 end -->
						</div>
					</form:form>
		    	</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctxStatic}/easy-selector/easy-selector.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-page-records/easy-page-records.js"></script>
    <script type="text/javascript" src="${ctxStatic}/easyorder/js/product/product-spec-customer-price.js"></script>
    <script type="text/javascript">
    	var productSpecificationId = "${productSpecificationCustomerPrice.productSpecificationId}";
    	
    	var iframe_name = top.getActiveTab().attr("name");
    	if(productSpecificationId) {
	    	var $targetTr = top.$('iframe[name="' + iframe_name + '"]').contents().find('.spec-table .spec-tbody[data-id="' + productSpecificationId + '"]');
    		var dataPrice = $targetTr.find('[data-attr-name="productSpecificationCustomerPrices"]').attr('data-price');
    		// 获取所有价格信息
    		if(dataPrice) {
    			dataPrice = JSON.parse(dataPrice);
    			if(dataPrice && dataPrice.length != 0) {
        			var $customerPrice = $("#customerPrice");
        			$.each(dataPrice, function(index, dp) {
        				var $tr = $("<tr>");
        				$tr.attr("data-id", dp.customerId);
        				$tr.appendTo($customerPrice);
        				// 遍历时需要的属性,注意顺序需要对应表头所需要的属性
        				var persistProp = ["customerName", "customerNo", "customerGroupName", "mtCityCd", "orderPrice"];
        				$.each(persistProp, function(idx, prop) {
        					var $td = $("<td>");
        					$td.attr("data-type", prop);
        					for(var key in dp) {
            					if(prop != key) {
            						continue;
            					}
            					if(key == "customerGroupId") {
            						continue;
            					} else if(key == "orderPrice") {
            						var $input = $("<input>");
            						$input.addClass("form-control").addClass("required");
            						$input.attr("type", "number");
            						$input.attr("maxlength", "20");
            						$input.attr("name", "customerPrice[" + dp.customerGroupId + "]");
            						$input.val(dp[key]);
            						$input.appendTo($td);
            					} else {
            						$td.text(dp[key]);
            					}
            				}
        					$td.appendTo($tr);
        				});
        				
        				var $td = $("<td>");
        				var $span = $("<span>")
						$span.addClass("fa").addClass("fa-times");
        				$span.on("click", function(e) {
        					$tr.remove();
        				});
						$span.appendTo($td);
						$td.appendTo($tr);
        			});
    			}
    		}
    	}
    	
    	
    	var getPrices = function() {
    		var prices = [];
    		var $trs = $("#customerPrice tbody").find("tr");
    		if(!$trs || $trs.length == 0) {
    			return "";
    		}
    		$.each($trs, function(index, tr) {
    			var p = {};
    			var $tr = $(tr);
    			var customerId = $tr.attr('data-id');

    			if(customerId) {
    				p.customerId = customerId;
    			}
    			
    			var $tds = $tr.find("[data-type]");
    			if($tds.length != 0) {
    				$.each($tds, function(idx, td) {
    					var $td = $(td);
    					var dataType = $td.attr("data-type");
    					var value = $td.find("input").val() || $td.text();
    					if(value) {
    						p[dataType] = value;
    					}
    				});
    			}
    			prices.push(p);
    		})
    		return prices;
    	}
    </script>
</body>
</html>