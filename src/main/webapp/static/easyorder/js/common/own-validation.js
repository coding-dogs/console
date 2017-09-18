/**
 * 
 */

$.validator.addMethod('specificationNo', function(val, element) {
	var _this = $(element);
	var parent = _this.parents('tr');
	if(parent.length == 0) { 
		return true;
	}
	var sibs = parent.siblings('.sub-spec-data');
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


// 手机号校验
$.validator.addMethod('phoneNo', function(val, element) {
	var phoneRegex = /^1[3|4|5|7|8][0-9]{9}$/;
	return this.optional(element) || (phoneRegex.test(val));
}, '手机号码格式不正确');

//邮政编码验证   
jQuery.validator.addMethod("zipCode", function(val, element) {   
    var zipCode = /^[0-9]{6}$/;
    return this.optional(element) || (zipCode.test(val));
}, "请正确填写邮政编码");