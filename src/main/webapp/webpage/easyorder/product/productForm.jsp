<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<title>商品管理</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/easy-selector/easy-selector.css">
<link rel="stylesheet" href="${ctxStatic}/easy-uploader/easy-uploader.css">
<link rel="stylesheet" href="${ctxStatic}/easy-tabs/easy-tabs.css">
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
			$('#description').val(descEditor.getContent());
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
								<label for="title" class="col-sm-2 control-label"><em class="required-tag">* </em>商品标题</label>
								<div class="col-sm-4">
									<input type="text" id="title" name="title" value="${product.title}" placeholder="请输入商品标题" maxlength="100" class="form-control required"/>
								</div>
							</div>
							<div class="form-group">
								<label for="modelNumber" class="col-sm-2 control-label"><em class="required-tag">* </em>商品型号</label>
								<div class="col-sm-4">
									<input type="text" id="modelNumber" name="modelNumber" value="${product.modelNumber}" placeholder="请输入商品型号" maxlength="100" class="form-control required"/>
								</div>
								<label class="col-sm-2 control-label"><em class="required-tag">* </em>商品分类</label>
								<div class="col-sm-4">
									<div id="productCategory" data-text="请选择商品分类" data-required="true" data-value="${product.productCategoryId}" data-name='productCategoryId'></div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">商品品牌</label>
								<div class="col-sm-4">
									<div id="productBrand" data-text="请选择商品品牌" data-required="false" data-value="${product.productBrandId}" data-name='productBrandId'></div>
								</div>
								<label for="key" class="col-sm-2 control-label">商品关键字</label>
								<div class="col-sm-4">
									<input type="text" id="key" name="key" maxlength="300" placeholder="请输入商品搜索关键字，多个以空格隔开" value="${product.key}" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label for="minimumOrderNumber" class="col-sm-2 control-label"><em class="required-tag">* </em>起订量</label>
								<div class="col-sm-4">
									<input type="text" id="minimumOrderNumber" name="minimumOrderNumber" value="${product.minimumOrderNumber}" maxlength="50" placeholder="请输入商品起订量" class="form-control required"/>
								</div>
								<label for="barCode" class="col-sm-2 control-label">商品条形码</label>
								<div class="col-sm-4">
									<input type="text" id="barCode" name="barCode" maxlength="50" placeholder="请输入商品包装上的条形码" value="${product.barCode}" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">上架下架</label>
								<div class="col-sm-4 easy-form-ichecks">
									<form:radiobuttons path="mtProductUpdownCd" items="${fns:getDictList('mtProductUpdownCd')}" itemLabel="label" itemValue="value" class="required i-checks"/>
								</div>
								<label for="remarks" class="col-sm-2 control-label">备注</label>
								<div class="col-sm-4">
									<textarea id="remarks" name="remarks" maxlength="300" class="form-control">${product.remarks}</textarea>
								</div>
							</div>
							<!-- 商品基本信息 end -->
						</div>
						
						<div class="easy-order product-price-info">
							<h2>价格</h2>
							<!-- start 商品价格信息 -->
							<div class="form-group">
								<label for="orderPrice" class="col-sm-2 control-label"><em class="required-tag">* </em>订货价</label>
								<div class="col-sm-4">
									<input type="number" id="orderPrice" name="orderPrice" value="${product.orderPrice}" placeholder="请输入订货价格(单位：元)" maxlength="20" class="form-control required"/>
								</div>
								<label for="buyPrice" class="col-sm-2 control-label"><em class="required-tag">* </em>进货价</label>
								<div class="col-sm-4">
									<input type="number" id="buyPrice" name="buyPrice" value="${product.buyPrice}" placeholder="请输入进货价格(单位：元)" maxlength="20" class="form-control required"/>
								</div>
							</div>
							<!-- 商品价格信息 end -->
							
							<!-- start 客户指定价 -->
							<div class="form-group">
								<label class="col-sm-2 control-label">客户价</label>
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
												<tr>
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
												<tr>
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
		$(".fa-times").on('click', function(e) {
			$(this).parents('tr').remove();
		});
		$('#customerSelector').on('click', function(e) {
			openDialogWithCallback('选择客户', '${ctx}/customerManager/customer/selector', '800px', '600px', null, function(index, layero) {
				var body = top.layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0];
	         	var $table = iframeWin.contentWindow.table;
	         	var records = $table.easyPageRecords('getRecords');
	         	if(!records || records.length == 0) {
	         		top.layer.close(index);
	         		return;
	         	}
	         	var $tbody = $('#customerPrice').find('tbody');
	         	if($tbody.length == 0) {
	         		$tbody = $('<tbody>');
	         		$tbody.appendTo($('#customerPrice'));
	         	}
	         	var fields = ['name', 'customerNo', 'customerGroupName', 'mtCityCd'];
	         	$.each(records, function(idx, record) {
	         		var $tr = $('<tr>');
	         		$.each(fields, function(i, field) {
	         			var $td = $('<td>');
	         			$td.html(record[field]);
	         			$td.appendTo($tr);
	         		});
	         		var $td = $('<td>');
	         		var $inputPrice = $('<input name="customerPrice[' + record.id + ']" type="number" maxlength="20" class="form-control">');
	         		$inputPrice.appendTo($td);
	         		$td.appendTo($tr);
	         		var $operateTd = $('<td>');
	         		var $remove = $('<span class="fa fa-times" aria-hidden="true">');
	         		$remove.appendTo($operateTd);
	         		$operateTd.appendTo($tr);
	         		
	         		$remove.on('click', function(e) {
	         			$(this).parents('tr').remove();
	         		});
	         		
	         		$tr.appendTo($tbody);
	         	});
	         	top.layer.close(index);
			});
		});
		
		$('#customerGroupSelector').on('click', function(e) {
			openDialogWithCallback('选择客户', '${ctx}/customerManager/customerGroup/selector', '800px', '600px', null, function(index, layero) {
				var body = top.layer.getChildFrame('body', index);
				var iframeWin = layero.find('iframe')[0];
	         	var $table = iframeWin.contentWindow.table;
	         	var records = $table.easyPageRecords('getRecords');
	         	if(!records || records.length == 0) {
	         		top.layer.close(index);
	         		return;
	         	}
	         	var $tbody = $('#customerGroupPrice').find('tbody');
	         	if($tbody.length == 0) {
	         		$tbody = $('<tbody>');
	         		$tbody.appendTo($('#customerGroupPrice'));
	         	}
	         	var fields = ['name'];
	         	$.each(records, function(idx, record) {
	         		var $tr = $('<tr>');
	         		$.each(fields, function(i, field) {
	         			var $td = $('<td>');
	         			$td.html(record[field]);
	         			$td.appendTo($tr);
	         		});
	         		var $td = $('<td>');
	         		var $inputPrice = $('<input name="customerGroupPrice[' + record.id + ']" type="number" maxlength="20" class="form-control">');
	         		$inputPrice.appendTo($td);
	         		$td.appendTo($tr);
	         		var $operateTd = $('<td>');
	         		var $remove = $('<span class="fa fa-times" aria-hidden="true">');
	         		$remove.appendTo($operateTd);
	         		$operateTd.appendTo($tr);
	         		
	         		$remove.on('click', function(e) {
	         			$(this).parents('tr').remove();
	         		});
	         		
	         		$tr.appendTo($tbody);
	         	});
	         	top.layer.close(index);
			});
		});
		
	
		$('#tabs-content').easyTabs({
			selectedId: '#baseInfo',
			targetElement: '#ibox-content'
		});
	
		var descEditor = UE.getEditor('desc');
		UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		UE.Editor.prototype.getActionUrl = function(action) {
			if(action == 'uploadimage') {
				return "${ctx}/upload/image?utype=product"
			} else {
                 return this._bkGetActionUrl.call(this, action);
            }
		}
		UE.Editor.prototype.getContentLength =function() {
		    return this.getContent().length;
		}
		
		function getProductCategory() {
			var items = [];
			$.ajax({
				url : "${ctx}/productManager/productCategory/tree",
				async : false,
				type : "GET",
				data : {"hasRoot" : "N"},
				dataType : "json",
				success : function(data) {
					var _item = [];
					if(SUCCESS_CODE === data.code) {
						if(data.result && data.result.length > 0) {
							$.each(data.result, function(index, cate) {
								var item = {};
								item.value = cate.id;
								item.text = cate.name;
								item.pvalue = cate.pId;
								items.push(item);
							});
						}
					}
				}
			});	
			return items;
		}
		var items = getProductCategory();
		// 构建客户组选择器
		$("#productCategory").easySelector({
			type : 'tree',
			maxHeight : 250,
			hasMore : true,
			moreText : "<span style='text-weight:bold'> + </span> 新增商品分类",
			items : items,
			moreCallback : function() {
				openDialogWithCallback('新增商品分类', '${ctx}/productManager/productCategory/form','800px', '500px', null, function(index, layero) {
					var body = top.layer.getChildFrame('body', index);
					var inputForm = body.find("#inputForm");
					var name = inputForm.find("#name").val();
					var remarks = inputForm.find("#remarks").val();
					var pid = inputForm.find("#pId").val();
					var sort = inputForm.find("#sort").val();
					// 保存客户组
					$.ajax({
						url : "${ctx}/productManager/productCategory/async/save",
						type : "POST",
						data : {"name" : name, "remarks" : remarks, "pid" : pid, "sort" : sort},
						dataType : "JSON",
						success : function(data) {
							if(SUCCESS_CODE === data.code) {
								// 更新选择项
								$("#productCategory").easySelector("setOptions", "items", getProductCategory());
								// 选择当前值
								$("#productCategory").easySelector("select", data.result);
							} else {
								top.layer.alert(data.msg);
							}
							top.layer.close(index);
						},
						error : function(XMLHttpRequest, errorMsg, ex) {
							top.layer.alert("新增失败");
							top.layer.close(index);
						}
					});
					
				});
			}
		});
		
		function getProductBrands() {
			var items = [];
			$.ajax({
				url : "${ctx}/productManager/productBrand/all",
				async : false,
				type : "GET",
				dataType : "json",
				success : function(data) {
					var _item = [];
					if(SUCCESS_CODE === data.code) {
						if(data.result && data.result.length > 0) {
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
		var items = getProductBrands();
		// 构建客户组选择器
		$("#productBrand").easySelector({
			maxHeight : 250,
			hasMore : true,
			moreText : "<span style='text-weight:bold'> + </span> 新增商品品牌",
			items : items,
			moreCallback : function() {
				openDialogWithCallback('新增商品品牌', '${ctx}/productManager/productBrand/form','800px', '500px', null, function(index, layero) {
					var body = top.layer.getChildFrame('body', index);
					var inputForm = body.find("#inputForm");
					var name = inputForm.find("#name").val();
					var simpleName = inputForm.find("#simpleName").val();
					var sort = inputForm.find("#sort").val();
					var remarks = inputForm.find("#remarks").val();
					// 保存客户组
					$.ajax({
						url : "${ctx}/productManager/productBrand/async/save",
						type : "POST",
						data : {"name" : name, "remarks" : remarks, "sort" : sort, "simpleName" : simpleName},
						dataType : "JSON",
						success : function(data) {
							if(SUCCESS_CODE === data.code) {
								// 更新选择项
								$("#productBrand").easySelector("setOptions", "items", getProductBrands());
								// 选择当前值
								$("#productBrand").easySelector("select", data.result);
							} else{
								top.layer.alert(data.msg);
							}
							top.layer.close(index);
						},
						error : function() {
							top.layer.alert("新增失败");
							top.layer.close(index);
						}
					});
					
				});
			}
		});
		
		var pictureDatas = [];
		$.ajax({
			url: '${ctx}/productManager/productPicture/async/list',
			type: 'GET',
			data: {productId : '${product.id}'},
			dataType: 'JSON',
			async: false,
			success: function(data) {
				if(SUCCESS_CODE == data.code) {
					if(data.result) {
						$.each(data.result, function(i, result) {
							var picData = result;
							picData['isMain'] = (result['isMain'] == 'Y');
							pictureDatas.push(picData);
						});
					}
				}
			}
		});
		
		// 上传插件参照此处使用
		$("#easy-uploader").easyUploader({
			// 回显图片数据
			datas: pictureDatas,
			uploadUrl : "${ctx}/upload/product/file",
			success: function(uploader, data) {			// 此函数必须返回图片URL，以便于预览
				var fileUrl;
				if(SUCCESS_CODE === data.code) {
					uploader.find('.hidden-value').val(data.result);
					fileUrl = data.result;
				} else {
					top.layer.alert(data.msg);
				}
				return fileUrl;
			},
			error: function() {
				top.layer.alert('上传失败,请联系管理员');
			},
			multiple: true,
			targetName: 'pictures',
			fileTypes: 'image/jpg,image/jpeg,image/png',					// 限制文件格式(相应格式后缀名)，多个以','分割
			maxSize: 5 * 1024,							// 限制单文件大小					
			maxLength: 24,								// 限制文件个数
			mainSetting: true,
			mainSettingPlaceholder: '设为封面图片',
			mainSettingText: '封面图片',
			mainSettingHandler: function(e, uploader) {
				$('#coverUrl').val(uploader.find('.hidden-value').val());
			},
			uploaderError : function(detailMsg, simpleMsg) {
				top.layer.alert(simpleMsg);
			},
			uploaderBtnClass: 'btn btn-success',
			/* mode: 'readonly', */
			closeHandler : function(uploader) {
				var $img = uploader.find('.file-preview').find('img');
				if($img.length > 0) {
					var src = $img.attr('_src');
					var coverUrl = $("#coverUrl").val();
					if(src == coverUrl) {
						$('#coverUrl').val('');
					}
				}
			}
		});
	</script>
</body>
</html>