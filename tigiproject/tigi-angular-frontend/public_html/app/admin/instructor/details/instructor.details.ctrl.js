(function() {
    angular.module("app.admin.instructor.details")
        .controller("AdminInstructorDetails", function($window, $scope, $rootScope, $routeParams) {
            if ($rootScope.curLogin == null || $rootScope.curLogin.username === undefined || $rootScope.curLogin.roles[0].type != 'ADMIN') {
                $window.location.href = "#/home";
            }
        });
})();