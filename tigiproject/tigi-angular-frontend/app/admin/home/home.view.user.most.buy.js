(function() {
    angular.module("app.admin.home")
        .directive("statisticUserMostBuy", function() {
            return {
                restrict: "AE",
                replace: true,
                scope: {
                    dpuser: "="
                },
                template: "<div class='col col-df' style='width: 100%; overflow: auto;'></div>",
                link: linkFn
            }

            function linkFn(scope, elem, attrs) {
                scope.$watch('dpuser', onDPuserChange);

                function onDPuserChange(newDPuser) {
                    if (newDPuser) {
                        initOptionsCourseMostRate(newDPuser);
                    }
                }

                function initOptionsCourseMostRate(dpuser) {
                    if (dpuser.length != 0) {
                        var str = '<table class="course-table" style="width: inherit; max-height: 400px;">' +
                            '<tr>' +
                            '<th>Full Name</th>' +
                            '<th>Number of course selled</th>' +
                            '<th>Action</th>' +
                            '</tr>';

                        dpuser.forEach(item => {
                            str += '<tr><td>' + item.student.firstName + ' ' + item.student.lastName + '</td>' +
                                '<td>' + item.totalBuy + '</td>' +
                                '<td><a href="#/student/' + item.student.customerId + '" class="action-link"><i class="fas fa-eye"></i></a></td></tr>';
                        });

                        str += '</table>';

                        $(elem).empty();
                        $(elem).append(str);
                    }
                }
            }
        })
})();