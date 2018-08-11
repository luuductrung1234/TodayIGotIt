(function() {
    angular.module("app.course.user")
        .controller("CourseUser", function($window, $scope, $rootScope, $cookies, $cookieStore, CourseSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'STUDENT') {
                    $window.location.href = "#/home";
                }
            }

            // $scope.mycourses = [];
            $scope.completedcourses = [];

            // $scope.$on('$viewContentLoaded', function() {
            //     $rootScope.curLogin.userCourseOwners.forEach(item => {
            //         if (item.owerType == 'BUY') {
            //             $scope.mycourses.push(item);
            //         }
            //     });
            // });
        })
        .directive("courseDescription", ['CourseSvc', function(CourseSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    CourseSvc.findByCourseId(attrs.ngClass)
                        .then(function(response) {
                            $(elem).empty();
                            $(elem).append(response.description);
                        }, function(err) {
                            console.log("Error: " + err);
                        });
                }
            }
        }])
})();