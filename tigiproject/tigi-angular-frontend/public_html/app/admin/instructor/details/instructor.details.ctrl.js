(function() {
    angular.module("app.admin.instructor.details")
        .controller("AdminInstructorDetails", function($window, $scope, $rootScope, $routeParams) {
            if ($rootScope.curLogin.length === 0 || $rootScope.curLogin[0].userName === undefined || $rootScope.curLogin[0].role != "admin") {
                $window.location.href = "#/home";
            }
        });
})();