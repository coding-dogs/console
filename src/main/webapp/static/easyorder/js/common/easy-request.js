/**
 * 
 */
var easyorder = window.easyorder || easyorder || {};

easyorder.request = function(url, type, data, async, successCallback, errorCallback) {
	$.ajax({
		url: ctx + '/' + url,
		type: type,
		data: data,
		async: async === false ? false : true,
		dataType: 'JSON',
		success: function(data) {
			if(typeof successCallback === 'function') {
				successCallback(data);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			if(typeof errorCallback === 'function') {
				errorCallback(XMLHttpRequest, textStatus, errorThrown);
			}
		}
	});
}

easyorder.get = function(url, data, async, successCallback, errorCallback) {
	easyorder.request(url, 'GET', data, async, successCallback, errorCallback);
}

easyorder.post = function(url, data, async, successCallback, errorCallback) {
	easyorder.request(url, 'POST', data, async, successCallback, errorCallback);
}

easyorder.put = function(url, data, async, successCallback, errorCallback) {
	easyorder.request(url, 'PUT', data, async, successCallback, errorCallback);
}

easyorder.del = function(url, data, async, successCallback, errorCallback) {
	easyorder.request(url, 'DELETE', data, async, successCallback, errorCallback);
}