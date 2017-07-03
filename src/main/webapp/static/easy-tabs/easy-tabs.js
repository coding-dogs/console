(function($, win){

	var defaults = {
		seletedId: '',
		targetElement: ''
	};

	var CONTAINER = 'easy-tabs-container',
		TABS = 'easy-tabs-list',
		PAGES = 'easy-tabs-pages',
		PAGE_ITEM = 'tab-page',
		SELECTED_CLASS = 'selected';

	function EasyTabs(element, options) {
		this.element = element;
		this.$element = $(element);
		this.selectedIndex = 0;

		this.options = $.extend({}, defaults, options);

		this._init();
	}

	EasyTabs.fn = EasyTabs.prototype;

	EasyTabs.prototype._init = function() {
		var that = this;
		var $targets = $(that.options.targetElement);

		if($targets.length <= 0) {
			$targets = [$(win.document.body)];
		}

		$.each($targets, function(index, target) {
			that.draw($(target));
		});
	}

	EasyTabs.prototype.draw = function(target) {
		var that = this,
			$element = that.$element,
			$container = $('<div class="' + CONTAINER + '">'),
			$tabs = $('<div class="' + TABS + '">'),
			$pages = $('<div class="' + PAGES + '">'),
			targetIds = [];

		$tabs.appendTo($container);
		$pages.appendTo($container);
		$container.appendTo(target);

		var $lis = $element.find('li');
		$.each($lis, function(index, li) {
			var $a = $(li).find('a');
			var href = $a.attr('href');
			$a.removeAttr('href');
			$(li).attr('data-target', href);
			targetIds.push(href);
		});

		$lis.on('click', function(e) {
			var index = $(this).index();
			$tabs.find('li').eq(index).addClass(SELECTED_CLASS).siblings().removeClass(SELECTED_CLASS);
			$pages.find('.' + PAGE_ITEM).eq(index).show().siblings().hide();
		});

		$element.appendTo($tabs);

		if(targetIds.length > 0) {
			$.each(targetIds, function(idx, targetId) {
				if(!$(targetId).hasClass(PAGE_ITEM)) {
					$(targetId).addClass(PAGE_ITEM);
				}
				$(targetId).appendTo($pages);
				if(targetId === that.options.seletedId) {
					that.selectedIndex = idx;
				}
			});
		}

		$pages.find('.' + PAGE_ITEM).eq(that.selectedIndex).show();
		$tabs.find('li').eq(that.selectedIndex).addClass(SELECTED_CLASS);

	}

	$.fn.easyTabs = function(options) {
		var args = arguments;
        var instance;

        if (options === undefined || typeof options === 'object') {
            return this.each(function () {
                if (!$.data(this, 'easy-tabs')) {
                    $.data(this, 'easy-tabs', new EasyTabs(this, options ));
                }
            });
        } else if (typeof options === 'string' && options[0] !== '_' && options !== 'init') {
            instance = $.data(this[0], 'easy-tabs');

            // Allow instances to be destroyed via the 'destroy' method
            if (options === 'destroy') {
                // TODO: destroy instance classes, etc
                $.data(this, 'easy-tabs', null);
            }

            if (instance instanceof EasyTabs && typeof instance[options] === 'function') {
                return instance[options].apply( instance, Array.prototype.slice.call( args, 1 ) );
            } else {
                return this;
            }
        }
	}


})(jQuery, window);