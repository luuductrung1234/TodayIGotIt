(function() {
    angular.module("app.admin.home")
        .controller("AdminHome", function($window, $scope, $rootScope, $cookies, $cookieStore, CourseSvc, UserSvc, InstructorSvc) {
            $scope.totalCourses = 0;
            $scope.totalUsers = 0;
            $scope.totalInstructors = 0;

            $scope.$on('$viewContentLoaded', function() {
                if ($rootScope.curLogin == null || $rootScope.curLogin.userName === undefined || $rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }

                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.totalCourses = response.length;
                    }, function(err) {
                        console.log("Error: " + err);
                    });

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
            });
        });
})();