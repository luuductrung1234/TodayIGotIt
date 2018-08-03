(function() {
    angular.module("app.course")
        .controller("Course", function($scope, CourseSvc) {
            $scope.courses = [];

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.courses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });
        });
})();