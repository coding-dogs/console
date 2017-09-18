/**
 * create by qiudequan on 2017-06-22
 * 上传插件easy-uploader
 * 目前多文件上传方式为：轮询各个图片进行逐个上传
 * 未完善细节：
 * 		1.上传位宽高设置
 * 		2.上传前回调函数调用
 * 		3.预览功能仅支持html image标签允许的图片格式
 * 		4.默认显示功能
 */

/**
 * 改进记录
 * 修改上传方式--由原有的选择文件即上传修改为选中文件后预览，点击上传按钮后上传
 * 修改预览图片为base64数据文件进行预览
 * 新增上传状态提示位
 */

(function($, win){
	// 默认参数
	var defaults = {
			config: {
				width: 100,								// 上传位宽度(自适应暂未实现，不建议修改)
				height: 100								// 上传位高度(自适应暂未实现，不建议修改)
			},
			datas: [],									// 已上传图片数据，供回显
			requestData: [],
			targetName : '',							// 目标表单域name属性名
			uploadUrl : '',								// 后台上传路径 
			multiple: false,							// 是否开启多文件上传
			fileTypes: '',								// 支持的文件类型，多个以','隔开
			maxSize: -1,								// 单文件大小限制(单位:KB)，-1:不限制，其他则为限制大小。
			maxLength: 10,								// 最大上传个数
			beforeSend: function() {},					// 上传前回调，暂不实现
			success: function(data) {},					// 上传成功回调，必须返回上传后的文件路径
			error: function() {},						// 错误回调函数
			mainSetting: false,							// 是否开启封面文件设置位
			mainSettingPlaceholder: '设为主图',			// 封面文件设置位提示文字
			mainSettingText: '已设为主图',					// 封面文件设置位设置成功后提示文字
			mainSettingHandler: function() {},			// 封面文件设置位设置回调函数
			uploaderError: function(detailMsg, simpleMsg) {
				throw new Error(detailMsg);
			},
			uploaderBtnClass : '',
			uploaderBtnText : '上传',
			mode: 'normal',
			closeHandler : function(uploader) {
				
			}
	},
	_keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789',
	UPLOADER_CONTAINER_CLASS = 'easy-uploader-container',
	UPLOADER_CLASS = 'easy-uploader',
	FILE_SELECTOR_CLASS = 'file-selector',
	PLUS_CLASS = 'plus',
	PREVIEW_CLASS = 'file-preview',
	MAIN_SETTING_CLASS = 'main-setting',
	MAIN_SETTING_PLACEHOLDER_CLASS = 'main-setting-placeholder',
	MAIN_SETTING_TEXT_CLASS = 'main-setting-text',
	HIDDEN_VALUE_CLASS = 'hidden-value',
	CLASS_FLAG = '.',
	CLOSE_CLASS = 'easy-uploader-close',
	UPLOADER_TIP_CLASS = 'easy-uploader-tip',
	UPLOADER_BIT_CLASS = 'easy-uploader-bit',
	UPLOADER_BTNS_CLASS = 'easy-uploader-btns',
	UPLOAD_INIT = 'initial',
	UPLOAD_CHANGED = 'changed',
	UPLOAD_SUCCESS = 'success',
	UPLOAD_FAILED = 'failed';

	/**
	 * 上传器对象
	 * @param element
	 * @param options
	 * @returns
	 */
	function EasyUploader(element, options) {
		this.element = element;
		this.$element = $(element);

		this.options = $.extend({}, defaults, options);
		// 必填参数校验
		this.checkParam();
		// 进行初始化
		this._init();

	}

	EasyUploader.fn = EasyUploader.prototype;

	/**
	 * 构建上传器时的参数校验
	 */
	EasyUploader.prototype.checkParam = function() {
		var that = this;
		if(!that.options.uploadUrl) {
			that.options.uploaderError('the param[uploaderUrl] of options is empty.', '文件上传地址为空');
			return;
		}
		
		if(that.options.datas && that.options.datas.length > that.options.maxLength) {
			that.options.uploaderError('the length of options.datas more than the options.maxLength', '已选图片数大于最大上传位限制');
			return;
		}
	}

	/**
	 * 初始化函数，主要处理参数校验，上传插件根节点绘制及时间绑定
	 */
	EasyUploader.prototype._init = function() {
		var that = this;
		if(!that.$element.hasClass(UPLOADER_CONTAINER_CLASS)) {
			that.$element.addClass(UPLOADER_CONTAINER_CLASS);
		}

		var $bit = $('<div>');
		$bit.addClass(UPLOADER_BIT_CLASS);
		$bit.appendTo(that.$element);

		if(that.options.mode != 'readonly') {
			var $btns = $('<div class="easy-uploader-clearfix">');
			var $btn = $('<button type="button">');
			$btn.text(that.options.uploaderBtnText);
			$btns.addClass(UPLOADER_BTNS_CLASS);
			$btn.appendTo($btns);
			$btns.appendTo(that.$element);
			if(that.options.uploaderBtnClass) {
				$btn.addClass(that.options.uploaderBtnClass);
			}
			that.$btns = $btns;
			$btn.on('click', function(e) {
				var $uploaders = $bit.find(CLASS_FLAG + UPLOADER_CLASS);
				if($uploaders.length > 0) {
					$uploaders.each(function(index, uploader) {
						var $uploader = $(uploader);
						var dataUpload = $uploader.attr('data-upload');
						if(dataUpload == UPLOAD_SUCCESS) {
							return true;			// continue;
						}
						var $img = $uploader.find(CLASS_FLAG + PREVIEW_CLASS).find('img');
						if($img.length > 0) {
							var f = $img.data('binary');
							var fileUrl = that.upload(index + 1, f);
							var $tip = $uploader.find(CLASS_FLAG + UPLOADER_TIP_CLASS);
							var removeCls = 'error',
								addCls = 'success',
								tipText = '已上传';
							if(fileUrl) {
								$uploader.attr('data-upload', UPLOAD_SUCCESS);
								$img.attr('_src', fileUrl);
								var hasMainSetting = that.options.mainSetting;
								if(hasMainSetting) {
									var $mainSetting = $('<div>');
									var $mainSettingText = $('<span>');
									$mainSetting.addClass(MAIN_SETTING_CLASS);
									$mainSettingText.addClass(MAIN_SETTING_PLACEHOLDER_CLASS);

									$mainSettingText.html(that.options.mainSettingPlaceholder);

									$mainSettingText.appendTo($mainSetting);
									$mainSetting.appendTo($uploader);
									
									if(that.options.mode != 'readonly') {
										// 封面文件设置区域点击事件：回调封面文件设置处理函数，变更文字提示
										$mainSetting.on('click', function(e) {
											if($(e.target).hasClass(MAIN_SETTING_CLASS) || $(e.target).parents(CLASS_FLAG + MAIN_SETTING_CLASS)) {
												that.options.mainSettingHandler(e, $uploader);
												$uploader.find(CLASS_FLAG + MAIN_SETTING_PLACEHOLDER_CLASS).html(that.options.mainSettingText);
												$uploader.siblings().find(CLASS_FLAG + MAIN_SETTING_PLACEHOLDER_CLASS).html(that.options.mainSettingPlaceholder);
											}
										});
									}
								}
							} else {
								$uploader.attr('data-upload', UPLOAD_FAILED);
								removeCls = 'success';
								addCls = 'error';
								tipText = '上传失败';
							}
							
							if($tip.length == 0) {
								$tip = $('<div class="' + UPLOADER_TIP_CLASS + '">');
								$tip.appendTo($uploader);
							}
							$tip.html('');
							$tip.removeClass(removeCls).addClass(addCls);
							var $success = $('<span>');
							$success.html(tipText);
							$success.appendTo($tip);
						}
					});
				}
			});
			
			$bit.on('click', function(e) {
				if($(e.target).hasClass(UPLOADER_CLASS)){
					$(e.target).find(CLASS_FLAG + FILE_SELECTOR_CLASS).trigger('click');
				} else if($(e.target).hasClass(PLUS_CLASS) || $(e.target).hasClass(PREVIEW_CLASS) || $(e.target).parents(CLASS_FLAG + PREVIEW_CLASS).length > 0) {
					$(e.target).parents(CLASS_FLAG + UPLOADER_CLASS).find(CLASS_FLAG + FILE_SELECTOR_CLASS).trigger('click');
				}
			});
		}

		that.$bit = $bit;
		
		if(that.options.datas && that.options.datas.length > 0) {
			$.each(that.options.datas, function(index, data) {
				that.drawUploader(data.url, '', data.isMain, data.url);
			});
			if(that.options.mode != 'readonly' && that.options.datas.length < that.options.maxLength) {
				that.drawUploader();
			}
		} else {
			if(that.options.mode != 'readonly') {
				that.drawUploader();
			}
		}
	}

	/**
	 * 绘制文件上传位
	 */
	EasyUploader.prototype.drawUploader = function(src, type, isMain, alreadyUpload) {
		var that = this,
		$bit = that.$bit,
		$uploader = $('<div>'),
		$fileSelector = $('<input type="file">'),
		$hiddenValue = $('<input type="hidden">'),
		$plus = $('<span>'),
		$preview = $('<div>');
		// 开启多文件上传模式
		if(that.options.multiple) {
			$fileSelector.attr('multiple', 'multiple');
		}
		$plus.addClass(PLUS_CLASS);
		$uploader.addClass(UPLOADER_CLASS);
		$uploader.attr('data-upload', UPLOAD_INIT);
		$fileSelector.addClass(FILE_SELECTOR_CLASS);
		// 限制文件上传格式
		if(that.options.fileTypes) {
			$fileSelector.attr('accept', that.options.fileTypes);
		}
		$preview.addClass(PREVIEW_CLASS);
		// 设置目标表单域name属性
		if(that.options.targetName) {
			$hiddenValue.attr('name', that.options.targetName);
		}
		
		$hiddenValue.addClass(HIDDEN_VALUE_CLASS);
		// 预览元素绝对定位
		$preview.css({'position' : 'absolute'}).hide();

		// 获取已有上传位长度(即为上传位总个数)
		var index = that.$bit.attr('data-length');
		if(!index) {
			index = 0;
		} 
		index = parseInt(index) + 1;
		// 覆盖上传位个数限制
		$bit.attr('data-length', index);

		$hiddenValue.appendTo($uploader);
		$fileSelector.appendTo($uploader);
		$plus.appendTo($uploader);
		$preview.appendTo($uploader);
		$uploader.appendTo(that.$bit);

		// 若是绘制上传位时已传入文件url地址，那么直接绘制文件预览区域及封面图片设置区域
		if(src) {
			if(alreadyUpload) {
				$hiddenValue.val(src);
			}
			that._drawImage(index, src, type);
			that._drawMainSetting(index, isMain, alreadyUpload);
		}
		
		if(that.options.mode != 'readonly' && alreadyUpload) {
			$uploader.attr('data-upload', UPLOAD_SUCCESS);
			var $tip = $('<div class="' + UPLOADER_TIP_CLASS + '">');
			$tip.appendTo($uploader);
			$tip.addClass('success');
			var $success = $('<span>');
			$success.html('已上传');
			$success.appendTo($tip);
		}

		if(that.options.mode != 'readonly') {
			// 事件绑定
			that.bindEvents(index);
		}
		
	}

	/**
	 * 已有元素事件绑定
	 */
	EasyUploader.prototype.bindEvents = function(index) {
		var that = this,
		$bit = that.$bit,
		$preview = $bit.find(CLASS_FLAG + PREVIEW_CLASS).eq(index -1),
		$plus = $bit.find(CLASS_FLAG + PLUS_CLASS).eq(index -1),
		$uploader = $bit.find(CLASS_FLAG + UPLOADER_CLASS).eq(index - 1);
		// 绑定上传位鼠标移入移出事件
		// 移入：显示左上角的上传位移除图标(叉号)，点击叉号移除指定上传位
		// 移出：隐藏左上角的上传位移除图标(叉号)
		$uploader.hover(function(e) {
			e.stopPropagation();
			if($preview.find('img').length != 0) {
				var $close = $('<span>');
				$close.addClass(CLOSE_CLASS);
				$close.appendTo($uploader);

				$close.on('click', function(e) {
					e.stopPropagation();
					// 移除上传位时，上传位长度(总个数)减一
					var dataLength = $bit.attr('data-length');
					$uploader.remove();
					that.options.closeHandler($uploader);
					dataLength = parseInt(dataLength) - 1;
					$bit.attr('data-length', dataLength);
					// 判断是否需要添加新上传位(即没有上传过图片的上传位)
					var imgLength = $bit.find(CLASS_FLAG + PREVIEW_CLASS).find('img').length;
					var needAdd = dataLength < that.options.maxLength && imgLength == dataLength;

					if(dataLength == 0 || needAdd) {
						that.drawUploader();
					}

				});
			}
		}, function(e) {
			e.stopPropagation();
			// 存在上传位移除按钮，移出鼠标时移除
			if($uploader.find(CLASS_FLAG + CLOSE_CLASS).length > 0) {
				$uploader.find(CLASS_FLAG + CLOSE_CLASS).remove();
			}
		});

		// 绑定已选文件改变事件：进行重新预览及上传
		$bit.find(CLASS_FLAG + FILE_SELECTOR_CLASS).eq(index - 1).on('change', function(e) {
			var $ele = $(this);
			var files = $ele[0].files;
			// 判定选定的文件数加上已有上传位是否超出上传个数限制
			var fileLength = files.length;
			if(fileLength > 0) {
				var relength = that.options.maxLength - parseInt(that.$bit.attr('data-length')) + 1;
				if(relength < fileLength) {
					that.options.uploaderError('Upload number exceeded limit.', '上传个数超出限制');
					return;
				}
				
				// 清除提示位
				var $tip = $uploader.find(CLASS_FLAG + UPLOADER_TIP_CLASS);
				if($tip.length > 0) {
					$tip.remove();
				}
				
				$uploader.attr('data-upload', UPLOAD_CHANGED);

				if($preview.length > 0) {
					// 移除预览区域及封面图片设置位
					if($preview.length > 0) {
						$preview.html('');
						$preview.siblings(CLASS_FLAG + MAIN_SETTING_CLASS).remove();
					}
					$.each(files, function(i, file) {
						// 非当前上传位需要新绘制上传位，传入图片URL直接进行当前选定文件预览
						if(i != 0) {
							index++;
							that.drawUploader(file, 'base64');
						} else {
							var _index = $uploader.index() + 1;
							that._drawImage(_index, file, 'base64');
							that._drawMainSetting(_index);
						}

					});
					var $uploaders = that.$bit.find(CLASS_FLAG + UPLOADER_CLASS);
					var len = $uploaders.length;
					var pLen = $uploaders.find(CLASS_FLAG + PREVIEW_CLASS).find('img').length;
					var totalLength = parseInt($bit.attr('data-length'));
					
					if(len == totalLength && pLen == totalLength && totalLength < that.options.maxLength) {
						// 新增一个
						that.drawUploader();
					}
				}
			}
		});
	}

	// 绘制要上传的图片
	EasyUploader.prototype._drawImage = function (index, file, type) {
		var that = this;
		var $uploader = that.$bit.find(CLASS_FLAG + UPLOADER_CLASS).eq(index - 1);
		var $preview = that.$bit.find(CLASS_FLAG + PREVIEW_CLASS).eq(index - 1);
		var $plus = that.$bit.find(CLASS_FLAG + PLUS_CLASS).eq(index - 1);
		var $img = $('<img>');
		$img.appendTo($preview);
		if('base64' === type) {
			var reader = new FileReader();
	        reader.readAsDataURL(file);
			reader.onload = function(e) {
				$img.attr('src', this.result);
				$img.attr('_src', this.result);
			};
			$img.data('binary', file);
		} else {
			$img.attr('src', file);
			$img.attr('_src', file);
		}
		$img.css({'width' : that.options.config.width, 'height' : that.options.config.height});
		$preview.show();
		$plus.hide();
		$preview.siblings(CLASS_FLAG + FILE_SELECTOR_CLASS).removeAttr('multiple');
	}

	// 绘制封面图片设置区域
	EasyUploader.prototype._drawMainSetting = function (index, selected, alreadyUpload) {
		var that = this;
		var $uploader = that.$bit.find(CLASS_FLAG + UPLOADER_CLASS).eq(index - 1);
		if(that.options.mainSetting) {
			if(alreadyUpload) {
				var $mainSetting = $('<div>');
				var $mainSettingText = $('<span>');
				$mainSetting.addClass(MAIN_SETTING_CLASS);
				$mainSettingText.addClass(MAIN_SETTING_PLACEHOLDER_CLASS);

				$mainSettingText.html(that.options.mainSettingPlaceholder);

				$mainSettingText.appendTo($mainSetting);
				$mainSetting.appendTo($uploader);
				if(selected) {
					$mainSettingText.html(that.options.mainSettingText);
				}
				if(that.options.mode != 'readonly') {
					// 封面文件设置区域点击事件：回调封面文件设置处理函数，变更文字提示
					$mainSetting.on('click', function(e) {
						if($(e.target).hasClass(MAIN_SETTING_CLASS) || $(e.target).parents(CLASS_FLAG + MAIN_SETTING_CLASS)) {
							that.options.mainSettingHandler(e, $uploader);
							$uploader.find(CLASS_FLAG + MAIN_SETTING_PLACEHOLDER_CLASS).html(that.options.mainSettingText);
							$uploader.siblings().find(CLASS_FLAG + MAIN_SETTING_PLACEHOLDER_CLASS).html(that.options.mainSettingPlaceholder);
						}
					});
				}
			}
		}
	}

	/**
	 * 获取图片Blob二进制数据
	 */
	EasyUploader.prototype.getBlobBydataURI = function (dataURI,type) { 
	      var binary = atob(dataURI.split(',')[1]); 
	      var array = []; 
	      for(var i = 0; i < binary.length; i++) { 
	        array.push(binary.charCodeAt(i)); 
	      } 
	      return new Blob([new Uint8Array(array)], {type:type }); 
	    } 

	/**
	 * base64转换为Blob
	 */
	EasyUploader.prototype.base64ToBlob = function (data) {
		var bytes=window.atob(urlData.split(',')[1]);

		//处理异常,将ascii码小于0的转换为大于0  
		var ab = new ArrayBuffer(bytes.length);  
		var ua = new Uint8Array(ab);  
		for (var i = 0; i < bytes.length; i++) {  
			ua[i] = bytes.charCodeAt(i);  
		}  

		return new Blob([ab]);  
	}

	EasyUploader.prototype.base64Encode = function (input) {
		var that = this;
		var output = ""; 
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4; 
		var i = 0; 
		input = that._utf8_encode(input); 
		while (i < input.length) { 
			chr1 = input.charCodeAt(i++); 
			chr2 = input.charCodeAt(i++); 
			chr3 = input.charCodeAt(i++); 
			enc1 = chr1 >> 2; 
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4); 
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6); 
			enc4 = chr3 & 63; 
			if (isNaN(chr2)) { 
				enc3 = enc4 = 64; 
			} else if (isNaN(chr3)) { 
				enc4 = 64; 
			} 
			output = output + 
			_keyStr.charAt(enc1) + _keyStr.charAt(enc2) + 
			_keyStr.charAt(enc3) + _keyStr.charAt(enc4); 
		} 
		return output; 
	}

	EasyUploader.prototype.base64Decode = function (data) {
		var that = this;
		var output = ""; 
		var chr1, chr2, chr3; 
		var enc1, enc2, enc3, enc4; 
		var i = 0; 
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, ""); 
		while (i < input.length) { 
			enc1 = _keyStr.indexOf(input.charAt(i++)); 
			enc2 = _keyStr.indexOf(input.charAt(i++)); 
			enc3 = _keyStr.indexOf(input.charAt(i++)); 
			enc4 = _keyStr.indexOf(input.charAt(i++)); 
			chr1 = (enc1 << 2) | (enc2 >> 4); 
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2); 
			chr3 = ((enc3 & 3) << 6) | enc4; 
			output = output + String.fromCharCode(chr1); 
			if (enc3 != 64) { 
				output = output + String.fromCharCode(chr2); 
			} 
			if (enc4 != 64) { 
				output = output + String.fromCharCode(chr3); 
			} 
		} 
		output = that._utf8_decode(output); 
		return output; 
	}

	EasyUploader.prototype._utf8_encode = function (string) { 
		string = string.replace(/\r\n/g,"\n"); 
		var utftext = ""; 
		for (var n = 0; n < string.length; n++) { 
			var c = string.charCodeAt(n); 
			if (c < 128) { 
				utftext += String.fromCharCode(c); 
			} else if((c > 127) && (c < 2048)) { 
				utftext += String.fromCharCode((c >> 6) | 192); 
				utftext += String.fromCharCode((c & 63) | 128); 
			} else { 
				utftext += String.fromCharCode((c >> 12) | 224); 
				utftext += String.fromCharCode(((c >> 6) & 63) | 128); 
				utftext += String.fromCharCode((c & 63) | 128); 
			} 

		} 
		return utftext; 
	} 

	// private method for UTF-8 decoding 
	EasyUploader.prototype._utf8_decode = function (utftext) { 
		var string = ""; 
		var i = 0; 
		var c = c1 = c2 = 0; 
		while ( i < utftext.length ) { 
			c = utftext.charCodeAt(i); 
			if (c < 128) { 
				string += String.fromCharCode(c); 
				i++; 
			} else if((c > 191) && (c < 224)) { 
				c2 = utftext.charCodeAt(i+1); 
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63)); 
				i += 2; 
			} else { 
				c2 = utftext.charCodeAt(i+1); 
				c3 = utftext.charCodeAt(i+2); 
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)); 
				i += 3; 
			} 
		} 
		return string; 
	} 

	// 上传文件
	EasyUploader.prototype.upload = function(index, file) {
		var that = this;
		var picUrl;
		var $uploader = that.$bit.find(CLASS_FLAG + UPLOADER_CLASS).eq(index - 1);
		var formData = new FormData();
		// 判定欲上传文件格式是否正确
		if(that.options.fileTypes) {
			var fileName = file.name;
			var fileType = file.type;
			if(!fileType) {
				that.options.uploaderError('Unknown file type, the fileName is ' + fileName + '.', '未知文件类型');
				return picUrl;
			}
			fileType = fileType.toLowerCase();
			var fileTypes = that.options.fileTypes.split(',');
			var pass = false;
			$.each(fileTypes, function(index, type) {
				if(type.toLowerCase() == fileType) {
					pass = true;
					return false;
				}
			});
			if(!pass) {
				that.options.uploaderError('easy-uploader allows file types:[' + that.options.fileTypes + '], but the type of the file[fileName : ' + fileName + '] is ' + fileType, '存在不支持的文件类型');
				return picUrl;
			}
		}
		// 判定欲上传文件是否超出指定大小
		if(that.options.maxSize && that.options.maxSize != -1) {
			var fs = parseInt(file.size);
			var maxSize = parseInt(that.options.maxSize) * 1024;
			if(fs > maxSize) {
				that.options.uploaderError('easy-uploader allows the maximum size of the file to be ' + that.options.maxSize + 'KB, but the size of the file[fileName : ' + file.name + '] is ' + (fs / 1024) + 'KB', '文件过大');
				return picUrl;
			}
		}

		// 表单数据
		formData.append('file', file);
		if(that.options.requestData) {
			$.each(that.options.requestData, function(index, rd) {
				formData.append(rd.key, rd.value);
			});
		}
		// ajax请求上传文件
		$.ajax({
			url: that.options.uploadUrl,
			type: 'POST',
			async: false,
			data: formData,
			processData: false,
			contentType: false,
			enctype: 'multipart/form-data',
			beforeSend: function() {
				that.options.beforeSend();
			},
			success : function(data) {
				picUrl = that.options.success($uploader, data);
			},
			error: function() {
				that.options.error();
			}
		});
		return picUrl;
	}

	/**
	 * jQuery方法扩展--文件上传插件
	 */
	$.fn.easyUploader = function(options) {
		var args = arguments;
		var instance;

		if (options === undefined || typeof options === 'object') {
			return this.each(function () {
				if (!$.data(this, 'easy-uploader')) {
					$.data(this, 'easy-uploader', new EasyUploader( this, options ));
				}
			});
		} else if (typeof options === 'string' && options[0] !== '_' && options !== 'init') {
			instance = $.data(this[0], 'easy-uploader');

			// Allow instances to be destroyed via the 'destroy' method
			if (options === 'destroy') {
				// TODO: destroy instance classes, etc
				$.data(this, 'easy-uploader', null);
			}

			if (instance instanceof EasyUploader && typeof instance[options] === 'function') {
				return instance[options].apply( instance, Array.prototype.slice.call( args, 1 ) );
			} else {
				return this;
			}
		}
	}


})(jQuery, window);
