(function() {
    angular.module("app.admin.user.details")
        .controller("AdminUserDetails", function($window, $scope, $rootScope, $routeParams, UserSvc) {
            $scope.curUser = [];

            // if ($rootScope.curLogin.length == 0 || $rootScope.curLogin[0].username == undefined || $rootScope.curLogin[0].username == null || $rootScope.curLogin[0].role != "admin") {
            //     $window.location.href = "#/home";
            // }

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