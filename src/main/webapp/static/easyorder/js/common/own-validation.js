/**
 * 
 */

$.validator.addMethod('specificationNo', function(val, element) {
	var _this = $(element);
	console.log('_value = ' + val);
	var parent = _this.parents('tr');
	console.log('parent length = ' + parent.length);
	if(parent.length == 0) { 
		return true;
	}
	var sibs = parent.siblings('.sub-spec-data');
	console.log('sibs length = ' + sibs.length);
	if(sibs.length == 0 ) {
		return true;
	}
	var pass = true;
	$.each(sibs,function(index, sib){ 
		var subNoInput = $(sib).find('input.sub-spec-no');
		if(subNoInput.length == 0) {
			return true;
		}
		var tvalue = subNoInput.val();
		console.log('current tvalue = ' + tvalue);
		if(tvalue == val) {
			pass = false;
			return false;
		}
	});
	if(!pass) {
		return false;
	}
	return true;
}, '此值重复不可用');