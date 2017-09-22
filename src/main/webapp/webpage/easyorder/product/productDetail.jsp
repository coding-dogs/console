<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default" />
	<link rel="stylesheet" href="${ctxStatic}/easy-carousel/easy-carousel.css">
	<link rel="stylesheet" href="${ctxStatic}/easyorder/css/product.css">
	<style type="text/css">
		.spec-box {
			margin-bottom: 10px;
		}
		.spec-block {
			border: 2px solid #F0F0F0;
			background-color: #fff;
			color: #666;
			padding: 3px 5px;
			width: auto;
			float: left;
			margin: 0 10px 10px 0;
			cursor: pointer;
			user-select: none;
		}
		
		.spec-block.active {
			border-color: #1ab394;
			background-color: #1ab394;
			color: #fff;
		}
		
		.spec-no-div {
			margin-bottom: 10px;
		}
	</style>
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
			    			<c:if test="${not empty product && not empty product.unitName }">
			    				<li>
				    				<span>商品单位：</span>
				    				<span>${product.unitName}</span>
				    			</li>
			    			</c:if>
			    			<c:if test="${not empty product && not empty product.minimumOrderNumber }">
			    				<li>
				    				<span>起订量：</span>
				    				<span>${product.minimumOrderNumber}${product.unitName}</span>
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
			    		
			    		<div id="spec-display-container" class="spec-display-container ">
			    		</div>
			    		
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
	<script type="text/javascript" src="${ctxStatic}/easyorder/js/common/easy-request.js"></script>
	<script type="text/javascript">
		$.ajax({
			url: '${ctx}/productManager/productPicture/async/list',
			type: 'GET',
			data: {productId : "${product.id}"},
			dataType: 'JSON',
			success: function(data) {
				if(SUCCESS_CODE == data.code) {
					$('#product-pictures').easyCarousel({
						items: data.result				// items参数为要显示的图片数据，类型为对象数组，对象必须包含属性---url：图片路径
						//,trigger: 'click'				//	默认为鼠标悬浮切换，值为hover，若要点击后切换图片，将此属性更改为click即可
					});
				}
			}
		});
		
		function generateNo() {
			var lis = $('#spec-display-container').find('.spec-box li.active');
			if(lis.length > 0) {
				var tmpPath = "";
				$.each(lis, function(index, li) { 
					tmpPath += "," + $(li).attr('data-id');
				});
				if(tmpPath) {
					tmpPath = tmpPath.substring(1);
				}
				var specNoDiv = $('#spec-display-container').find('.spec-no-div');
				if(specNoDiv.length > 0) {
					specNoDiv.html('');
				}
				if(productSpecList && productSpecList.length > 0) {
					$.each(productSpecList, function(idx, psl) {
						if(tmpPath == psl["specificationItemPath"]) {
							var specNoSpan = $('<span>规格编号：' + psl.specificationNo + '</span>');
							specNoSpan.appendTo(specNoDiv);
						}
					});
				}
			}
		}
		
		function generateItemBlocks(sp) {
			easyorder.get("productManager/specification/items/ids", {itemIds: sp}, false, function(dataItem) {
				if(dataItem.code == SUCCESS_CODE) {
					var container =	$("#spec-display-container");
					var result = dataItem.result;
					var tmp = result[0];
					var box = container.find('.spec-box[data-spec-id="' + tmp.specificationId + '"]');
					if(box.length > 0) {
						var ul = $('<ul>');
						ul.appendTo(box);
						$.each(result, function(i, specItem) {
							var li = $('<li class="spec-block" data-id="' + specItem.id + '">');
							li.text(specItem.name);
							li.appendTo(ul);
							if(i == 0) {
								li.addClass('active');
							}
						});
					}
				} else {
					top.layer.alert(data.msg);
				}
			}, function() {
				
			});
		}
		
		var productId = "${product.id}";
		var productSpecList = [];
		// 多规格展示
		// 1. 先获取商品多规格关联信息
		easyorder.get("productManager/productSpecification/async/list", {productId: productId}, false, function(data) {
			if(SUCCESS_CODE == data.code) {
				var resultList = data.result;
				if(resultList && resultList.length > 0) {
					var result = resultList[0];
					productSpecList = resultList;
					var path = result['specificationItemPath'];
					
					// 使用多规格值信息查询多规格项信息
					easyorder.get("productManager/specification/details", {itemIds: path}, false, function(rs) {
						if(rs.code == SUCCESS_CODE) {
							var itemList = rs.result;
							if(itemList && itemList.length > 0) {
								var container =	$("#spec-display-container");
								$.each(itemList, function(index, item) {
									var box = $("<div class='clearfix spec-box'>");
									box.attr('data-spec-id', item.id);
									box.appendTo(container);
									
									var span = $('<span class="pull-left">' + item.name + '：</span>');
									span.appendTo(box);
								});
								var specNoDiv = $('<div class="spec-no-div">');
								specNoDiv.appendTo(container);
							}
						} else {
							top.layer.alert(data.msg);
						}
					}, function() {
						
					});
					
					var array = path.split(',');
					// 遍历组装数据
					var pList1 = [], pList2 = [];
					$.each(resultList, function(i, rl) {
						var sip = rl.specificationItemPath.split(',');
						if(existsOfArray(sip[0] ,pList1) == -1) {
							pList1.push(sip[0]);
						}
						if(array.length == 2) {
							if(existsOfArray(sip[1] ,pList2) == -1) {
								pList2.push(sip[1]);
							}
						}
					});
					
					var sp1 = pList1.join(',');
					generateItemBlocks(sp1);
					
					
					if(array.length == 2) {
						var sp2 = pList2.join(',');
						generateItemBlocks(sp2);
					}
					generateNo();
				}
			} else {
				top.layer.alert(data.msg);
			}
			$('.spec-box .spec-block').on('click', function(e) {
				var that = $(this);
				that.addClass('active').siblings().removeClass('active');
				generateNo();
			});
		}, function(XMLHttpRequest, textStatus, errorThrown) {
			
		});
		
		// 2. 拆分多规格信息
		
		
		
		// 3. 分别获取不同规格类型下涉及到的值
		
		
		
		// 4. 查找对应位置进行显示
	</script>
</body>
</html>