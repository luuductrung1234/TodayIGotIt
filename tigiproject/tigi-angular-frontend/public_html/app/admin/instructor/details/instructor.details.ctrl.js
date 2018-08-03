(function() {
    angular.module("app.admin.instructor.details")
        .controller("AdminInstructorDetails", function($window, $scope, $rootScope, $routeParams) {
            if ($rootScope.curLogin == null || $rootScope.curLogin[0].username == undefined || $rootScope.curLogin[0].username == null || $rootScope.curLogin[0].role != "admin") {
                $window.location.href = "#/home";
            }
        });
})();