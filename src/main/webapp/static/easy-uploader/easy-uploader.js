/**
 * create by qiudequan on 2017-06-22
 */

(function($, win){

	var defaults = {
			config: {
				width: 100,
				height: 100
			},
			uploadUrl : "",
			maltiple: false,
			maxLength: 10,
			beforeSend: function() {},
			success: function(data) {},
			error: function() {},
			mainSetting: false,
			mainSettingPlaceholder: '设为主图',
			mainSettingText: '已设为主图',
			mainSettingHandler: function() {}

	},
	UPLOADER_CONTAINER_CLASS = 'easy-uploader-container',
	UPLOADER_CLASS = 'easy-uploader',
	FILE_SELECTOR_CLASS = 'file-selector',
	PLUS_CLASS = 'plus',
	PREVIEW_CLASS = 'file-preview',
	MAIN_SETTING_CLASS = 'main-setting',
	MAIN_SETTING_PLACEHOLDER_CLASS = 'main-setting-placeholder',
	MAIN_SETTING_TEXT_CLASS = 'main-setting-text',
	HIDDEN_VALUE_CLASS = 'hidden-value',
	CLASS_FLAG = '.';

	function EasyUploader(element, options) {
		this.element = element;
		this.$element = $(element);

		this.options = $.extend({}, defaults, options);

		this._init();

	}

	EasyUploader.fn = EasyUploader.prototype;

	EasyUploader.prototype.checkParam = function() {
		var that = this;
		if(!that.options.uploadUrl) {
			throw new Error('the param of options.uploadUrl is empty.');
		}
	}

	EasyUploader.prototype._init = function() {
		var that = this;
		if(!that.$element.hasClass(UPLOADER_CONTAINER_CLASS)) {
			that.$element.addClass(UPLOADER_CONTAINER_CLASS);
		}

		that.$element.on('click', function(e) {
			if($(e.target).hasClass(UPLOADER_CLASS)){
				$(e.target).find(CLASS_FLAG + FILE_SELECTOR_CLASS).trigger('click');
			} else if($(e.target).hasClass(PLUS_CLASS) || $(e.target).hasClass(PREVIEW_CLASS) || $(e.target).parents(CLASS_FLAG + PREVIEW_CLASS).length > 0) {
				$(e.target).parents(CLASS_FLAG + UPLOADER_CLASS).find(CLASS_FLAG + FILE_SELECTOR_CLASS).trigger('click');
			}
		});

		that.drawUploader();
	}

	EasyUploader.prototype.drawUploader = function() {
		var that = this,
			$element = that.$element,
			$uploader = $('<div>'),
			$fileSelector = $('<input type="file">'),
			$hiddenValue = $('<input type="hidden">'),
			$plus = $('<span>'),
			$preview = $('<div>');

		$plus.addClass(PLUS_CLASS);
		$uploader.addClass(UPLOADER_CLASS);
		$fileSelector.addClass(FILE_SELECTOR_CLASS);
		$preview.addClass(PREVIEW_CLASS);
		$hiddenValue.addClass(HIDDEN_VALUE_CLASS);
		$preview.css({'position' : 'absolute'}).hide();

		var index = that.$element.attr('data-length');
		if(!index) {
			index = 0;
		} 
		index = parseInt(index) + 1;
		$element.attr('data-length', index);

		$hiddenValue.appendTo($uploader);
		$fileSelector.appendTo($uploader);
		$plus.appendTo($uploader);
		$preview.appendTo($uploader);
		$uploader.appendTo(that.$element);

		that.bindEvents(index);
	}

	EasyUploader.prototype.bindEvents = function(index) {
		var that = this,
		$element = that.$element,
		$preview = $element.find(CLASS_FLAG + PREVIEW_CLASS).eq(index -1),
		$plus = $element.find(CLASS_FLAG + PLUS_CLASS).eq(index -1),
		$uploader = $element.find(CLASS_FLAG + UPLOADER_CLASS).eq(index - 1);

		$element.find(CLASS_FLAG + FILE_SELECTOR_CLASS).eq(index - 1).on('change', function(e) {
			var $ele = $(this);
			var files = $ele[0].files;
			if(files.length > 0) {
				var fileName = files[0].name;
				var reader = new FileReader();
				if($preview.length > 0) {
					$preview.html('');
					$preview.siblings(CLASS_FLAG + MAIN_SETTING_CLASS).remove();
					that.upload(index, files[0]);
					if($preview.length > 0) {
						var $img = $('<img>');
						$img.attr('src', that.getFileURL(files[0]));
						$img.appendTo($preview);
						$img.css({'width' : that.options.config.width, 'height' : that.options.config.height});
						$preview.show();
						$plus.hide();
						if(that.options.mainSetting) {
							var $mainSetting = $('<div>');
							var $mainSettingText = $('<span>');
							$mainSetting.addClass(MAIN_SETTING_CLASS);
							$mainSettingText.addClass(MAIN_SETTING_PLACEHOLDER_CLASS);
							
							$mainSettingText.html(that.options.mainSettingPlaceholder);
							
							$mainSettingText.appendTo($mainSetting);
							$mainSetting.appendTo($uploader);
							
							$mainSetting.on('click', function(e) {
								if($(e.target).hasClass(MAIN_SETTING_CLASS) || $(e.target).parents(CLASS_FLAG + MAIN_SETTING_CLASS)) {
									that.options.mainSettingHandler(e, $uploader);
									$uploader.find(CLASS_FLAG + MAIN_SETTING_PLACEHOLDER_CLASS).html(that.options.mainSettingText);
									$uploader.siblings().find(CLASS_FLAG + MAIN_SETTING_PLACEHOLDER_CLASS).html(that.options.mainSettingPlaceholder);
								}
							});
						}
						if(parseInt($element.attr('data-length')) < that.options.maxLength) {
							// 新增一个
							that.drawUploader();
						}
					}
				}
			}
		});
	}

	EasyUploader.prototype.getFileURL = function (file) {
		var url = null;
		if (window.createObjectURL != undefined) {
			url = window.createObjectURL(file);
		} else if (window.URL != undefined) {
			url = window.URL.createObjectURL(file);
		} else if (window.webkitURL != undefined) {
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	}

	EasyUploader.prototype.upload = function(index, file) {
		var that = this;
		var $uploader = that.$element.find(CLASS_FLAG + UPLOADER_CLASS).eq(index - 1);
		var formData = new FormData();
		formData.append('file', file);
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
				that.options.success($uploader, data);
			},
			error: function() {
				that.options.error();
			}
		});
	}

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
