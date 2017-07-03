(function($, win){
	var ID_KEY = 'id';
	var defaults = {
		url: '',					// 数据请求url
		records: '',				// 不可选中记录ID集合
		type: 'none',				// 	none: 无多选或单选框，checkbox：多选框，radio：单选框
		titles: [],					// 表头定制，数组内对象属性 name：返回数据取值字段名；title：表头名称
		listProp: '',				// 数据中列表数据取值属性
		pagination: true,			// 是否需要分页条
		requestData: {},			// 请求参数
		successCode: '200',			// 后台数据表示成功的代码
		pageItemCount: 10,			// 分页条显示总个数
		errorCallback: function() {	// 请求错误回调函数
			
		}
	};

	function EasyPageRecords(element, options) {
		this.element = element;
		this.$element = $(element);
		this.records = [];
		if(this.$element.prop('nodeName') !== 'TABLE') {
			throw new Error('the jquery plugin[easy-page-records] must act on the table.');
		}

		this.options = $.extend({}, defaults, options);

		if(!this.options.titles || this.options.titles.length == 0) {
			throw new Error('the titles of options is empty.');
		}

		this._init();
	}

	EasyPageRecords.fn = EasyPageRecords.prototype;

	EasyPageRecords.prototype.getRecords = function() {
		var that = this;
		return that.records;
	}

	EasyPageRecords.prototype._init = function() {
		var that = this;
		var $thead = $('<thead>');
		var $tr = $('<tr>');

		// 处理单选全选按钮
		if(that.options.type && that.options.type !== 'none') {
			var $td = $('<td>');
			var $icheckAll = $('<input class="i-checks">');
			$icheckAll.attr('type', that.options.type);
			$icheckAll.appendTo($td);
			$td.appendTo($tr);
		}

		$.each(that.options.titles, function(index, title) {
			var $td = $('<td>');
			$td.html(title.title);
			$td.appendTo($tr);
		});

		$tr.appendTo($thead);
		$thead.appendTo(that.$element);

		that.request();
		
	}

	EasyPageRecords.prototype.request = function() {
		var that = this;
		if(that.options.pagination) {
			that.$element.siblings('.fixed-table-pagination').remove();
		}
		var $tbody = that.$element.find('tbody');
		if($tbody.length > 0) {
			$tbody.remove();
		}
		$.ajax({
			url: that.options.url,
			type: "GET",
			data: that.options.requestData,
			dataType: "JSON",
			success: function(data) {
				if(that.options.successCode === data.code) {
					var $tbody = $('<tbody>');
					var listProp = that.options.listProp;
					var result = data.result;
					if(listProp) {
						result = result[listProp];
					}
					if(!result) {
						return;
					}
					$.each(result, function(index, item) {
						var $tr = $('<tr>');
						$tr.attr('data-value', item[ID_KEY]);
						$tr.data('record', item);
						if(that.options.type && that.options.type !== 'none') {
							var $icheckTd = $('<td>');
							var $icheck = $('<input class="i-checks">');
							$icheck.attr('type', that.options.type);
							$icheck.appendTo($icheckTd);
							$icheckTd.appendTo($tr);
						}
						$.each(that.options.titles, function(index, title) {
							var $td = $('<td>');
							$td.attr('data-key', title.name);
							var value = item[title.name];
							
							$td.attr('data-value', item[title.name]);
							$td.html(item[title.name]);
							$td.appendTo($tr);
						});	
						$tr.appendTo($tbody);					
						
					});
					$tbody.appendTo(that.$element);
					that.ichecksInit();
					that.bindEvents();
					if(that.options.pagination) {
						that.drawPager(data.result.pageNo, data.result.pageSize, data.result.count);
					}
				}
			},
			error: function() {
				that.options.errorCallback();
			}
		});
	}
	
	EasyPageRecords.prototype.ichecksInit = function() {
		var $ichecks = $('.i-checks');
		if($ichecks.length > 0){
			$ichecks.iCheck({
				checkboxClass: 'icheckbox_flat-green',
	            radioClass: 'iradio_flat-green',
	        });
		}
	}
	
	EasyPageRecords.prototype.drawPager = function(pageNo, pageSize, count) {
		var that = this;
		var $pager = $('<div class="fixed-table-pagination clearfix">');
		var $left = $('<div class="pull-left pagination-detail">');
		var $record = $('<span class="pagination-info">');
		var $pageList = $('<span class="page-list">');
		var $btnGroup = $('<span class="btn-group dropup">');
		var btnHtml = '<button type="button" class="btn btn-default  btn-outline dropdown-toggle"' 
			+ 'data-toggle="dropdown"><span class="page-size">' + pageSize + '</span> <span class="caret"></span></button>';
		var $menu = $('<ul class="dropdown-menu" role="menu">');
		var $text1 = $('<span>');
		var $text2 = $('<span>');
		$text1.text('每页显示 ');
		$text2.text(' 条记录');
		
		$btnGroup.html(btnHtml);
		$text1.appendTo($pageList);
		var sizeArray = [10, 25, 50, 100];
		$.each(sizeArray, function(index, size) {
			var $li = $('<li>');
			var $a = $('<a>');
			$a.html(size);
			$a.appendTo($li);
			if(pageSize == size) {
				$li.addClass('active');
			}
			$li.appendTo($menu);
		});
		$menu.appendTo($btnGroup);
		$btnGroup.appendTo($pageList);
		$text2.appendTo($pageList);
		
		var $right = $('<div class="pull-right pagination-roll">');
		var startRecord = ((pageNo - 1) * pageSize + 1);
		var endRecord = ((pageNo - 1) * pageSize + pageSize);
		(endRecord > count) && (
			endRecord = count
		);
		$record.html('显示第 ' + startRecord + ' 到第 ' + endRecord + ' 条记录，总共 ' + count + ' 条记录 ');
		$left.appendTo($pager);
		$record.appendTo($left);
		$pageList.appendTo($left);
		$right.appendTo($pager);
		that.$element.after($pager);
		$pager.show();
		
		// 计算禁用页，页码
		var $ul = $('<ul class="pagination no-page pagination-outline">');
		var $firstLi = $('<li class="paginate_button first-page previous">');
		var $firstA = $('<a>');
		var $firstI = $('<i class="fa fa-angle-double-left">');
		$firstI.appendTo($firstA);
		$firstA.appendTo($firstLi);
		$firstLi.appendTo($ul);
		var $prevLi = $('<li class="paginate_button prev-page previous">');
		var $prevA = $('<a>');
		var $prevI = $('<i class="fa fa-angle-left">');
		$prevI.appendTo($prevA);
		$prevA.appendTo($prevLi);
		$prevLi.appendTo($ul);
		
		$ul.css('margin', '0');
		
		var totalPage = (count % pageSize == 0) ? parseInt(count / pageSize) : (parseInt(count / pageSize) + 1);
		var hasPrev = pageNo != 1;
		var hasNext = (totalPage != pageNo) && totalPage > 0;
		var middlePage = parseInt((that.options.pageItemCount % 2 == 0) ? (that.options.pageItemCount / 2) : (that.options.pageItemCount / 2 + 1));
		
		if(!hasPrev) {
			$firstLi.addClass('disabled');
			$prevLi.addClass('disabled');
		}
		
		var startPage;
		var endPage;
		if(totalPage <= that.options.pageItemCount) {
			startPage = 1;
			endPage = totalPage;
		} else if(totalPage - pageNo <= middlePage) {
			startPage = totalPage - pageSize + 1;
			endPage = totalPage;
		} else {
			startPage = (pageNo - middlePage + 1);
			endPage = (pageSize - middlePage + pageNo);
		}
		if(startPage == 1 && endPage == 0) {
			startPage = 1;
			endPage = 1;
			pageNo = 1;
		}
		
		for(; startPage <= endPage; startPage++) {
			var $li = $('<li class="paginate_button">');
			var $a = $('<a>');
			$a.html(startPage);
			if(startPage == pageNo) {
				$li.addClass('active');
			}
			$a.appendTo($li);
			$li.appendTo($ul);
		}
		
		var $nextLi = $('<li class="paginate_button next-page next">');
		var $nextA = $('<a>');
		var $nextI = $('<i class="fa fa-angle-right">');
		$nextI.appendTo($nextA);
		$nextA.appendTo($nextLi);
		$nextLi.appendTo($ul);
		var $lastLi = $('<li class="paginate_button last-page previous">');
		var $lastA = $('<a>');
		var $lastI = $('<i class="fa fa-angle-double-right">');
		$lastI.appendTo($lastA);
		$lastA.appendTo($lastLi);
		$lastLi.appendTo($ul);
		if(!hasNext) {
			$nextLi.addClass('disabled');
			$lastLi.addClass('disabled');
		}
		$ul.appendTo($right);
		
		$menu.find('li').on('click', function(e) {
			var $li = $(this);
			if($li.hasClass('active')) {
				return;
			}
			that.options.requestData['pageSize'] = $li.find('a').html();
			that.request();
		});
		
		$right.find('ul li').on('click', function(e) {
			var $li = $(this);
			if($li.hasClass('active') || $li.hasClass('disabled')) {
				return;
			}
			var resetPage = 1;
			if($li.hasClass('prev-page')) {
				resetPage = pageNo - 1;
			} else if($li.hasClass('first-page')) {
				resetPage = 1;
			} else if($li.hasClass('next-page')) {
				resetPage = pageNo + 1;
			} else if($li.hasClass('last-page')) {
				resetPage = totalPage;
			} else {
				resetPage = $li.find('a').html();
			}
			console.log(resetPage);
			that.options.requestData['pageNo'] = resetPage;
			that.request();
		});
	}
	
	EasyPageRecords.prototype.bindEvents = function() {
		var that = this;
		that.$element.find("thead tr td input.i-checks").on('ifChecked', function(e) {
			var $checkAll = $(this);
			if(that.$element.find("tbody tr td input.i-checks").length == 0) {
				return;
			}
			that.$element.find("tbody tr td input.i-checks").each(function(index, check) {
				$(check).iCheck('check');
			});
		});
		
		that.$element.find("thead tr td input.i-checks").on('ifUnchecked', function(e) {
			var $checkAll = $(this);
			if($checkAll.attr('data-no-operate') === 'true') {
				$checkAll.removeAttr('data-no-operate');
				return;
			}
			if(that.$element.find("tbody tr td input.i-checks").length == 0) {
				return;
			}
			that.$element.find("tbody tr td input.i-checks").each(function(index, check) {
				$(check).iCheck('uncheck');
			});
		});
		
		that.$element.find("tbody tr td input.i-checks").on('ifChecked', function(e) {
			var $icheck = $(this);
			var $tr = $icheck.parents('tr');
			var dataId = $tr.attr('data-value');
			var index = that.indexOf(dataId);
			if(index == -1) {
				that.records.push($tr.data('record'));
			}
			var len = that.$element.find("tbody tr td input.i-checks").length;
			var checkedLength = 0;
			if(len > 0) {
				that.$element.find("tbody tr td input.i-checks").each(function(index, ele) {
					var checked = $(this).is(':checked');
					if(checked) {
						checkedLength++;
					}
				});
				if(checkedLength == len) {
					that.$element.find("thead tr td input.i-checks").iCheck('check');
				}
			}
			
			
			console.log(that.records);
		});
		
		that.$element.find("tbody tr td input.i-checks").on('ifUnchecked', function(e) {
			var $icheck = $(this);
			var $tr = $icheck.parents('tr');
			var dataId = $tr.attr('data-value');
			var index = that.indexOf(dataId);
			if(index != -1) {
				that.records.splice(index, 1);
			}
			var len = that.$element.find("tbody tr td input.i-checks").length;
			var checkedLength = that.$element.find("tbody tr td input.i-checks:checked").length;
			if(len > 0) {
				if(checkedLength !== len) {
					if(that.$element.find("thead tr td input.i-checks").is(':checked')) {
						that.$element.find("thead tr td input.i-checks").attr('data-no-operate', 'true');
						that.$element.find("thead tr td input.i-checks").iCheck('uncheck');
					}
				}
			}
		});
	}
	
	EasyPageRecords.prototype.indexOf = function(id) {
		var that = this;
		var index = -1;
		$.each(that.records, function(idx, record) {
			if(id === record[ID_KEY]) {
				index = idx;
				return false;
			}
		});
		return index;
	}
	
	EasyPageRecords.prototype.setOptions = function(key, value) {
		var that = this;
		if(!key) {
			return null;
		}
		that.options[key] = value;
		return that.options[key];
	}

	$.fn.easyPageRecords = function(options) {
		var args = arguments;
        var instance;

        if (options === undefined || typeof options === 'object') {
            return this.each(function () {
                if (!$.data(this, 'easy-page-records')) {
                    $.data(this, 'easy-page-records', new EasyPageRecords( this, options ));
                }
            });
        } else if (typeof options === 'string' && options[0] !== '_' && options !== 'init') {
            instance = $.data(this[0], 'easy-page-records');

            // Allow instances to be destroyed via the 'destroy' method
            if (options === 'destroy') {
                // TODO: destroy instance classes, etc
                $.data(this, 'easy-page-records', null);
            }

            if (instance instanceof EasyPageRecords && typeof instance[options] === 'function') {
                return instance[options].apply( instance, Array.prototype.slice.call( args, 1 ) );
            } else {
                return this;
            }
        }
	}


})(jQuery, window);