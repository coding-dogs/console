(function($, win){

	var defaults = {
		hasMore : false,
		moreText : '更多',
		moreCallback : function() {

		},
		items : [],
		zIndex : 9999999,
		maxHeight : 300,
		width : null,
		height : null
	};

	function EasySelector(element, options) {
		this.element = element;
		this.$element = $(element);
		this.state = 'close';
		this.text = '';
		this.value = '';
		this.required = false;

		this.options = $.extend({}, defaults, options);

		this._init();
	}

	EasySelector.fn = EasySelector.prototype;

	EasySelector.prototype._init = function() {
		var that = this;
		this.text = that.$element.data('text');
		this.value = that.$element.data('value');
		this.required = that.$element.data('required') || false;

		that._createElement();
	}

	EasySelector.prototype._createElement = function() {
		var that = this,
			$element = that.$element;
		$element.css({'width' : (that.options.width ? that.options.width + 'px' : '100%')});
		$element.addClass('easy-selector');
		// 追加值显示区域并赋值
		var $va = $('<div class="value-area">');
		$va.appendTo($element);

		var $vs = $('<span>');
		$vs.text(that.text);
		$vs.appendTo($va);

		var $hidden = $('<input type="hidden" name="' + $element.data('name') + '"/>');
		if(that.required) {
			$hidden.addClass('required');
		}
		$hidden.appendTo($va);
		$hidden.val(that.value);

		// 生成下拉图标
		var $caret = $('<i class="easy-caret caret-bottom">');
		$caret.appendTo($va);

		// 生成下拉菜单
		var $dropdownMenu = $('<div class="dropdown-menu">');
		$dropdownMenu.css({'display': 'none', 'z-index': that.options.zIndex, 
			'width' : (that.options.width ? that.options.width + 'px' : '100%'),
			'height' : that.options.height ? that.options.height + 'px' : 'auto'});
		var $ulWrapper = $('<div class="menu-wrapper">');
		var $ul = $('<ul>');
		$ul.css({'max-height' : that.options.maxHeight + 'px'});
		$ul.appendTo($ulWrapper);
		
		$ulWrapper.appendTo($dropdownMenu);
		$dropdownMenu.appendTo($element);
		
		// 构建菜单项
		that.renderItems();

		// 生成更多按钮
		if(that.options.hasMore) {
			var $moreWrapper = $('<div class="more-wrapper">');
			var $moreBtn = $('<a class="more-btn">');
			$moreBtn.html(that.options.moreText);
			$moreBtn.appendTo($moreWrapper);
			$moreWrapper.appendTo($dropdownMenu);
			$moreBtn.on('click', function() {
				typeof that.options.moreCallback && that.options.moreCallback(that.value, that.text);
			});
		}

		$element.on('click', function() {
			if(that.state === 'close') {
				that.open();
			} else if(that.state === 'open') {
				that.close();
			}
		});
		
		$(win.document.body).on('click', function(e) {
			if(!$(e.target).hasClass('easy-selector') && $(e.target).parents('.easy-selector').length == 0) {
				that.close();
				console.log('123');
			}
		});


		// 计算位置
		that.winResize();
	}

	EasySelector.prototype.winResize = function() {
		var that = this;
		$(win).resize(function() {
			var $caret = that.$element.find('.value-area').find('.easy-caret');
			var $dropdownMenu = that.$element.find('.dropdown-menu');
			var selectedBottom = that.$element.position().top + that.$element.height();
			var menuHeight = $dropdownMenu.height();
			var winHeight = $(win).height();
			if(winHeight - selectedBottom - 4 >= menuHeight) {
				$dropdownMenu.css({'top' : that.$element.height() + 2 + 'px'});
				if($caret.length > 0) {
					$caret.removeClass('caret-top').addClass('caret-bottom');
				}
			} else {
				if(that.$element.position().top >= menuHeight) {
					$dropdownMenu.css({'top' : -(menuHeight + 4) + 'px'});
					if($caret.length > 0) {
						$caret.removeClass('caret-bottom').addClass('caret-top');
					}
				}
			}
		});
	}

	EasySelector.prototype.close = function() {
		var that = this;
		that.$element.find('.dropdown-menu').hide();
		that.changeState('close');
	}

	EasySelector.prototype.open = function() {
		var that = this;
		that.$element.find('.dropdown-menu').show();
		that.changeState('open');
	}

	EasySelector.prototype.changeState = function(state) {
		var that = this;
		if(state) {
			that.state = state;
			return;
		}
		if(that.state === 'open') {
			that.state = 'close';
		} else if(that.state === 'close') {
			that.state = 'open';
		}
		
	}

	EasySelector.prototype.setOptions = function(key, value) {
		var that = this;
		if(value) {
			this.options[key] = value;
			if(key === 'items') {
				that.renderItems();
			}
		}
		return this.options[key];
	}
	
	EasySelector.prototype.renderItems = function() {
		var that = this;
		var $ul = that.$element.find('.menu-wrapper ul');
		if($ul.length <= 0) {
			return;
		}
		$ul.html('');
		var $va = that.$element.find('.value-area');
		var $hidden = $va.find('input[type="hidden"]');
		var $vs = $va.find('span');
		if(that.options.items) {
			$.each(that.options.items, function(index, item) {
				var $li = $('<li>');
				var $a = $('<a>');
				$a.addClass('easy-selector-item');
				$a.attr('tabindex', '0');
				$a.data('value', item.value);
				$a.text(item.text);
				$a.appendTo($li);
				$ul.append($li);

				if(that.value === item.value) {
					$a.addClass('selected');
					if(!that.text) {
						that.text = item.text;
						$hidden.val(item.value);
						$vs.text(item.text);
					}
				}
			});
		}
		$ul.find('li a').hover(function() {
			if(!$(this).hasClass('selected')) {
				$(this).addClass('hover');
			}
			
		}, function() {
			if($(this).hasClass('hover')){
				$(this).removeClass('hover');
			}
		});

		$ul.find('li a').on('click', function(e) {
			e.stopPropagation();
			var value = $(this).data('value');
			var text = $(this).text();

			that.value = value;
			that.text = text;

			$vs.text(that.text);
			$hidden.val(that.value);
			var selectedA = that.$element.find('.menu-wrapper').find('a.selected');
			if(selectedA.length > 0) {
				selectedA.removeClass('selected');
			}
			$(this).addClass('selected');
			that.close();
		});
	}
	
	EasySelector.prototype.select = function(value) {
		var that = this;
		var items = that.options.items;
		if(items.length > 0) {
			$.each(items, function(index, item) {
				if(value === item.value) {
					that.value = item.value;
					that.text = item.text;
					that.$element.find('.value-area').find('input').val(item.value);
					that.$element.find('.value-area').find('span').html(item.text);
					that.$element.find('.menu-wrapper ul li a').removeClass('selected');
					that.$element.find('.menu-wrapper ul li a').eq(index).addClass('selected');
				}
			});
		}
	}


	$.fn.easySelector = function(options) {
		var args = arguments;
        var instance;

        if (options === undefined || typeof options === 'object') {
            return this.each(function () {
                if (!$.data(this, 'easy-selector')) {
                    $.data(this, 'easy-selector', new EasySelector( this, options ));
                }
            });
        } else if (typeof options === 'string' && options[0] !== '_' && options !== 'init') {
            instance = $.data(this[0], 'easy-selector');

            // Allow instances to be destroyed via the 'destroy' method
            if (options === 'destroy') {
                // TODO: destroy instance classes, etc
                $.data(this, 'easy-selector', null);
            }

            if (instance instanceof EasySelector && typeof instance[options] === 'function') {
                return instance[options].apply( instance, Array.prototype.slice.call( args, 1 ) );
            } else {
                return this;
            }
        }
	}


})(jQuery, window);