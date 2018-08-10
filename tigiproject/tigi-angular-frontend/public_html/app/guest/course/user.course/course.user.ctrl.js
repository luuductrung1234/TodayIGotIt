(function() {
    angular.module("app.course.user")
        .controller("CourseUser", function($scope, $rootScope, CourseSvc) {
            if ($rootScope.curLogin.length === 0 || $rootScope.curLogin.userName === undefined) {
                $window.location.href = "#/home";
            }

            $scope.mycourses = [];
            $scope.completedcourses = [];

            $scope.$on('$viewContentLoaded', function() {
                $rootScope.curLogin.userCourseOwners.forEach(item => {
                    if (item.owerType == 'BUY') {
                        $scope.mycourses.push(item);
                    }
                });
            });
        });
})();