(function() {
    angular.module("app.profiles")
        .controller("Profiles", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams, UserSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'STUDENT' && $rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            }

            $scope.editPersonalInfo = function() {
                $rootScope.curLogin.passwordText = $cookieStore.get('curPass');
                $rootScope.curLogin.passwordTextConf = $cookieStore.get('curPass');
                editAction();
            }

            $scope.editContactInfo = function() {
                $rootScope.curLogin.passwordText = $cookieStore.get('curPass');
                $rootScope.curLogin.passwordTextConf = $cookieStore.get('curPass');
                editAction();
            }

            $scope.changPass = function() {
                $rootScope.curLogin.passwordText = $scope.newPass;
                $rootScope.curLogin.passwordTextConf = $scope.passRepeat;
                editAction();
            }

            function editAction() {
                $rootScope.curLogin.lastUpdated = Date.parse(new Date());
                var data = $rootScope.curLogin;

                UserSvc.editAction($rootScope.curLogin.userId, data, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                            console.log(response);
                        },
                        function(err) {
                            console.log(err);
                        });
            }
        });
})();