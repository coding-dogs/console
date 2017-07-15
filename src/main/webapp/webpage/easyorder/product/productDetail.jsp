<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default" />
	<link rel="stylesheet" href="${ctxStatic}/easy-carousel/easy-carousel.css">
	<link rel="stylesheet" href="${ctxStatic}/easyorder/css/product.css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5>
					商品详情
				</h5>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
	    
		    <div id="ibox-content" class="ibox-content clearfix product-detail">
		    	<div class="product-detail-top">
		    		<div class="product-detail-top-left">
		    			<div id="product-pictures"></div>
			    	</div>
			    	<div class="product-detail-top-right">
			    		<h2>${product.title}</h2>
			    		<ul class="product-base-info">
			    			<c:if test="${not empty product && not empty product.productCategoryName }">
			    				<li>
				    				<span>商品分类：</span>
				    				<span>${product.productCategoryName}</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.productBrandName }">
			    				<li>
				    				<span>商品品牌：</span>
				    				<span>${product.productBrandName}</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.productNo }">
			    				<li>
				    				<span>商品编号：</span>
				    				<span>${product.productNo}</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.modelNumber }">
			    				<li>
				    				<span>商品型号：</span>
				    				<span>${product.modelNumber}</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.barCode }">
			    				<li>
				    				<span>商品条形码：</span>
				    				<span>${product.barCode}</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.mtProductUpdownCd }">
			    				<li>
				    				<span>上架下架：</span>
				    				<span>${fns:getDictLabel(product.mtProductUpdownCd, 'mtProductUpdownCd', '-') }</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.minimumOrderNumber }">
			    				<li>
				    				<span>起订量：</span>
				    				<span>${product.minimumOrderNumber}</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.productCategoryName }">
			    				<li>
				    				<span>商品分类：</span>
				    				<span>${product.productCategoryName}</span>
				    			</li>
			    			</c:if>
			    		</ul>
			    		
			    		<ul class="product-price">
			    			<li>
			    				<span>订货价：</span>
			    				<span>¥${product.orderPrice}元</span>
			    			</li>
			    			<li>
			    				<span>进货价：</span>
			    				<span>¥${product.buyPrice}元</span>
			    			</li>
			    		</ul>
			    		
			    		<div class="product-price-table">
			    			<span>客户指定价</span>
			    			<div class="price-table">
				    			<table id="customerPrice" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
									<thead>
										<tr>
											<th>客户名称</th>
											<th>订货价格</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${product.productCustomerPrices}" var="cp">
											<tr>
												<td>${cp.customerName}</td>
												<td>
													${cp.price}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
			    		</div>
			    		<div class="product-price-table">
			    			<span>客户组指定价</span>
			    			<div class="price-table">
				    			<table id="customerGroupPrice" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
									<thead>
										<tr>
											<th>客户组名称</th>
											<th>订货价格</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${product.productCustomerGroupPrices}" var="cgp">
											<tr>
												<td>${cgp.customerGroupName}</td>
												<td>
													${cgp.price}
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
			    		</div>
			    	</div>
		    	</div>
		    	
		    	<div class="product-description col-xs-12">
		    		<div class="product-desc col-xs-12">
		    			${product.description}
		    		</div>
		    	</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctxStatic}/easy-carousel/easy-carousel.js"></script>
	<script type="text/javascript">
		$.ajax({
			url: '${ctx}/productManager/productPicture/async/list',
			type: 'GET',
			data: {productId : "${product.id}"},
			dataType: 'JSON',
			success: function(data) {
				if(SUCCESS_CODE == data.code) {
					$('#product-pictures').easyCarousel({
						items: data.result
					});
				}
			}
		});
	</script>
</body>
</html>