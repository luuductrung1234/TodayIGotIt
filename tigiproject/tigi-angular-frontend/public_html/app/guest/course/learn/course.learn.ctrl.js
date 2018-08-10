(function() {
    angular.module("app.course.learn")
        .controller("CourseLearn", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams, CourseSvc) {
            $scope.current = null;
            $scope.resources = [];
            $scope.sourceVideo = null;

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

            $scope.outCourse = function() {
                $rootScope.learning = false;
                $scope.$apply();
                $window.location.href = "#/course/" + $scope.current.courseId;
            }

            function getByCourseId(id) {
                CourseSvc.findByCourseId(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.current = response;
                        getResources(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'));
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }

            function getResources(id, username, password) {
                CourseSvc.getAllResources(id, username, password)
                    .then(function(response) {
                        $scope.resources = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    })
            }
        })
        .directive("navigateChapter", ['$cookies', '$cookieStore', 'CourseSvc', function($cookies, $cookieStore, CourseSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {

                        return false;
                    })
                }
            }
        }])
})();