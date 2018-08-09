(function() {
    angular.module("app.course.learn")
        .controller("CourseLearn", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams, CourseSvc) {
            $scope.current = null;

            if ($rootScope.curLogin == null || $rootScope.curLogin.userName === undefined) {
                $window.location.href = "#/home";
            }

            if ($routeParams.id !== undefined) {
                getByCourseId($routeParams.id);
            }

            $scope.$on('$viewContentLoaded', function() {
                $rootScope.learning = true;
                $scope.$apply();
            });

            function getByCourseId(id) {
                CourseSvc.findByCourseId(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.current = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }
        })
})();