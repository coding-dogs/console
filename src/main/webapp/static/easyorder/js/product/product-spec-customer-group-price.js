/**
 * 
 */

/* 获取已选客户组ID */
function getCustomerGroupIds() {
	var customerGroupIds = [];
	var $trs = $("#customerGroupPrice").find('tbody tr');
	if($trs && $trs.length > 0) {
		$.each($trs, function(index, tr) {
			var $tr = $(tr);
			var id = $tr.attr('data-id');
			customerGroupIds.push(id);
		});
	}
	return customerGroupIds;
}

$('#customerGroupIds').val(getCustomerGroupIds());

/* 选择客户组事件，打开新窗口进行客户组选中，选中后绘制到客户组指定价的表格中进行客户组指定价设置 */
$('#customerGroupSelector').on('click', function(e) {
	openDialogWithCallback('选择客户组', ctx + '/customerManager/customerGroup/selector?type=checkbox&productSpecificationId=' + productSpecificationId, '800px', '600px', null, function(index, layero) {
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
     	var fieldMapping = {
     		"name" : "customerGroupName"
     	};
     	$.each(records, function(idx, record) {
     		var $findTr = $tbody.find('tr[data-id="' + record.id + '"]');
     		if($findTr.length != 0) {
     			return true;
     		}
     		var $tr = $('<tr data-id="' + record.id + '">');
     		$.each(fields, function(i, field) {
     			var $td = $('<td>');
     			$td.attr("data-type", fieldMapping[field] ? fieldMapping[field] : field);
     			$td.html(record[field]);
     			$td.appendTo($tr);
     		});
     		var $td = $('<td>');
     		$td.attr("data-type", "orderPrice");
     		var $inputPrice = $('<input name="customerGroupPrice[' + record.id + ']" type="number" maxlength="20" class="form-control required">');
     		$inputPrice.appendTo($td);
     		$td.appendTo($tr);
     		var $operateTd = $('<td>');
     		var $remove = $('<span class="fa fa-times" aria-hidden="true">');
     		$remove.appendTo($operateTd);
     		$operateTd.appendTo($tr);
     		
     		$remove.on('click', function(e) {
     			var $ptr = $(this).parents('tr');
     			if($ptr.length > 0) {
     				$ptr.remove();
     				$('#customerGroupIds').val(getCustomerGroupIds());
     			}
     		});
     		
     		$tr.appendTo($tbody);
     	});
     	$('#customerGroupIds').val(getCustomerGroupIds());
     	top.layer.close(index);
	});
});