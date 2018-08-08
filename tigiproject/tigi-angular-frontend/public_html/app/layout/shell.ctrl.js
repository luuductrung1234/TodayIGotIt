(function() {
    angular.module("app.shell")
        .controller("Shell", ['$window', '$scope', '$rootScope', '$filter', '$cookies', '$cookieStore', '$routeParams', 'UserSvc', function($window, $scope, $rootScope, $filter, $cookies, $cookieStore, $routeParams, UserSvc) {
            $scope.win = $window;
            $scope.cook = $cookieStore;

            $scope.loginOnRefresh = function() {
                loginStep($cookieStore.get('curUser'), $cookieStore.get('curPass'))
            }

            $scope.loginAction = function() {
                loginStep($scope.loginUsername, $scope.loginPassword);

                if ($rootScope.curLogin.userRoles[0].type == "ADMIN") {
                    window.location.href = "#/admin/home";
                } else if ($rootScope.curLogin.userRoles[0].type == "STUDENT") {
                    window.location.href = "#/home";
                }
            }

            $rootScope.$on("GetUserInfo", function(event, userName, password) {
                reloadUserInfo(userName, password);
            });

            $rootScope.$on("LearnActivated", function(event) {
                angular.element('#topnav').css('display', 'none');
                angular.element('#footer').css('display', 'none');
            })

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
                    $scope.role == "") ? "STUDENT" : "ADMIN";
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
                $rootScope.curLogin = null;
                $cookieStore.remove('curUser');
                $cookieStore.remove('curPass');
                $rootScope.isAdminLogged = false;
                $rootScope.isUserLogged = false;
                $window.location.href = "#/home";
            }

            $scope.removeErr = function() {
                $rootScope.hasError = false;
                $rootScope.errMess = null;
            }

            $scope.checkLogin = function() {
                return $scope.isLogged;
            }

            function loginStep(user, pass) {
                UserSvc.loginAction(user, pass)
                    .then(function(response) {
                            $rootScope.curLogin = response;

                            if ($rootScope.curLogin == null) {
                                $rootScope.curLogin = null;
                                $window.location.href = "#/home";
                                $rootScope.hasError = true;
                                $rootScope.errMess = "Wrong Info";
                                $rootScope.isAdminLogged = false;
                                $rootScope.isUserLogged = false;
                            } else if ($rootScope.curLogin.userRoles[0].type == "ADMIN") {
                                $rootScope.isAdminLogged = true;
                                $rootScope.isUserLogged = false;
                                $cookieStore.put('curUser', user);
                                $cookieStore.put('curPass', pass);
                            } else if ($rootScope.curLogin.userRoles[0].type == "STUDENT") {
                                $rootScope.isAdminLogged = false;
                                $rootScope.isUserLogged = true;
                                $cookieStore.put('curUser', user);
                                $cookieStore.put('curPass', pass);
                            }

                            console.log($cookieStore.get('curUser'));
                            console.log($cookieStore.get('curPass'));
                        },
                        function(err) {
                            $rootScope.isAdminLogged = false;
                            $rootScope.isUserLogged = false;
                            console.log(err);
                        });
            }

            function reloadUserInfo(userName, password) {
                UserSvc.loginAction(userName, password)
                    .then(function(response) {
                            $rootScope.curLogin = response;
                        },
                        function(err) {
                            console.log(err);
                        });
            }
        }])
        .directive("loginModel", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {

                }
            }
        })
        .directive("signinModel", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {

                }
            }
        });
})();