(function() {
    angular.module("app.admin.instructor.details")
        .controller("AdminInstructorDetails", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            }
        });
})();