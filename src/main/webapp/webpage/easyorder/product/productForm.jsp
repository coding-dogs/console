<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>商品管理</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/easy-selector/easy-selector.css">
<link rel="stylesheet" href="${ctxStatic}/easy-uploader/easy-uploader.css">
<link rel="stylesheet" href="${ctxStatic}/easy-tabs/easy-tabs.css">
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
	var descForm;
	function doSubmit() {//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		if (validateForm.form()) {
			$('#specJson').val(JSON.stringify(getSpec4Json()));
			$("#inputForm").submit();
			return true;
		}

		return false;
	}
	$(document).ready(function () {
		validateForm = $("#inputForm").validate({
			ignore: '',
			rules: {
				productCategoryId: {
					required: true
				}
			},
			messages: {
				productCategoryId: {
					required: '请选择商品类目'
				}
			},
			submitHandler : function(form) {
				loading('正在提交，请稍等...');
				$('#specJson').val(JSON.stringify(getSpec4Json()));
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
				} else if(element.parents('.easy-selector').length > 0){
					element.parents('.easy-selector').addClass('error');
					error.insertAfter(element.parents('.easy-selector'));
				} else {
					error.insertAfter(element);
				}
			}
		});
		
		descForm = $("#descForm").validate({
			ignore: '',
			rules: {
				id: {
					required: true
				}
			},
			messages: {
				id: {
					required: '请保存商品后重新进入后进行保存'
				}
			},
			submitHandler : function(form) {
				loading('正在提交，请稍等...');
				$('#description').val(descEditor.getContent());
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
				<h5>
					<c:choose>
						<c:when test="${product.action eq 'add'}">
							新增商品信息
						</c:when>
						<c:when test="${product.action eq 'edit'}">
							编辑商品信息
						</c:when>
						<c:otherwise>
							商品信息预览
						</c:otherwise>
					</c:choose>
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
	    
		    <div id="ibox-content" class="ibox-content">
		    	<ul id="tabs-content">
		    		<li>
		    			<a href="#baseInfo">基本信息</a>
		    		</li>
		    		<li>
		    			<a href="#picAndDesc">图片与详情</a>
		    		</li>
		    	</ul>
		    
		    	<div id="baseInfo">
		    		<form:form id="inputForm" modelAttribute="product"
					action="${ctx}/productManager/product/save" method="post"
					class="form-horizontal">
						<input type="hidden" id="id" name="id" value="${product.id}"/>
						<sys:message content="${message}" />
						<div class="easy-order product-base-info">
							<h2>基本信息</h2>
							<!-- start 商品基本信息 -->
							<div class="form-group">
								<label for="name" class="col-sm-2 control-label"><em class="required-tag">* </em>商品名称</label>
								<div class="col-sm-4">
									<input type="text" id="name" name="name" value="${product.name}" placeholder="请输入商品名称" maxlength="50" class="form-control required"/>
								</div>
								<label for="productNo" class="col-sm-2 control-label"><em class="required-tag">* </em>商品编号</label>
								<div class="col-sm-4">
									<input type="text" id="productNo" name="productNo" value="${product.productNo}" placeholder="请输入商品编号" maxlength="50" class="form-control required" readonly="readonly"/>
								</div>
							</div>
							<div class="form-group">
								<label for="title" class="col-sm-2 control-label"><em class="required-tag">* </em>商品标题</label>
								<div class="col-sm-4">
									<input type="text" id="title" name="title" value="${product.title}" placeholder="请输入商品标题" maxlength="100" class="form-control required"/>
								</div>
								<label for="modelNumber" class="col-sm-2 control-label"><em class="required-tag">* </em>商品型号</label>
								<div class="col-sm-4">
									<input type="text" id="modelNumber" name="modelNumber" value="${product.modelNumber}" placeholder="请输入商品型号" maxlength="100" class="form-control required"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label"><em class="required-tag">* </em>商品分类</label>
								<div class="col-sm-4">
									<div id="productCategory" data-text="请选择商品分类" data-required="true" data-value="${product.productCategoryId}" data-name='productCategoryId'></div>
								</div>
								<label class="col-sm-2 control-label">商品品牌</label>
								<div class="col-sm-4">
									<div id="productBrand" data-text="请选择商品品牌" data-required="false" data-value="${product.productBrandId}" data-name='productBrandId'></div>
								</div>
							</div>
							<div class="form-group">
								<label for="key" class="col-sm-2 control-label">商品关键字</label>
								<div class="col-sm-4">
									<input type="text" id="key" name="key" maxlength="300" placeholder="请输入商品搜索关键字，多个以空格隔开" value="${product.key}" class="form-control" />
								</div>
								<label for="minimumOrderNumber" class="col-sm-2 control-label"><em class="required-tag">* </em>起订量</label>
								<div class="col-sm-4">
									<input type="text" id="minimumOrderNumber" name="minimumOrderNumber" value="${product.minimumOrderNumber}" maxlength="50" placeholder="请输入商品起订量" class="form-control required"/>
								</div>
							</div>
							<div class="form-group">
								<label for="barCode" class="col-sm-2 control-label">商品条形码</label>
								<div class="col-sm-4">
									<input type="text" id="barCode" name="barCode" maxlength="50" placeholder="请输入商品包装上的条形码" value="${product.barCode}" class="form-control" />
								</div>
								<label class="col-sm-2 control-label"><em class="required-tag">* </em>上架下架</label>
								<div class="col-sm-4 easy-form-ichecks">
									<form:radiobuttons path="mtProductUpdownCd" items="${fns:getDictList('mtProductUpdownCd')}" itemLabel="label" itemValue="value" class="required i-checks"/>
								</div>
							</div>
							<div class="form-group">
								<label for="remarks" class="col-sm-2 control-label">备注</label>
								<div class="col-sm-4">
									<textarea id="remarks" name="remarks" maxlength="300" class="form-control">${product.remarks}</textarea>
								</div>
							</div>
							<!-- 商品基本信息 end -->
						</div>
						
						<!-- start 商品单位 -->
						<div class="easy-order product-price-info">
							<h2>单位</h2>
							<div class="form-group">
								<label class="col-sm-2 control-label"><em class="required-tag">* </em>商品单位</label>
								<div class="col-sm-4">
									<div id="unit" data-text="请选择商品单位" data-required="true" data-value="${product.unitId}" data-name='unitId'></div>
								</div>
							</div>
						</div>
						<!-- 商品单位 end -->
						
						<!-- start 商品多规格开启与设置 -->
						<div class="easy-order product-price-info" id="variousSpecManager">
							<h2>多规格</h2>
							<input type="hidden" name="specJson" id="specJson">
							<div class="form-group">
								<!-- 多规格开启及关闭 -->
								<div class="col-sm-2 control-label">
									<label><input id="openMultiple" name="openVariousSepc" class="form-control i-checks openVariousSepc" type="checkbox" /> 开启多规格</label>
								</div>
								<!-- <div id="spec-opera-control">
									<a id="addSpec" href="javascript:void(0);" class="easy-form-span"><i class="fa fa-plus"></i> 新增多规格</a>
									<a id="specManager" href="javascript:void(0);" class="easy-form-span"><i class="fa fa-gears"></i> 管理多规格</a>
								</div> -->
							</div>
							<!-- 多规格设置区域 -->
							<div class="variousSpec form-group" id="variousSpecArea">

							</div>
						</div>
						<!-- 商品多规格开启与设置 end -->
						
						<div class="easy-order product-price-info">
							<h2>价格</h2>
							<!-- start 商品价格信息 -->
							<div class="form-group">
								<label for="orderPrice" class="col-sm-2 control-label"><em class="required-tag">* </em>订货价</label>
								<div class="col-sm-4">
									<input type="number" id="orderPrice" name="orderPrice" value="${product.orderPrice}" placeholder="请输入订货价格(单位：元)" maxlength="20" class="form-control required"/>
								</div>
								<label for="buyPrice" class="col-sm-2 control-label"> 进货价</label>
								<div class="col-sm-4">
									<input type="number" id="buyPrice" name="buyPrice" value="${product.buyPrice}" placeholder="请输入进货价格(单位：元)" maxlength="20" class="form-control"/>
								</div>
							</div>
							<!-- 商品价格信息 end -->
							
							<!-- start 客户指定价 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">客户价</label>
								<input id="customerIds" type="hidden"/>
								<div class="col-sm-10 display-ib">
									<button type="button" class="btn btn-white btn-sm" id="customerSelector">选择客户</button>
								</div>
								<div class="col-sm-11 col-sm-offset-1">
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
											<c:forEach items="${product.productCustomerPrices}" var="cp">
												<tr data-id="${cp.customerId}">
													<td>${cp.customerName}</td>
													<td>${cp.customerNo}</td>
													<td>${cp.customerGroupName}</td>
													<td>${cp.mtCityCd}</td>
													<td>
														<input name="customerPrice[${cp.customerId}]" type="number" value="${cp.price}" maxlength="20" class="form-control">
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
							
							<!-- start 客户组指定价 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">客户组价</label>
								<input id="customerGroupIds" type="hidden"/>
								<div class="col-sm-10 display-ib">
									<button type="button" class="btn btn-white btn-sm" id="customerGroupSelector">选择客户组</button>
								</div>
								<div class="col-sm-11 col-sm-offset-1">
									<table id="customerGroupPrice" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
										<thead>
											<tr>
												<th>客户组名称</th>
												<th>订货价格</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${product.productCustomerGroupPrices}" var="cgp">
												<tr data-id='${cgp.customerGroupId}'>
													<td>${cgp.customerGroupName}</td>
													<td>
														<input name="customerGroupPrice[${cgp.customerGroupId}]" type="number" value="${cgp.price}" maxlength="20" class="form-control">
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
						
						<div class="easy-order text-center btns">
							<c:if test="${product.action eq 'add' or product.action eq 'edit' }">
								<button type="submit" class="btn btn-success">提交</button>			
							</c:if>
							<button type="button" class="btn btn-white" onclick="window.location.href='${ctx}/productManager/product'">返回</button>
						</div>
					</form:form>
		    	</div>
				<div id="picAndDesc">
					<form:form id="descForm" modelAttribute="product" action="${ctx}/productManager/product/picDesc" method="post" class="form-horizontal">
						<input type="hidden" name="id" id="productId" class="form-control required" value="${product.id}"/>
						<!-- start 商品图片 -->
						<div class="easy-order product-pictures">
							<h2>商品图片<span class="placeholder">(最多可添加24张图片，建议图片尺寸800*800，大小≤5MB,支持JPG、PNG、JPEG)</span></h2>
							<input type="hidden" name="coverUrl" id="coverUrl" value="${product.coverUrl }"/>
							<div id="easy-uploader"></div>
						</div>
						<!-- 商品图片 end -->
						
						<!-- start 商品描述 -->
						<div class="easy-order">
							<h2>商品图文描述</h2>
							<div class="form-group">
								<div class="col-sm-12">
									<input type="hidden" id="description" name="description" value='${product.description}'/>
									<!-- 注意此处内容，不要出现空行，否则存在每次编辑都多处空行的问题 -->
									<script id="desc" name="desc" type="text/plain">${product.description}</script>
								</div>							
							</div>
						</div>
						<br>
						<!-- 商品描述 end -->
						<div class="easy-order text-center btns">
							<c:if test="${product.action eq 'add' or product.action eq 'edit' }">
								<button type="submit" class="btn btn-success">提交</button>			
							</c:if>
							<button type="button" class="btn btn-white" onclick="window.location.href='${ctx}/productManager/product'">返回</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="${ctxStatic}/easy-selector/easy-selector.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-uploader/easy-uploader.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-tabs/easy-tabs.js"></script>
	<script type="text/javascript" src="${ctxStatic}/easy-page-records/easy-page-records.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${ctxStatic}/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript">
    	var productId = '${product.id}';
    	var productNo = '${product.productNo}';
    	var productSpecJson = '${product.specJson}';
    </script>
    <!-- 商品添加/编辑页面函数及组件使用 -->
	<script type="text/javascript" src="${ctxStatic}/easyorder/js/product/product-form.js"></script>
</body>
</html>