
channelReport.$inject = ['$interval', 'DashboardService'];
function channelReport($interval, DashboardService) {
    "use strict";

    return {
        restrict: 'E',
        template: require('./report.html'),
        scope: {
            ch: '=channel'
        },
        link: function (scope, el, attrs, ctrl) {
            var chart;
            var lastDate = new Date().getTime() - 60 * 60 * 1000;

            DashboardService.channelsHistory({channels: [scope.ch.name], from: lastDate}).then(function (data) {
                var chartData = [];
                var d = data[0];
                if (d) {
                    if (d.dates.length) {
                        for (var i = 0; i < d.dates.length; ++i) {
                            chartData.push({
                                x: d.dates[i],
                                y: d.viewers[i]
                            })
                        }
                        lastDate = d.dates[d.dates.length - 1];
                    }
                }
                chart = createChart($(el).find('.chart'), chartData);
            });

            var polling = startPolling($interval, DashboardService);

            scope.$on('$destroy', function () {
                if (chart) {
                    chart.destroy();
                    chart = null;
                }
                if (polling) {
                    $interval.cancel(polling);
                    polling = undefined;
                }
            })

        }
    }
}

function startPolling($interval, DashboardService) {
    return $interval(function () {
        var req = DashboardService.channelsHistory({channels: [scope.ch.name], from: lastDate}).then(function (data) {
            var d = data[0];
            if (d) {
                for (var i = 0; i < d.dates.length; ++i) {
                    chart.series[0].addPoint([d.dates[i],  d.viewers[i]], true, true);
                }
                lastDate = d.dates[d.dates.length - 1];
            }
        });
    }, 30000);
}

function createChart(element, data) {
    var options = {
        chart: {
            type: 'spline',
            height: 250,
            animation: Highcharts.svg,
            marginRight: 0
        },
        title: {text: ''},
        xAxis: {type: 'datetime', tickPixelInterval: 150},
        yAxis: {
            title: {text: 'Viewers'},
            plotLines: [{value: 0, width: 1, color: '#808080'}]
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                    Highcharts.numberFormat(this.y, 2);
            }
        },
        plotOptions: {
            spline: {
                lineWidth: 1
            }
        },
        legend: {enabled: false},
        exporting: {enabled: false},
        series: [{name: 'Viewers', data: data}]
    };
    var ch = $(element).highcharts(options);

    return ch.highcharts();
}

export default angular.module('app.components.channelreport', [])
    .directive('appChannelReport', channelReport)
    .name
