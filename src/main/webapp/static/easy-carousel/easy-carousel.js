(function($, win){
	// 默认参数
	var defaults = {
		items: [],
		trigger: 'hover'
	},
	EASY_CAROUSEL_CONTAINER = 'easy-carousel-container',
	EASY_CAROUSEL_PRIMARY = 'easy-carousel-primary',
	EASY_CAROUSEL_LIST = 'easy-carousel-list',
	EASY_CAROUSEL_LIST_ITEMS = 'easy-carousel-list-items',
	EASY_CAROUSEL_IMG_ITEM = 'easy-carousel-img-item',
	EASY_CAROUSEL_BTN = 'easy-carousel-btn',
	EASY_CAROUSEL_PREV = 'easy-carousel-prev',
	EASY_CAROUSEL_NEXT = 'easy-carousel-next',
	EASY_CAROUSEL_CLEARFIX = 'easy-carousel-clearfix',
	ACTIVE_CLASS = 'active',
	MAX_LEFT = 74 * 6;

	/**
	 * 轮播图对象
	 * @param element
	 * @param options
	 * @returns
	 */
	function EasyCarousel(element, options) {
		this.element = element;
		this.$element = $(element);
		
		this.$element.addClass(EASY_CAROUSEL_CLEARFIX);

		this.options = $.extend({}, defaults, options);

		// 进行初始化
		this._init();
	}

	EasyCarousel.fn = EasyCarousel.prototype;
	
	/**
	 * 初始化函数
	 */
	EasyCarousel.prototype._init = function() {
		var that = this;

		// 绘制
		that.drawCarousel();
	}

	/**
	 * 绘制轮播图
	 */
	EasyCarousel.prototype.drawCarousel = function() {
		var that = this,
			$element = that.$element,
			$container = $('<div class="' + EASY_CAROUSEL_CONTAINER + '">'),
			$primary = $('<div class="' + EASY_CAROUSEL_PRIMARY + '">'),
			$list = $('<div class="' + EASY_CAROUSEL_LIST + '">'),
			$listItems = $('<div class="' + EASY_CAROUSEL_LIST_ITEMS + '">'),
			$primaryImage = $('<img>'),
			$prev = $('<a>'),
			$next = $('<a>'),
			$listUl = $('<ul>')
			;

			$element.append($container);
			$container.append($primary);
			$container.append($list);

			$prev.addClass(EASY_CAROUSEL_BTN).addClass(EASY_CAROUSEL_PREV);
			$next.addClass(EASY_CAROUSEL_BTN).addClass(EASY_CAROUSEL_NEXT);
			$prev.html('&lt;');
			$next.html('&gt;');
			$list.append($prev);
			$list.append($listItems);
			$list.append($next);

			$listItems.append($listUl);
			if(that.options.items && that.options.items.length > 0) {
				$primaryImage.attr('src', that.options.items[0].url);
				$primary.append($primaryImage);

				$.each(that.options.items, function(index, item) {
					var $listLi = $('<li>');
					var $img = $('<img>');
					
					if(index == 0) {
						$listLi.addClass(ACTIVE_CLASS);
					}

					$listLi.addClass(EASY_CAROUSEL_IMG_ITEM);

					$img.attr('src', item.url);

					$listUl.append($listLi);
					$listLi.append($img);
				});
			}

			// 事件绑定
			that.bindEvents();
			

	}

	/**
	 * 事件绑定函数
	 */
	EasyCarousel.prototype.bindEvents = function() {
		var that = this,
			$element = that.$element,
			$prev = $element.find(that._getJqClass(EASY_CAROUSEL_PREV)),
			$next = $element.find(that._getJqClass(EASY_CAROUSEL_NEXT)),
			$listUl = $element.find(that._getJqClass(EASY_CAROUSEL_LIST_ITEMS)).find('ul');

		$element.find(that._getJqClass(EASY_CAROUSEL_BTN)).hover(function(e) {
			$(this).addClass(ACTIVE_CLASS);
		}, function(e) {
			$(this).removeClass(ACTIVE_CLASS);
		});

		// 向前切页
		$prev.on('click', function(e) {
			var left = parseInt($listUl.css('left'));
			if(left < 0) {
				left = left + 74;
				$listUl.css('left', left + 'px');
			}
			
		});

		// 向后切页
		var len = that.options.items.length;
		$next.on('click', function(e) {
			var left = parseInt($listUl.css('left'));
			var max = 74 * (6 - len);
			if(left <= max) {
				return;
			}

			left = left - 74;
			$listUl.css('left', left + 'px');
			
		});

		if(that.options.trigger === 'hover') {
			$element.find(that._getJqClass(EASY_CAROUSEL_IMG_ITEM)).hover(function(e) {
				var $this = $(this);
				$this.addClass(ACTIVE_CLASS).siblings().removeClass(ACTIVE_CLASS);
				var src = $this.find('img').attr('src');
				var $img = $element.find(that._getJqClass(EASY_CAROUSEL_PRIMARY)).find('img');
				if($img.length != 0) {
					$img.attr('src', src);
					$this.addClass(ACTIVE_CLASS).siblings().removeClass(ACTIVE_CLASS);
				}
			}, function(e) {
				
			});
		} else {
			// 绑定小图片的事件
			$element.find(that._getJqClass(EASY_CAROUSEL_IMG_ITEM)).on(that.options.trigger, function() {
				var $this = $(this);
				var src = $this.find('img').attr('src');
				var $img = $element.find(that._getJqClass(EASY_CAROUSEL_PRIMARY)).find('img');
				if($img.length != 0) {
					$img.attr('src', src);
					$this.addClass(ACTIVE_CLASS).siblings().removeClass(ACTIVE_CLASS);
				}
			});
		}
	}

	EasyCarousel.prototype._getJqClass = function(className) {
		return '.' + className;
	}

	/**
	 * jQuery方法扩展--轮播图插件
	 */
	$.fn.easyCarousel = function(options) {
		var args = arguments;
		var instance;

		if (options === undefined || typeof options === 'object') {
			return this.each(function () {
				if (!$.data(this, 'easy-carousel')) {
					$.data(this, 'easy-carousel', new EasyCarousel( this, options ));
				}
			});
		} else if (typeof options === 'string' && options[0] !== '_' && options !== 'init') {
			instance = $.data(this[0], 'easy-carousel');

			// Allow instances to be destroyed via the 'destroy' method
			if (options === 'destroy') {
				// TODO: destroy instance classes, etc
				$.data(this, 'easy-carousel', null);
			}

			if (instance instanceof EasyCarousel && typeof instance[options] === 'function') {
				return instance[options].apply( instance, Array.prototype.slice.call( args, 1 ) );
			} else {
				return this;
			}
		}
	}


})(jQuery, window);
