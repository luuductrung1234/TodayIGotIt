(function() {
    angular.module("app.admin.home")
        .controller("AdminHome", function($window, $scope, $rootScope) {
            $scope.$on('$viewContentLoaded', function() {
                if ($rootScope.curLogin.length === 0 || $rootScope.curLogin[0].userName === undefined || $rootScope.curLogin[0].role != "admin") {
                    $window.location.href = "#/home";
                }
            });
        });
})();