(function() {
    angular.module("app.admin.home")
        .directive("statisticInsMostBuy", function() {
            return {
                restrict: "AE",
                replace: true,
                scope: {
                    dpins: "="
                },
                template: "<div class='col col-df' style='width: 100%; overflow: auto;'></div>",
                link: linkFn
            }

            function linkFn(scope, elem, attrs) {
                scope.$watch('dpins', onDPinsChange);

                function onDPinsChange(newDPins) {
                    if (newDPins) {
                        initOptionsCourseMostRate(newDPins);
                    }
                }

                function initOptionsCourseMostRate(dpins) {
                    if (dpins.length != 0) {
                        var str = '<table class="course-table" style="width: inherit; max-height: 400px;">' +
                            '<tr>' +
                            '<th>Full Name</th>' +
                            '<th>Number of course selled</th>' +
                            '<th>Action</th>' +
                            '</tr>';

                        dpins.forEach(item => {
                            str += '<tr><td>' + item.instructor.firstName + ' ' + item.instructor.lastName + '</td>' +
                                '<td>' + item.totalBuy + '</td>' +
                                '<td><a href="#/instructor/' + item.instructor.customerId + '" class="action-link"><i class="fas fa-eye"></i></a></td></tr>';
                        });

                        str += '</table>';

                        $(elem).empty();
                        $(elem).append(str);
                    }
                }
            }
        })
})();