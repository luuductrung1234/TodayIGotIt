(function() {
    angular.module("app.admin.user.details")
        .controller("AdminUserDetails", function($window, $scope, $rootScope, $routeParams, UserSvc) {
            $scope.curUser = [];

            if ($rootScope.curLogin == null || $rootScope.curLogin.userName === undefined || $rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                $window.location.href = "#/home";
            }

            if ($routeParams.id !== undefined) {
                getCurUserInfo($routeParams.id);
            }

            function getCurUserInfo(id) {
                UserSvc.findByUsername(id)
                    .then(function(response) {
                        $scope.curUser = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }
        });
})();