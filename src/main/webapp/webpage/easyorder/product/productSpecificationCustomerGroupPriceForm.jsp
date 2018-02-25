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
				// 不提交表单
				
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
				<h5>多规格客户组价格设置</h5>
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
					action="${ctx}/productManager/productSpecificationCustomerGroupPrice/save" method="post"
					class="form-horizontal">
						<input type="hidden" id="customerGroupPrices" name="customerGroupPrices">
						<input type="hidden" id="productSpecificationId" name="productSpecificationId" value="${productSpecificationCustomerGroupPrice.productSpecificationId}"/>
						<sys:message content="${message}" />
							<!-- start 客户组指定价 -->
							<div class="form-group  paddingLR-10">
								<div class="col-sm-12">
									<label class="">客户组价</label>
										<input id="customerGroupIds" type="hidden"/>
										<div class="display-ib">
											<button type="button" class="btn btn-white btn-sm" id="customerGroupSelector">选择客户组</button>
										</div>
								</div>
								<div class="col-sm-12">
									<table id="customerGroupPrice" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
										<thead>
											<tr>
												<th>客户组名称</th>
												<th>订货价格</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${productSpecificationCustomerGroupPrice.customerGroupPrices}" var="cgp">
												<tr data-id='${cgp.customerGroupId}'>
													<td data-type="customerGroupName">${cgp.customerGroupName}</td>
													<td data-type="orderPrice">
														<input name="customerGroupPrice[${cgp.customerGroupId}]" type="number" value="${cgp.price}" maxlength="20" class="form-control required">
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
							<!-- 客户组指定价 end -->
						</div>
					</form:form>
		    	</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctxStatic}/easy-selector/easy-selector.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-page-records/easy-page-records.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easyorder/js/product/product-spec-customer-group-price.js"></script>
    <script type="text/javascript">
    	var productSpecificationId = "${productSpecificationCustomerGroupPrice.productSpecificationId}";
    	var iframe_name = top.getActiveTab().attr("name");
    	if(productSpecificationId) {
	    	var $targetTr = top.$('iframe[name="' + iframe_name + '"]').contents().find('.spec-table .spec-tbody[data-id="' + productSpecificationId + '"]');
    		var dataPrice = $targetTr.find('[data-attr-name="productSpecificationCustomerGroupPrices"]').attr('data-price');
    		// 获取所有价格信息
    		if(dataPrice) {
    			dataPrice = JSON.parse(dataPrice);
    			if(dataPrice && dataPrice.length != 0) {
        			var $customerGroupPrice = $("#customerGroupPrice");
        			$.each(dataPrice, function(index, dp) {
        				var $tr = $("<tr>");
        				$tr.attr("data-id", dp.customerGroupId);
        				$tr.appendTo($customerGroupPrice);
        				// 遍历时需要的属性,注意顺序需要对应表头所需要的属性
        				var persistProp = ["customerGroupName", "orderPrice"];
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
            						$input.attr("name", "customerGroupPrice[" + dp.customerGroupId + "]");
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
						$span.appendTo($td);
						$td.appendTo($tr);
        			});
    			}
    		}
    	}
    	
    	var getPrices = function() {
    		var prices = [];
    		var $trs = $("#customerGroupPrice tbody").find("tr");
    		if(!$trs || $trs.length == 0) {
    			return "";
    		}
    		$.each($trs, function(index, tr) {
    			var p = {};
    			var $tr = $(tr);
    			var customerGroupId = $tr.attr('data-id');
    			if(customerGroupId) {
    				p.customerGroupId = customerGroupId;
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