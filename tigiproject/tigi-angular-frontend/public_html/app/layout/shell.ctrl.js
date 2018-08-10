(function() {
    angular.module("app.shell")
        .controller("Shell", ['$q', '$window', '$scope', '$rootScope', '$filter', '$cookies', '$cookieStore', '$routeParams', 'UserSvc', function($q, $window, $scope, $rootScope, $filter, $cookies, $cookieStore, $routeParams, UserSvc) {
            $scope.win = $window;
            $scope.cook = $cookieStore;

            $scope.$on("$viewContentLoaded", function() {
                if ($cookieStore.get('curUser') != undefined) {
                    loginStep($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                        .then(function(data) {

                        }, function(err) {
                            console.log("Error: " + err);
                        });
                }
            })

            $scope.loginAction = function() {
                let deferred = $q.defer();

                loginStep($scope.loginUsername, $scope.loginPassword)
                    .then(function(data) {
                        if ($rootScope.curLogin.userRoles[0].type == "ADMIN") {
                            window.location.href = "#/admin/home";
                        } else if ($rootScope.curLogin.userRoles[0].type == "STUDENT") {
                            window.location.href = "#/home";
                        }

                        deferred.resolve(data);
                    }, function(err) {
                        console.log("Error: " + err);
                        deferred.reject(err);
                    });

                return deferred.promise;
            }

            $rootScope.$on("GetUserInfo", function(event, userName, password) {
                reloadUserInfo(userName, password);
            });

            $rootScope.$on("LearnActivated", function(event) {
                angular.element('#topnav').css('display', 'none');
                angular.element('#footer').css('display', 'none');
            })

            $scope.signinAction = function() {
                let deferred = $q.defer();

                var userName = $scope.signinUsername;
                var password = $scope.signinPassword;
                var firstName = $scope.signinfname;
                var lastName = $scope.signinlname;
                var gender = $scope.signingender;
                var dob = $scope.signindob;
                var email = $scope.signinemail;
                var phoneNumber = $scope.signinphone;
                var userCart = [];
                var userOrders = [];
                var userCourseOwners = [];
                var userRoles = [{
                    id: 1,
                    dateCreated: Date.parse(new Date()),
                    lastUpdated: Date.parse(new Date()),
                    type: "STUDENT"
                }];

                var addressLine1 = ($scope.signinaddressLine == null ||
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

                var customer = {
                    billingAddress: {
                        addressLine1: addressLine1,
                        addressLine2: "",
                        city: addressCity,
                        state: addressState,
                        zipCode: addressZipcode
                    },
                    dateCreated: Date.parse(new Date()),
                    email: email,
                    firstName: firstName,
                    id: 0,
                    lastName: lastName,
                    lastUpdated: Date.parse(new Date()),
                    phoneNumber: phoneNumber,
                    shippingAddress: {
                        addressLine1: addressLine1,
                        addressLine2: "",
                        city: addressCity,
                        state: addressState,
                        zipCode: addressZipcode
                    }
                }

                var data = {
                    userName,
                    password,
                    userRoles,
                    userCart,
                    userCourseOwners,
                    userOrders,
                    customer,
                    dateCreated,
                    lasUpdated
                }

                UserSvc.signinAction(data)
                    .then(function(response) {
                            loginStep(data.userName, data.password);
                            deferred.resolve();
                        },
                        function(err) {
                            console.log(err);
                            deferred.reject(err);
                        });

                return deferred.promise;
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
                let deferred = $q.defer();

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

                            deferred.resolve();
                        },
                        function(err) {
                            $rootScope.isAdminLogged = false;
                            $rootScope.isUserLogged = false;
                            console.log(err);
                            deferred.reject(err);
                        });

                return deferred.promise;
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
                    $(elem).children().eq(0).children().eq(0).children().eq(0).on('submit', function() {
                        scope.loginAction()
                            .then(function(data) {
                                if (scope.$root.curLogin != null) {
                                    $(elem).removeClass('show');
                                } else {

                                }
                            }, function(err) {
                                console.log("Error: " + err);
                            });
                    })
                }
            }
        })
        .directive("signinModel", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).children().eq(0).children().eq(0).children().eq(0).on('submit', function() {
                        scope.signinAction()
                            .then(function(data) {
                                if (scope.$root.curLogin != null) {
                                    $(elem).removeClass('show');
                                } else {

                                }
                            }, function(err) {
                                console.log("Error: " + err);
                            });
                    })
                }
            }
        });
})();