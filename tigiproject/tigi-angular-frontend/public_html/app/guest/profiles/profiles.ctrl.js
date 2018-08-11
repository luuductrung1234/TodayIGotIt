(function() {
    angular.module("app.profiles")
        .controller("Profiles", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams, UserSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null ||
                $rootScope.curLogin.userName === undefined) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'STUDENT') {
                    $window.location.href = "#/home";
                }
            }

            $scope.editPersonalInfo = function() {
                editAction();
            }

            $scope.editContactInfo = function() {
                editAction();
            }

            $scope.changPass = function() {
                $rootScope.curLogin[0].password = $scope.newPass;
                editAction();
            }

            function editAction() {
                $rootScope.curLogin[0].lastUpdated = new Date();
                var data = $rootScope.curLogin[0];

                UserSvc.editAction($rootScope.curLogin[0].id, data)
                    .then(function(response) {
                            console.log(response);
                        },
                        function(err) {
                            console.log(err);
                        });
            }
        });
})();