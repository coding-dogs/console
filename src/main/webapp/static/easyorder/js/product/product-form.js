$(".fa-times").on('click', function(e) {
	$(this).parents('tr').remove();
});


$('#customerSelector').on('click', function(e) {
	openDialogWithCallback('选择客户', ctx + '/customerManager/customer/selector?type=customerPrice&productId=' + productId, '800px', '600px', null, function(index, layero) {
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
     		var $findTr = $tbody.find('tr[data-id="' + record.id + '"]');
     		if($findTr.length != 0) {
     			return true;
     		}
     		var $tr = $('<tr data-id="' + record.id + '">');
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
	openDialogWithCallback('选择客户组', ctx + '/customerManager/customerGroup/selector?type=customerGroupPrice&productId=' + productId, '800px', '600px', null, function(index, layero) {
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
     		var $findTr = $tbody.find('tr[data-id="' + record.id + '"]');
     		if($findTr.length != 0) {
     			return true;
     		}
     		var $tr = $('<tr data-id="' + record.id + '">');
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
	
	
// 页签组件的使用
$('#tabs-content').easyTabs({
	selectedId: '#baseInfo',				// 指定要选中"小页面"的id属性
	targetElement: '#ibox-content'			// 页签元素们的父元素
});

// UEditor编辑器使用
var descEditor = UE.getEditor('desc');
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
UE.Editor.prototype.getActionUrl = function(action) {
	// 调用后台用于UEditor上传图片的方法
	if(action == 'uploadimage') {
		return ctx + "/upload/image?utype=product"
	} else {
         return this._bkGetActionUrl.call(this, action);
    }
}
// 处理内容长度问题
UE.Editor.prototype.getContentLength = function() {
    return this.getContent().length;
}

/**
 * 获取单位数据
 */
function getUnits() {
	var units = [];
	$.ajax({
		url : ctx + "/productManager/unit/async/list",
		async : false,
		type : "GET",
		data : {},
		dataType : "json",
		success : function(data) {
			var _item = [];
			if(SUCCESS_CODE === data.code) {
				if(data.result && data.result.length > 0) {
					$.each(data.result, function(index, unit) {
						var item = {};
						item.value = unit.id;
						item.text = unit.unit;
						units.push(item);
					});
				}
			}
		}
	});	
	return units;
}

/**
 * 获取商品分类数据
 */
function getProductCategory() {
	var items = [];
	$.ajax({
		url : ctx + "/productManager/productCategory/tree",
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
var units = getUnits();
$("#unit").easySelector({
	maxHeight : 250,
	hasMore: true,
	defaults: {
		value : '',
		text : '请选择商品单位',
		selected: true
	},
	moreText : "<span style='text-weight:bold'> + </span> 新增单位",
	items: units,
	moreCallback: function() {
		openDialogWithCallback('新增单位', ctx + '/productManager/unit/form','800px', '500px', null, function(index, layero) {
			var body = top.layer.getChildFrame('body', index);
			var inputForm = body.find("#inputForm");
			var unit = inputForm.find("#unit").val();
			var remarks = inputForm.find("#remarks").val();
			// 保存客户组
			$.ajax({
				url : ctx + "/productManager/unit/async/save",
				type : "POST",
				data : {"unit" : unit, "remarks" : remarks},
				dataType : "JSON",
				success : function(data) {
					if(SUCCESS_CODE === data.code) {
						// 更新选择项
						$("#unit").easySelector("setOptions", "items", getUnits());
						// 选择当前值
						$("#unit").easySelector("select", data.result);
					} else {
						top.layer.alert(data.msg);
					}
					top.layer.close(index);
				},
				error : function(XMLHttpRequest, errorMsg, ex) {
					top.layer.alert("单位新增失败");
					top.layer.close(index);
				}
			});
		});
	}
});
// 构建商品分类选择器
$("#productCategory").easySelector({
	type : 'tree',
	maxHeight : 250,
	hasMore : true,
	defaults: {
		value : '',
		text : '请选择商品分类',
		selected: true
	},
	moreText : "<span style='text-weight:bold'> + </span> 新增商品分类",
	items : items,
	moreCallback : function() {
		openDialogWithCallback('新增商品分类', ctx + '/productManager/productCategory/form','800px', '500px', null, function(index, layero) {
			var body = top.layer.getChildFrame('body', index);
			var inputForm = body.find("#inputForm");
			var name = inputForm.find("#name").val();
			var remarks = inputForm.find("#remarks").val();
			var pid = inputForm.find("#pId").val();
			var sort = inputForm.find("#sort").val();
			// 保存客户组
			$.ajax({
				url : ctx + "/productManager/productCategory/async/save",
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
					top.layer.alert("商品类目新增失败");
					top.layer.close(index);
				}
			});
			
		});
	}
});

function getProductBrands() {
	var items = [];
	$.ajax({
		url : ctx + "/productManager/productBrand/all",
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
// 构建商品品牌选择器
$("#productBrand").easySelector({
	maxHeight : 250,
	hasMore : true,
	defaults: {
		value : '',
		text : '请选择商品品牌',
		selected: true
	},
	moreText : "<span style='text-weight:bold'> + </span> 新增商品品牌",
	items : items,
	moreCallback : function() {
		openDialogWithCallback('新增商品品牌', ctx + '/productManager/productBrand/form','800px', '500px', null, function(index, layero) {
			var body = top.layer.getChildFrame('body', index);
			var inputForm = body.find("#inputForm");
			var name = inputForm.find("#name").val();
			var simpleName = inputForm.find("#simpleName").val();
			var sort = inputForm.find("#sort").val();
			var remarks = inputForm.find("#remarks").val();
			// 保存客户组
			$.ajax({
				url : ctx + "/productManager/productBrand/async/save",
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
					top.layer.alert("商品品牌新增失败");
					top.layer.close(index);
				}
			});
			
		});
	}
});



function getSpecifications() {
	var items = [];
	$.ajax({
		url: ctx + "/productManager/specification/async/list",
		async: false,
		type: "GET",
		dataType: "json",
		success: function(data) {
			if(SUCCESS_CODE == data.code && data.result && data.result.length != 0){
				$.each(data.result, function(index, item) {
					var _item = {};
					_item.text = item.name;
					_item.value = item.id;
					_item.data = item.items;
					items.push(_item);
				});
			}
		}
	});
	return items;
};

var specItems = getSpecifications();

// 构建多规格选择器
function buildEasySelector(ele) {
	$(ele).easySelector({
		maxHeight : 250,
		hasMore : true,
		moreText : "<span style='text-weight:bold'> + </span> 新增多规格",
		defaults: {
			value : '',
			text : '请选择商品多规格',
			selected: true
		},
		items : specItems,
		moreCallback : function() {
			openDialogWithCallback('新增多规格', ctx + '/productManager/specification/form','800px', '500px', null, function(index, layero) {
				var body = top.layer.getChildFrame('body', index);
				var inputForm = body.find("#inputForm");
				var name = inputForm.find("#name").val();
				var no = inputForm.find("#no").val();
				var iframeWin = layero.find('iframe')[0]
				var data = (JSON.stringify(iframeWin.contentWindow.wrapJson()));
				// 保存多规格
				$.ajax({
					url : ctx + "/productManager/specification/async/save",
					type : "POST",
					data : {"name" : name, "no" : no, "data" : data},
					dataType : "JSON",
					success : function(data) {
						if(SUCCESS_CODE === data.code) {
							// 更新选择项
							$(ele).easySelector("setOptions", "items", getSpecifications());
							// 选择当前值
							$(ele).easySelector("select", data.result);
						} else{
							top.layer.alert(data.msg);
						}
						top.layer.close(index);
					},
					error : function() {
						top.layer.alert("新增多规格失败");
						top.layer.close(index);
					}
				});
				
			});
		},
		selectedCallback: function($selector) {
			var $selected = $selector.find('.menu-wrapper ul li.selected');
			if($selected.length == 0) {
				return;
			}
			var $ss = $selector.parents('.spec-selector');
			var $checks = $ss.find('.spec-sub-check');
			if($checks.length != 0) {
				$checks.html('');
			}
			var id = $selected.attr('data-id');
			var sibs = $ss.siblings();
			$selector.attr('data-no', '');
			if(id) {
				$.ajax({
					url: ctx + "/productManager/specification/detail",
					async: false,
					data: {id: id},
					type: "GET",
					dataType: "json",
					success: function(data) {
						if(SUCCESS_CODE == data.code && data.result) {
							$selector.attr('data-no', data.result.no);
							$.each(data.result.items, function(index, item) {
								var $label = $('<label class="easy-form-span">');
								var $icheck = $('<input class="form-control i-checks" type="checkbox"/>');
								$label.appendTo($checks);
								$icheck.appendTo($label);
								$label.append(' ' + item.name);
								
								$icheck.attr('data-no', item.no);
								$icheck.attr('data-id', item.id);
								
								$icheck.iCheck({
						            checkboxClass: 'icheckbox_flat-green',
						            radioClass: 'iradio_flat-green',
						        });
							});
							
							$checks.find('.i-checks').on('ifChanged', function(e) {
								var $specSelector = $('.spec-selector');
								if($specSelector.length == 0) {
									return;
								}
								var item1;
								var item2;
								$.each($specSelector, function(index, s) {
									var $s = $(s);
									var no = $s.find('.easy-selector').attr('data-no');
									var name = $s.find('.easy-selector').find('span[role="text-container"]').text();
									var id = $s.find('.easy-selector').find('[role="value-container"]').val();
									var item = {id: id, name: name, no: no};
									item.children = [];
									var $ichecks = $s.find('.i-checks');
									if($ichecks.length > 0) {
										$.each($ichecks, function(index, icheck) {
											var $icheck = $(icheck);
											if($icheck.is(':checked')) {
												var dataNo = $icheck.attr('data-no');
												var dataId = $icheck.attr('data-id');
												var dataName = $icheck.parents('label').text();
												item.children.push({name: dataName, no: dataNo, id: dataId});
											}
										});
									}
									if(index == 0) {
										item1 = item;
									} else if(index == 1) {
										item2 = item;
									}
								});
								createSpecTable(item1, item2);
							});
						}
					}
				});
			}
		}
	});
}


// 后台获取图片信息，用于回显
var pictureDatas = [];
$.ajax({
	url: ctx + '/productManager/productPicture/async/list',
	type: 'GET',
	data: {productId : productId},
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
	uploadUrl : ctx + "/upload/product/file",
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
	multiple: true,									// 开启多文件上传
	targetName: 'pictures',							// 后台接收文件对象属性的属性名
	fileTypes: 'image/jpg,image/jpeg,image/png',	// 限制文件格式(相应格式后缀名)，多个以','分割
	maxSize: 5 * 1024,								// 限制单文件大小					
	maxLength: 24,									// 限制文件个数
	mainSetting: true,								// 是否需要封面图片设置位
	mainSettingPlaceholder: '设为封面图片',			// 封面文件设置位提示语
	mainSettingText: '封面图片',						// 封面文件设置位设置后提示文本
	mainSettingHandler: function(e, uploader) {		// 设置为封面文件后的函数，uploader为上传位jquery元素，审查元素即可知晓
		$('#coverUrl').val(uploader.find('.hidden-value').val());
	},
	uploaderError : function(detailMsg, simpleMsg) {// 上传过程出错处理函数，detailMsg为英文错误提示，simpleMsg为简体中文错误提示
		top.layer.alert(simpleMsg);
	},
	uploaderBtnClass: 'btn btn-success',			// 上传按钮样式
	/* mode: 'readonly', */
	closeHandler : function(uploader) {				// 点击图片右上角的移除按钮时的处理函数
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

function buildSpecSelectorAll(specs) {
	// 若为设置过多规格直接生成两个
	if(!specs || specs.length == 0) {
		buildSpecSelector({});
		buildSpecSelector({});
		return;
	}
	
	function isExist(str, array) {
		var exists = false;
		if(!str || !array || array.length == 0) {
			return exists;
		}
		$.each(array, function(index, arr) {
			if(arr == str) {
				exists = true;
				return false;
			}
		});
		return exists;
	}
	
	function initTable(specs) {
		var $manager = $('#variousSpecManager');
		if($manager.length == 0) {
			return;
		}
		// 获取相关表头数据
		var $ss = $manager.find('.spec-selector');
		if($ss.length == 0) {
			return;
		}
		var firstItem, secondItem;
		$.each($ss, function(index, sItem) {
			var $sItem = $(sItem);
			var $tc = $sItem.find('span[role="text-container"]');
			var $vc = $sItem.find('input[role="value-container"]');
			if($vc.val()) {
				var item = {id: $vc.val(), name: $tc.text(), children: []};
				var $formSpans = $sItem.find('.spec-sub-check .easy-form-span');
				var $checks = $formSpans.find('.i-checks:checked');
				$.each($checks, function(i, c) {
					var $icheck = $(c);
					var name = $icheck.parents('.easy-form-span').text();
					var no = $icheck.attr('data-no');
					var id = $icheck.attr('data-id');
					var obj = {id: id, name: name, no: no};
					item.children.push(obj);
				});
				if(index == 0) {
					firstItem = item;
				} else {
					secondItem = item;
				}
				createSpecTable(firstItem, secondItem, specs);
			}
		});
		
	}
	
	var path = specs[0].specificationItemPath;
	$.ajax({
		url: ctx + '/productManager/specification/details',
		type: 'GET',
		data: {itemIds : path},
		dataType: 'JSON',
		success: function(data) {
			if(SUCCESS_CODE == data.code) {
				$.each(data.result, function(index, spec){ 
					var selectedItems = [];
					$.each(specs,function(idx, spe) {
						var itemPath = spe.specificationItemPath.split(',');
						if(!isExist(itemPath[index], selectedItems)) {
							selectedItems.push(itemPath[index]);
						}
					});
					buildSpecSelector(spec, selectedItems);
				});
				if(data.result.length <= 1) {
					buildSpecSelector({});
				}
				initTable(specs);
			}
		},
		error: function() {
			
		}
	});
	
	
	
}

function buildSpecSelector(spec, selectedItems) {
	var $ss = $('<div class="spec-selector">');
	var $ssHeader = $('<div class="spec-selector-header clearfix">');
	var $ssBody = $('<div class="spec-selector-body">');
	
	$ssHeader.appendTo($ss);
	$ssBody.appendTo($ss);
	
	var $ssHeaderLeft = $('<div class="col-sm-4 pull-left">');
	var $ssHeaderRight = $('<div class="pull-right spec-selector-remove">');
	$ssHeaderLeft.appendTo($ssHeader);
	$ssHeaderRight.appendTo($ssHeader);
	
	var $sgs = $('<div class="specGroupSelector" data-value="' + (spec.id ? spec.id : '') + '" data-text="' + (spec.name ? spec.name : '') + '"></div>');
	$sgs.appendTo($ssHeaderLeft);
	buildEasySelector($sgs);
	
	var $ssc = $('<div class="spec-sub-check easy-form-ichecks">');
	$ssc.appendTo($ssBody);
	$ss.appendTo($('#variousSpecArea'));
	
	// 直接选择当前值，以便于生成多选框
	$sgs.easySelector('select', spec.id);
	var $specSelectors = $('.spec-selector');
	$.each($specSelectors, function(i, specSelector) {
		var $specSelector = $(specSelector);
		var $easySelector = $specSelector.find('.easy-selector');
		var id = $easySelector.easySelector('getValue');
		var sibs = $specSelector.siblings();
		if(sibs.length != 0) {
			$.each(sibs, function(index, sib) {
				var $sib = $(sib);
				var $select = $sib.find('.easy-selector');
				var disabled = [];
				disabled.push(id);
				$select.easySelector('setOptions', 'disabled', disabled);
			});
		}
	});
	
	if(selectedItems && selectedItems.length != 0) {
		$.each(selectedItems, function(index, selectedItem) {
			var $label = $ss.find('.easy-form-span');
			if($label.length != 0) {
				$.each($label, function(ix, l) {
					var $l = $(l);
					var dataId = $l.find('.i-checks').attr('data-id');
					if(dataId == selectedItem) {
						$l.find('.i-checks').prop('checked', true);
						$l.find('div').addClass('checked');
					}
				});
			}
			
		});
	}
}

// 开启多规格
$('#openMultiple').on('ifChanged', function(e) {
	var isChecked = $(this).is(':checked');
	var $selectors = $('#variousSpecArea').find('.spec-selector');
	if($selectors.length != 0) {
		$.each($selectors, function(index, selector) {
			if(isChecked) {
				$(selector).show();
			} else { 
				$(selector).hide();
			}
		});
	}
	if(isChecked) {
		$('#variousSpecManager').find('.spec-table').show();
	} else {
		$('#variousSpecManager').find('.spec-table').hide();
	}
});

var specJson = productSpecJson;
if(specJson) {
	specJson = JSON.parse(specJson);
}

buildSpecSelectorAll(specJson);

$('#variousSpecArea').find('.spec-selector').hide();

if(specJson) {
	$('#openMultiple').iCheck('check');
}

/**
 * 创建规格组合表格
 * @param firstItem
 * @param secondItem
 * @returns
 */
function createSpecTable(firstItem, secondItem, specs) {
	var $manager = $('#variousSpecManager');
	if($manager.length == 0) {
		return;
	}
	
	if(firstItem.children && firstItem.children.length == 0 && secondItem.children && secondItem.children.length > 0) {
		var tempItem = firstItem;
		firstItem = secondItem;
		secondItem = tempItem;
	}
	
	if($manager.find('.spec-table').length > 0) {
		$manager.find('.spec-table').remove();
	}
	var $table = $('<table class="spec-table table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">');
	var $headerTr = $('<tr class="spec-theader">');
	
	var $f = $('<th data-id="' + firstItem.id + '">');
	$f.text(firstItem.name);
	$f.appendTo($headerTr);
	
	var hasSecond = secondItem && secondItem.children && secondItem.children.length > 0;
	
	if(hasSecond) {
		var $s = $('<th data-id="' + secondItem.id + '">');
		$s.text(secondItem.name);
		$s.appendTo($headerTr);
	}
	
	$headerTr.appendTo($table);
	
	var $no = $('<th data-attr-name="no">');
	$no.text('规格编号');
	$no.appendTo($headerTr);
	
	var $opera = $('<th>');
	$opera.text('操作');
	$opera.appendTo($headerTr);
	$table.appendTo($manager);
	
	var paths = [];
	if(specs && specs.length != 0) {
		$.each(specs, function(ix, spec) {
			paths.push(spec.specificationItemPath);
		});
	}
	
	$.each(firstItem.children, function(index, fi) {
		var $tbodyTr = $('<tr class="spec-tbody">');
		var $fv = $('<td data-is-spec="true" data-id="' + fi.id + '">');
		if(hasSecond) {
			$.each(secondItem.children, function(index2, si) {
				var $tbodyTr = $('<tr class="spec-tbody">');
				$fv = $('<td data-is-spec="true" data-id="' + fi.id + '">');
				$fv.text(fi.name);
				$fv.appendTo($tbodyTr);
				var $sv = $('<td data-is-spec="true" data-id="' + si.id + '">');
				$sv.text(si.name);
				$sv.appendTo($tbodyTr);
				var $nov = $('<td data-attr-name="specificationNo">');
				var $novInput = $('<input type="text" class="form-control required">');
				
				var specNo = productNo + fi.no + si.no;
				var comPath = fi.id + "," + si.id;
				if(specs && specs.length != 0) {
					var existIndex = existsOfArray(comPath, paths);
					if(existIndex != -1) {
						specNo = specs[existIndex].specificationNo;
					} else {
						return;
					}
				}
				$novInput.val(specNo);
				$novInput.appendTo($nov);
				$nov.appendTo($tbodyTr);
				
				var $ope = $('<td class="spec-opera">');
				var $remove = $('<a href="javascript:void(0);">');
				var $removeI = $('<i class="fa fa-times">');
				$removeI.appendTo($remove);
				$remove.appendTo($ope);
				$ope.appendTo($tbodyTr);
				
				$remove.on('click', function(e) {
					$tbodyTr.remove();
				});
				$tbodyTr.appendTo($table);
			});
		} else {
			$fv.text(fi.name);
			$fv.appendTo($tbodyTr);
			var $nov = $('<td data-attr-name="specificationNo">');
			var $novInput = $('<input type="text" class="form-control required">');
			$novInput.val(productNo + fi.no);
			$novInput.appendTo($nov);
			$nov.appendTo($tbodyTr);
			
			var $ope = $('<td class="spec-opera">');
			var $remove = $('<a href="javascript:void(0);">');
			var $removeI = $('<i class="fa fa-times">');
			$removeI.appendTo($remove);
			$remove.append($ope);
			$ope.appendTo($tbodyTr);
			
			$remove.on('click', function(e) {
				$tbodyTr.remove();
			});
			$tbodyTr.appendTo($table);
		}
		
	});
}

function getSpec4Json() {
	var $specTable = $('.spec-table');
	var isOpen = $('#openMultiple').is(':checked');
	var specData = null;
	if($specTable.length == 0 || !isOpen) {
		return specData;
	}
	var $specTbody = $specTable.find('.spec-tbody');
	var $specTheader = $specTable.find('.spec-theader');
	if($specTbody.length == 0) {
		return specData;
	}
	specData = [];
	$.each($specTbody, function(index, item) {
		var $tr = $(item);
		var $tds = $tr.find('td');
		var sdItem = {};
		var specificationItemPath = '';
		$.each($tds, function(idx, td) {
			var $td = $(td);
			if($td.hasClass('spec-opera')) {
				return true;
			}
			var isSpec = $td.attr('data-is-spec');
			if(isSpec == 'true') {
				var dataId = $td.attr('data-id');
				specificationItemPath = specificationItemPath + "," + dataId;
			}
			
			var attrName = $td.attr('data-attr-name');
			if(attrName == 'specificationNo') {
				sdItem.specificationNo = $td.find('input').val();
			}
			
		});
		
		if(specificationItemPath) {
			specificationItemPath = specificationItemPath.substring(1);
		}
		sdItem.specificationItemPath = specificationItemPath;
		specData.push(sdItem);
	});
	return specData;
}