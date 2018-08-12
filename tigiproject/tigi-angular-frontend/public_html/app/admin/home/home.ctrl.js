(function() {
    angular.module("app.admin.home")
        .controller("AdminHome", function($window, $scope, $rootScope, $cookies, $cookieStore, CourseSvc, UserSvc, InstructorSvc, StatisticSvc) {
            $scope.totalCourses = 0;
            $scope.totalUsers = 0;
            $scope.totalInstructors = 0;
            $scope.courseMostBuy = [];
            $scope.courseMostRate = [];

            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                var cnt = 100;
                var init = setInterval(function() {
                    cnt += 100;
                    if ($rootScope.curLogin != null) {
                        if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                            $window.location.href = "#/home";
                            clearInterval(init);
                        }
                    }

                    if (cnt == (10 * 60 * 1000)) {
                        clearInterval(init);
                    }
                }, 100);


            }

            $scope.$on('$viewContentLoaded', function() {
                $scope.courseMostBuy = [];
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.totalCourses = response.length;
                    }, function(err) {
                        console.log("Error: " + err);
                    });

                var cntInit = 0;
                var initInterval = setInterval(function() {
                    cntInit += 100;

                    if ($rootScope.curLogin != null) {
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

                            StatisticSvc.getMostCourseBuy($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $scope.courseMostBuy = response;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            StatisticSvc.getMostCourseRate($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    $scope.courseMostRate = response;
                                }, function(err) {
                                    console.log("Error: " + err);
                                });

                            clearInterval(initInterval);
                        }
                    }

                    if (cntInit >= (5 * 60 * 1000)) {
                        alert("Something wrong! Please try again!");
                        clearInterval(initInterval);
                    }
                }, 100);
            });
        });
})();