/**
 * 
 */

var easyorder = window.easyorder || easyorder || {};

easyorder.charts = {};

var options = {
	ele: "#xxxx",						// 绑定元素
	title: "",							// 主标题
	subTitle: "",						// 副标题
	xAxisData: [],
	yAxisSetting: [],
	series: []
}; 


easyorder.charts.lineColumnar = function(options) {
	if(!options.ele) {
		throw new Error('The options[ele] is empty, charts is unable to work.');
		return;
	}
	var yAxisSetting = options.yAxisSetting;
	if(!yAxisSetting) {
		throw new Error('The options[yAxisSetting] no result, charts is unable to work.');
		return;
	}
	
	var seriesSetting = options.series;
	if(!seriesSetting) {
		throw new Error('The options[series] no result, charts is unable to work.');
		return;
	}
	
	var yAxis = [];
	$.each(yAxisSetting, function(index, setting) {
		var axis = {};
		axis.labels = {};
		axis.labels.format = '{value} ' + setting.suffix;
		axis.labels.style = {
			color: Highcharts.getOptions().colors[index]
		};
		axis.title = {};
		axis.title.text = setting.text;
		axis.title.style = {
			color: Highcharts.getOptions().colors[index]
		};
		if(index != 0) {
			axis.opposite = true;
		}
		yAxis.push(axis);
		
	});
	
	var series = [];
	$.each(seriesSetting, function(index, setting) {
		var sery = {};
		sery.name = setting.name;
		sery.type = 'spline';
		if(index == 0) {
			sery.type = 'column';
		}
		sery.tooltip = {};
		sery.tooltip.valueSuffix = setting.suffix;
		sery.data = setting.data;
		if(setting.yAxis) {
			sery.yAxis = setting.yAxis;
		}
		series.push(sery);
	});
	
	
	
	
	$(options.ele).highcharts({
        chart: {
            zoomType: 'xy'
        },
        credits: {
            enabled:false
        },
        title: {
            text: options.title
        },
        subtitle: {
            text: options.subTitle
        },
        xAxis: [{
            categories: options.xAxisData,
            crosshair: true
        }],
        yAxis: yAxis,
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 120,
            verticalAlign: 'top',
            y: 100,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: series
    });
}