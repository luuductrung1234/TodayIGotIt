(function() {
    angular.module("app.admin.home")
        .directive("statisticReceipt", function() {
            return {
                restrict: "AE",
                replace: true,
                scope: {
                    dpreceipt: "="
                },
                template: "<div class='col col-df' style='width: 90%'></div>",
                link: linkFn
            }

            function linkFn(scope, elem, attrs) {
                var optionReceipt = {
                    chart: {
                        backgroundColor: {
                            linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
                            stops: [
                                [0, '#777777'],
                                [1, '#333333']
                            ]
                        },
                        zoomType: 'xy',
                        type: 'line'
                    },
                    title: {
                        text: 'Receipt',
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
                        // title: {
                        //     text: '(s)',
                        //     style: {
                        //         color: '#A0A0A3'
                        //     }
                        // },
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
                            text: 'Total ($)',
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
                        name: 'Total Receipt',
                        data: []
                    }]
                }

                scope.$watch('dpreceipt', onDPreceiptChange);

                function onDPreceiptChange(newDPreceipt) {
                    if (newDPreceipt) {
                        initOptionsReceipt(newDPreceipt);
                    }
                }

                function initOptionsReceipt(dpreceipt) {
                    var data;
                    var totalAmounts = [];
                    var units = [];

                    if (scope.$parent.receiptType === 'day') {
                        dpreceipt.forEach(item => {
                            totalAmounts.push(item.totalAmount);
                            units.push(item.day);
                        });
                    } else if (scope.$parent.receiptType === 'month') {
                        dpreceipt.forEach(item => {
                            totalAmounts.push(item.totalAmount);
                            units.push(item.month);
                        });
                    } else if (scope.$parent.receiptType === 'year') {
                        dpreceipt.forEach(item => {
                            totalAmounts.push(item.totalAmount);
                            units.push(item.year);
                        });
                    }

                    optionReceipt.xAxis.categories = units;
                    optionReceipt.series[0].data = totalAmounts;
                    render();
                }

                function render() {
                    elem.highcharts(optionReceipt);
                }
            }
        });
})();