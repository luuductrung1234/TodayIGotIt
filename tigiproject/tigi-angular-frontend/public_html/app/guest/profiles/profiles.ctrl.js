(function() {
    angular.module("app.profiles")
        .controller("Profiles", function($window, $scope, $rootScope, $routeParams, UserSvc) {
            if ($rootScope.curLogin.length === 0 || $rootScope.curLogin[0].userName === undefined) {
                $window.location.href = "#/home";
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