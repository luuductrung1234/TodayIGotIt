(function() {
    angular.module("app.admin.home")
        .directive("statisticCourseBuy", function() {
            return {
                restrict: "AE",
                replace: true,
                scope: {
                    dpmostbuy: "="
                },
                template: "<div class='col col-df'></div>",
                link: linkFn
            }

            function linkFn(scope, elem, attrs) {
                var optionCourseMostBuy = {
                    chart: {
                        backgroundColor: {
                            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
                            stops: [
                                [0, '#777777'],
                                [1, '#333333']
                            ]
                        },
                        zoomType: 'xy'
                            // type: 'line'
                    },
                    title: {
                        text: 'Most Buy',
                        style: {
                            color: '#E0E0E3',
                            fontFamily: '\'Quicksand\''
                        }
                    },
                    // subtitle: {
                    //     text: 'Source: WorldClimate.com'
                    // },
                    xAxis: {
                        gridLineColor: '#707073',
                        labels: {
                            style: {
                                color: '#E0E0E3'
                            }
                        },
                        lineColor: '#707073',
                        minorGridLineColor: '#505053',
                        tickColor: '#707073',
                        title: {
                            text: 'Course(s)',
                            style: {
                                color: '#A0A0A3'

                            }
                        },
                        categories: [],
                        crosshair: true
                    },
                    yAxis: {
                        gridLineColor: '#707073',
                        lineColor: '#707073',
                        minorGridLineColor: '#505053',
                        tickColor: '#707073',
                        tickWidth: 1,
                        title: {
                            text: 'Total Buy',
                            style: {
                                color: '#A0A0A3'
                            }
                        },
                        labels: {
                            style: {
                                color: '#E0E0E3'
                            }
                        },
                    },
                    plotOptions: {
                        line: {
                            dataLabels: {
                                enabled: true
                            },
                            enableMouseTracking: false
                        },
                        series: {
                            dataLabels: {
                                color: '#B0B0B3'
                            },
                            marker: {
                                lineColor: '#333'
                            }
                        },
                        boxplot: {
                            fillColor: '#505053'
                        },
                        candlestick: {
                            lineColor: 'white'
                        },
                        errorbar: {
                            color: 'white'
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'left',
                        x: 50,
                        verticalAlign: 'top',
                        y: 0,
                        floating: true,
                        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#aaaaaa',
                        itemStyle: {
                            color: '#E0E0E3'
                        },
                        itemHoverStyle: {
                            color: '#FFF'
                        },
                        itemHiddenStyle: {
                            color: '#606063'
                        }
                    },
                    series: [{
                        name: 'Total Buy',
                        type: 'column',
                        data: []
                    }]
                }

                scope.$watch('dpmostbuy', onDPmostbuyChange);

                function onDPmostbuyChange(newDPmostbuy) {
                    if (newDPmostbuy) {
                        initOptionsCourseMostBuy(newDPmostbuy);
                    }
                }

                function initOptionsCourseMostBuy(dpmostbuy) {
                    if (dpmostbuy.length != 0) {
                        var data;
                        var courseNames = [];
                        var buyCounts = [];

                        dpmostbuy.forEach(item => {
                            courseNames.push(item.courseName);
                            buyCounts.push(item.buyCount);
                        });

                        optionCourseMostBuy.xAxis.categories = courseNames;
                        optionCourseMostBuy.series[0].data = buyCounts;
                        render();
                    }
                }

                function render() {
                    elem.highcharts(optionCourseMostBuy);
                }
            }
        });
})();