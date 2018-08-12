(function() {
    angular.module("app.admin.course")
        .controller("AdminCourse", function($window, $scope, $rootScope, $cookies, $cookieStore, CourseSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            }

            $scope.allcourses = [];
            $scope.startAdminCourseFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse('')
                    .then(function(response) {
                        $scope.allcourses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });

            $scope.filterRange = function(field, minValue, maxValue) {
                if (minValue === undefined) minValue = Number.MIN_VALUE;
                if (maxValue === undefined) maxValue = Number.MAX_VALUE;

                return function predicateFunc(item) {
                    return minValue <= item[field] && item[field] <= maxValue;
                };
            };
        });
    // .directive("adminCourseNavPage", function() {
    //     return {
    //         restrict: "A",
    //         link: function(scope, elem, attrs) {
    //             $(elem).empty();
    //             $(elem).append((scope.startAdminCourseFrom + 1) + ' - ' + (scope.allcourses.length) + ' of ' + scope.allcourses.length);
    //         }
    //     }
    // });
})();