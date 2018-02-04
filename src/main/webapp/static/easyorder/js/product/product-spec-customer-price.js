
/* 获取已选客户ID */
function getCustomerIds() {
	var customerIds = [];
	var $trs = $("#customerPrice").find('tbody tr');
	if($trs && $trs.length > 0) {
		
		$.each($trs, function(index, tr) {
			var $tr = $(tr);
			var id = $tr.attr('data-id');
			customerIds.push(id);
		});
	}
	return customerIds;
}

$('#customerIds').val(getCustomerIds());


/* 选择客户事件，打开新窗口进行客户选中，选中后绘制到客户指定价的表格中进行客户指定价设置 */
$('#customerSelector').on('click', function(e) {
	openDialogWithCallback('选择客户', ctx + '/customerManager/customer/selector?type=checkbox&productSpecificationId=' + productSpecificationId, '800px', '600px', null, function(index, layero) {
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
     			$td.attr("data-type", field);
     			$td.html(record[field]);
     			$td.appendTo($tr);
     		});
     		var $td = $('<td>');
     		$td.attr("data-type", "orderPrice");
     		var $inputPrice = $('<input name="customerPrice[' + record.id + ']" type="number" maxlength="20" class="form-control required">');
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
     				$('#customerIds').val(getCustomerGroupIds());
     			}
     		});
     		
     		$tr.appendTo($tbody);
     	});
     	$('#customerIds').val(getCustomerIds());
     	top.layer.close(index);
	});
});