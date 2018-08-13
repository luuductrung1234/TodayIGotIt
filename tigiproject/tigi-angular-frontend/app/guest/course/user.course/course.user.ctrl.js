(function() {
    angular.module("app.course.user")
        .controller("CourseUser", function($window, $scope, $rootScope, $cookies, $cookieStore, CourseSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin != null) {
                    if ($rootScope.curLogin.userRoles[0].type != 'STUDENT') {
                        $window.location.href = "#/home";
                    }
                } else {
                    $window.location.href = "#/home";
                }
            }

            $scope.startMyCourseFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                var cnt = 0;
                var init = setInterval(function() {
                    cnt += 100;
                    if ($rootScope.curLogin != null) {
                        if ($rootScope.curLogin.userRoles[0].type == 'STUDENT') {
                            CourseSvc.getMyCourses($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $rootScope.mycourses = response;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            clearInterval(init);
                        }
                    }

                    if (cnt >= (5 * 60 * 1000)) {
                        clearInterval(init);
                    }
                }, 100);
            })
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
        .directive("currentInstructorInUserCourse", ['InstructorSvc', function(InstructorSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    var curIns = null;

                    InstructorSvc.findCourseOwner(attrs.ngClass)
                        .then(function(response) {
                            curIns = response;
                            $(elem).append('<a href="#" class="intro-link">' + curIns.firstName + " " + curIns.lastName + '</a>');
                        }, function(err) {
                            console.log("Error: " + err);
                        });
                }
            }
        }])
})();