(function() {
    angular.module("app.shell")
        .controller("Shell", ['$window', '$scope', '$rootScope', '$filter', '$cookies', '$cookieStore', 'UserSvc', function($window, $scope, $rootScope, $filter, $cookies, $cookieStore, UserSvc) {
            $scope.win = $window;
            $scope.cook = $cookieStore;
            // $scope.isLogged = false;

            // $scope.$on('$viewContentLoaded', function() {
            //     if ($cookieStore.get('curUser') !== undefined && $cookieStore.get('curPass') !== undefined) {
            //         loginStep($cookieStore.get('curUser'), $cookieStore.get('curPass'));
            //     }
            // });

            $scope.loginAction = function() {
                $rootScope.loginStep($scope.loginUsername, $scope.loginPassword);
            }

            $scope.signinAction = function() {
                var userName = $scope.signinUsername;
                var password = $scope.signinPassword;
                var fname = $scope.signinfname;
                var lname = $scope.signinlname;
                var gender = $scope.signingender;
                var dob = $scope.signindob;
                var email = $scope.signinemail;
                var phone = $scope.signinphone;
                var role = ($scope.role == null ||
                    $scope.role == undefined ||
                    $scope.role == "") ? "user" : "admin";
                var addressLine = ($scope.signinaddressLine == null ||
                    $scope.signinaddressLine == undefined ||
                    $scope.signinaddressLine == "") ? "" : $scope.signinaddressLine;
                var addressCity = ($scope.signinaddressCity == null ||
                    $scope.signinaddressCity == undefined ||
                    $scope.signinaddressCity == "") ? "" : $scope.signinaddressCity;
                var addressState = ($scope.signinaddressState == null ||
                    $scope.signinaddressState == undefined ||
                    $scope.signinaddressState == "") ? "" : $scope.signinaddressState;
                var addressZipcode = ($scope.signinaddressZipcode == null ||
                    $scope.signinaddressZipcode == undefined ||
                    $scope.signinaddressZipcode == "") ? "" : $scope.signinaddressZipcode;
                var dateCreated = new Date();
                var lasUpdated = new Date();

                var data = {
                    userName,
                    password,
                    fname,
                    lname,
                    gender,
                    dob,
                    email,
                    phone,
                    role,
                    addressLine,
                    addressCity,
                    addressState,
                    addressZipcode,
                    dateCreated,
                    lasUpdated
                }

                UserSvc.signinAction(data)
                    .then(function(response) {
                            $rootScope.loginStep(data.userName, data.password);
                        },
                        function(err) {
                            console.log(err);
                        });
            }

            $scope.logoutAction = function() {
                $rootScope.logoutAction();
                $window.location.href = "#/home";
            }

            $scope.removeErr = function() {
                $rootScope.hasError = false;
                $rootScope.errMess = null;
            }

            $scope.checkLogin = function() {
                return $scope.isLogged;
            }
        }]);
})();