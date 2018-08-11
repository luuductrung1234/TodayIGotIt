(function() {
    angular.module("app.admin.home")
        .controller("AdminHome", function($window, $scope, $rootScope, $cookies, $cookieStore, CourseSvc, UserSvc, InstructorSvc) {
            $scope.totalCourses = 0;
            $scope.totalUsers = 0;
            $scope.totalInstructors = 0;

            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            }

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.totalCourses = response.length;
                    }, function(err) {
                        console.log("Error: " + err);
                    });

                var cntInit = 0;
                var initInterval = setInterval(function() {
                    cntInit += 100;

                    if ($rootScope.curLogin.userRoles[0].type == 'ADMIN') {
                        UserSvc.findAllUser($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            .then(function(response) {
                                $scope.totalUsers = response.length;
                            }, function(err) {
                                console.log("Error: " + err);
                            });

                        InstructorSvc.findAllInstructor($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            .then(function(response) {
                                $scope.totalInstructors = response.length;
                            }, function(err) {
                                console.log("Error: " + err);
                            });

                        clearInterval(initInterval);
                    }

                    if (cntInit >= 5000) {
                        clearInterval(initInterval);
                    }
                }, 100);
            });
        });
})();