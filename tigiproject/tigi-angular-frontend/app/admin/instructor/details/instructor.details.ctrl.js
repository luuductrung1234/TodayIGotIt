(function() {
    angular.module("app.admin.instructor.details")
        .controller("AdminInstructorDetails", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams, $q, InstructorSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            }

            if ($routeParams.id !== undefined) {
                getCurInstructorInfo($routeParams.id);
            }

            $scope.current = null;
            $scope.relatedCourses = [];

            function getCurInstructorInfo(id) {

            }

            $scope.$on('$viewContentLoaded', function() {

            })
        });
})();