(function() {
    angular.module("app.admin.home")
        .controller("AdminHome", function($window, $scope, $rootScope) {
            $scope.$on('$viewContentLoaded', function() {
                if ($rootScope.curLogin == null || $rootScope.curLogin.userName === undefined || $rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            });
        });
})();