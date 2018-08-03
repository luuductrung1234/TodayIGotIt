(function() {
    angular.module("app.course.user")
        .controller("CourseUser", function($scope, $rootScope, CourseSvc) {
            if ($rootScope.curLogin[0].username == undefined || $rootScope.curLogin[0].username == null) {
                $window.location.href = "#/home";
            }

            $scope.javaavacourses = null;

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse('java')
                    .then(function(response) {
                        $scope.javaavacourses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });
        });
})();