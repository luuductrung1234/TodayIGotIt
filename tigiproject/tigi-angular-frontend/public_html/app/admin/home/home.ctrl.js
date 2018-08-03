(function() {
    angular.module("app.admin.home")
        .controller("AdminHome", function($window, $scope, $rootScope) {
            $scope.$on('$viewContentLoaded', function() {
                if ($rootScope.curLogin == null || $rootScope.curLogin[0].username == undefined || $rootScope.curLogin[0].username == null || $rootScope.curLogin[0].role != "admin") {
                    $window.location.href = "#/home";
                }
            });
        });
})();