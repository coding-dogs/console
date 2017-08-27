(function($, win){

	var ID_KEY = 'value',
		PARENT_ID_KEY = 'pvalue',
		CHILDREN_KEY = 'children';

	var defaults = {
		type : 'select',	// select : 正常下拉框，tree : 树状下拉框
		hasMore : false,	// 是否需要更多选项
		moreText : '更多',	// 需要更多选项时的文本内容，支持html
		moreCallback : function() {		// 点击更多选项时的回调函数

		},
		required: false,
		disabled: [],
		targetLevel: 2,		// 指定树形菜单可选择的层级【暂只支持指定一个层级】，type为tree时生效，从0开始
		defaults: {
			value : '',
			text : '请选择',
			selected: true
		},
		items : [],			// 下拉框数据来源
		zIndex : 9999999,	
		maxHeight : 300,
		width : null,
		height : null,
		selectedCallback: function(selector) {
			
		}
	};

	function EasySelector(element, options) {
		this.element = element;
		this.$element = $(element);
		this.state = 'close';
		this.text = '';
		this.value = '';
		this.required = false;
		this.idKey = ID_KEY;
		this.parentKey = PARENT_ID_KEY;
		this.childKey = CHILDREN_KEY;

		this.options = $.extend({}, defaults, options);

		this._init();
	}

	EasySelector.fn = EasySelector.prototype;

	EasySelector.prototype.settings = function() {

	}

	EasySelector.prototype._init = function() {
		var that = this;
		this.text = that.$element.attr('data-text') || that.options.defaults.text;
		this.value = that.$element.attr('data-value');
		this.required = (that.$element.attr('data-required') == 'true') || that.options.required;

		if(this.options.type === 'tree') {
			var treeData = this.generateTreeData();
			this.options.items = treeData;
		}

		that._createElement();
	}

	EasySelector.prototype.generateTreeData = function() {
		var index,len,
			that = this, 
			items = that.options.items,
			isTree = that.options.type === 'tree',
			childKey = that.childKey,
			idKey = that.idKey,
			parentKey = that.parentKey;
		if(!isTree) {
			return []; 
		}

		var tempItems = [];

		if(!idKey || idKey == '' || !items) {
			return [];
		}

		if(Object.prototype.toString.call(items) === '[object Array]') {
			var r = [];
			var tmpMap = [];
			for (index = 0, len = items.length; index < len; index++) {
				tmpMap[items[index][idKey]] = items[index];
			}
			for (index = 0, len = items.length; index < len; index++) {
				if (tmpMap[items[index][parentKey]] && items[index][idKey] != items[index][parentKey]) {
					if (!tmpMap[items[index][parentKey]][childKey])
						tmpMap[items[index][parentKey]][childKey] = [];
					tmpMap[items[index][parentKey]][childKey].push(items[index]);
				} else {
					r.push(items[index]);
				}
			}
			return r;
		} else {
			return [items];
		}
	}

	EasySelector.prototype._createElement = function() {
		var that = this,
			$element = that.$element;
		$element.css({'width' : (that.options.width ? that.options.width + 'px' : '100%')});
		$element.addClass('easy-selector');
		// 追加值显示区域并赋值
		var $va = $('<div class="value-area">');
		$va.appendTo($element);

		var $vs = $('<span role="text-container">');
		$vs.text(that.text);
		$vs.appendTo($va);

		var $hidden = $('<input type="text" style="display:none" class="form-control" role="value-container" name="' + $element.data('name') + '"/>');
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
			if((!$(e.target).hasClass('easy-selector') && $(e.target).parents('.easy-selector').length == 0
					&& !$(e.target).hasClass('easy-arrow')) || !$(e.target).parents('.easy-selector').is(that.$element)) {
				that.close();
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
					$dropdownMenu.css({'top' : - (menuHeight + 4) + 'px'});
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
		that.$element.removeClass('focus');
	}

	EasySelector.prototype.open = function() {
		var that = this;
		that.$element.find('.dropdown-menu').show();
		that.changeState('open');
		that.$element.addClass('focus');
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
			that.options[key] = value;
			if(key === 'items') {
				if(that.options.type === 'tree') {
					var treeData = that.generateTreeData();
					that.options.items = treeData;
				}
				that.renderItems();
			}
		}
		return that.options[key];
	}
	
	EasySelector.prototype.getOptions = function(key) {
		var that = this;
		if(!key) {
			return null;
		}
		return that.options[key];
	}

	EasySelector.prototype.drawItem = function(item, pid, level, hasArrow) {
		var that = this,
			$ul = that.$element.find('.menu-wrapper ul'),
			$li = $('<li>'),
			$span = $('<span>'),
			$va = that.$element.find('.value-area'),
			$hidden = $va.find('input[role="value-container"]'),
			$vs = $va.find('span[role="text-container"]');
		$span.text(item.text);
		$li.attr('data-pid', pid);
		$li.attr('data-id', item[that.idKey]);
		$li.addClass('easy-selector-item');
		$li.attr('tabindex', '0');
		$li.data('value', item.value);
		$li.attr('data-level', level);
		$ul.append($li);
		
		if(level > 0) {
			for(var i = 0; i < level; i++) {
				var $indent = $('<span class="easy-selector-indent">');
				$indent.appendTo($li);
			}
		}
		
		if(that.options.type === 'tree') {
			var $pli = $ul.find('li[data-id="' + item.pvalue + '"]');
			var hasChildren = item[that.childKey] && item[that.childKey].length > 0;
			if((!hasArrow && level == 0) || ($pli.length > 0 && $pli.attr('data-has-children') === 'true' && !hasChildren)) {
				var $indent = $('<span class="easy-selector-indent">');
				$indent.appendTo($li);
			}

			if(hasChildren) {
				var $arrow = $('<span class="easy-arrow easy-arrow-bottom"></span>');
				$arrow.appendTo($li);
				hasArrow = true;
			}
		}

		$span.appendTo($li);

		// 如果指定的唯一值相等，则需要默认选中，并取消初始化设置的"请选择"项的选中状态
		if(that.value === item.value) {
			$li.addClass('selected').siblings().removeClass('selected');
			if(that.text) {
				that.text = item.text;
				$hidden.val(item.value);
				$vs.text(item.text);
			}
		}
		var children = item[this.childKey];
		if(children) {
			$li.attr('data-has-children', 'true');
			level++;
			for(var i = 0; i < children.length; i++) {
				that.drawItem(children[i], item[that.idKey], level, hasArrow);
			}
		}
	}
	
	EasySelector.prototype.renderItems = function() {
		var that = this;
		var $ul = that.$element.find('.menu-wrapper ul');
		if($ul.length <= 0) {
			return;
		}
		$ul.html('');
		var $va = that.$element.find('.value-area');
		var $hidden = $va.find('input[role="value-container"]');
		var $vs = $va.find('span');
		if(that.options.defaults) {
			var $defaultLi = $('<li>');
			$defaultLi.attr('data-pid', '-1');
			$defaultLi.attr('data-id', that.options.defaults.value);
			$defaultLi.attr('data-level', '0');
			$defaultLi.attr('tab-index', '0');
			$defaultLi.data('role', 'default');
			$defaultLi.html('<span>' + that.options.defaults.text + '</span>');
			$defaultLi.appendTo($ul);
			
			if(that.options.defaults.selected) {
				$defaultLi.addClass('selected');
				$hidden.val(that.options.defaults.value);
				$vs.html(that.options.defaults.text);
			}
		}
		
		if(that.options.items) {
			$.each(that.options.items, function(index, item) {
				that.drawItem(item, -1, 0, (item[that.childKey] && item[that.childKey].length > 0));
			});
		}
		$ul.find('li').hover(function(e) {
			var _this = $(this);
			var dataId = _this.attr('data-id');
			if(that.isDisabled(dataId)) {
				_this.addClass('disabled');
				return;
			}
			_this.removeClass('disabled');
			if(!$(this).hasClass('selected')) {
				if(that.options.type === 'tree' && ($(this).data('role') == 'default' || ($(this).attr('data-level') == that.options.targetLevel))) {
					$(this).addClass('hover');
				} else if(that.options.type !== 'tree'){ 
					$(this).addClass('hover');
				}
			}
			
		}, function(e) {
			if($(this).hasClass('hover')){
				$(this).removeClass('hover');
			}
		});

		$ul.find('li').on('click', function(e) {
			e.stopPropagation();
			var _this = $(this);
			var dataId = _this.attr('data-id');
			if(that.isDisabled(dataId)) {
				_this.addClass('disabled');
				return;
			}
			_this.removeClass('disabled');
			var $target = $(e.target);
			var isArrow = $target.hasClass('easy-arrow');
			if(isArrow) {
				if ($target.hasClass('easy-arrow-top')) {
					$target.removeClass('easy-arrow-top').addClass('easy-arrow-bottom');
					that.display($(this).attr('data-id'));
				} else if($target.hasClass('easy-arrow-bottom')){
					$target.removeClass('easy-arrow-bottom').addClass('easy-arrow-top');
					// 收缩选项
					that.hidden($(this).attr('data-id'));
				}
				return;
			}
			
			if(that.options.type === 'tree' && $(this).data('role') != 'default' && ($(this).attr('data-level') != that.options.targetLevel)) {
				return;
			}

			var value = $(this).data('value');
			var text = $(this).text();
			
			that.value = value;
			that.text = text;

			$vs.text(that.text);
			$hidden.val(that.value);
			var selectedA = that.$element.find('.menu-wrapper').find('li.selected');
			if(selectedA.length > 0) {
				selectedA.removeClass('selected');
			}
			$(this).addClass('selected');
			that.$element.siblings('.error').remove();
			that.$element.removeClass('error');
			that.close();
			that.options.selectedCallback(that.$element);
		});
	}

	EasySelector.prototype.display = function(id) {
		var that = this;
		var $ul = that.$element.find('.menu-wrapper ul');
		var $children = $ul.find('li[data-pid="' + id + '"]');

		if($children.length == 0) {
			return;
		}

		// 展开选项
		$.each($children, function(index, child) {
			$(child).show();
			if($(child).attr('data-has-children') === 'true') {
				$(child).find('span.easy-arrow').removeClass('easy-arrow-top').addClass('easy-arrow-bottom');
			}
			that.display($(child).attr('data-id'));
		});
	}

	EasySelector.prototype.hidden = function(pid) {
		var that = this;
		var $ul = that.$element.find('.menu-wrapper ul');
		var $lis = $ul.find('li[data-pid="' + pid + '"]');
		if($lis.length == 0) {
			return;
		}
		$.each($lis, function(index, item) {
			var _pid = $(item).attr('data-id');
			$(item).hide();
			if($(item).attr('data-has-children') === 'true') {
				$(item).find('span.easy-arrow').removeClass('easy-arrow-bottom').addClass('easy-arrow-top');
			}
			that.hidden(_pid);
		});
	}
	
	EasySelector.prototype.select = function(value) {
		var that = this;
		var items = that.options.items;
		
		var $valueLi = that.$element.find('.menu-wrapper ul li[data-id="' + value + '"]');
		if($valueLi.length > 0 && that.options.type === 'tree' && that.options.targetLevel) {
			var valueLevel = $valueLi.attr('data-level');
			if(valueLevel != that.options.targetLevel) {
				return;
			}
		}
		
		if(items.length > 0) {
			$.each(items, function(index, item) {
				if(value === item.value) {
					that.value = item.value;
					that.text = item.text;
					that.$element.find('.value-area').find('input').val(item.value);
					that.$element.find('.value-area').find('span').html(item.text);
					that.$element.find('.menu-wrapper ul li').removeClass('selected');
					var $selectedLi = that.$element.find('.menu-wrapper ul li[data-id="' + value +'"]');
					$selectedLi.addClass('selected');
					that.options.selectedCallback(that.$element);
					return false;
				}
				if(item.children && item.children.length > 0){
					var _result = that._recurseSelect(item.children, value);
					if(_result) {
						return false;
					} else {
						return true;
					}
				}
			});
		}
	}
	
	EasySelector.prototype.isDisabled = function(id) {
		var that = this;
		var isDisabled = false;
		var disabled = that.options.disabled;
		if(!disabled || disabled.length == 0) {
			return isDisabled;
		}
		$.each(disabled, function(index, item) {
			if(item == id) {
				isDisabled = true;
				return false;
			}
		});
		return isDisabled;
	}
	
	EasySelector.prototype._recurseSelect = function(items, value) {
		var that = this,
			result = false;
		if(items.length > 0) {
			$.each(items, function(index, item) {
				if(value === item.value) {
					that.value = item.value;
					that.text = item.text;
					that.$element.find('.value-area').find('input').val(item.value);
					that.$element.find('.value-area').find('span').html(item.text);
					that.$element.find('.menu-wrapper ul li').removeClass('selected');
					var $selectedLi = that.$element.find('.menu-wrapper ul li[data-id="' + value +'"]');
					$selectedLi.addClass('selected');
					that.options.selectedCallback(that.$element);
					result = true;
					return false;
				} else if(item.children && item.children.length > 0){
					var _result = that._recurseSelect(item.children, value);
					if(_result) {
						result = _result;
						return false;
					} else {
						return true;
					}
				}
			});
		}
		return result;
	}
	
	EasySelector.prototype.getValue = function() {
		var that = this;
		return that.value;
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