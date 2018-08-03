(function() {
    var name = "app",
        requires = [
            "app.shell",
            "app.data",
            "app.home",
            "app.course",
            "app.about",
            "app.course.details",
            "app.profiles",
            "app.course.user",
            "app.admin.home",
            "app.admin.course",
            "app.admin.instructor",
            "app.admin.user",
            "app.admin.course.details",
            "app.admin.instructor.details",
            "app.admin.user.details",
            "ngCookies"
        ];

    angular.module(name, requires)
        .run(['$route', function($route) {
            $route.reload();
        }])
        .run(['$window', '$rootScope', '$cookies', '$cookieStore', 'UserSvc',
            function($window, $rootScope, $cookies, $cookieStore, UserSvc) {
                $rootScope.curLogin = [];
                $rootScope.hasError = false;
                $rootScope.errMess = null;

                $rootScope.loginStep = function(user, pass) {
                    UserSvc.loginAction(user, pass)
                        .then(function(response) {
                                $rootScope.curLogin = response;

                                if ($rootScope.curLogin.length == 0 || $rootScope.curLogin == null) {
                                    $rootScope.curLogin = null;
                                    $window.location.href = "#/home";
                                    $rootScope.hasError = true;
                                    $rootScope.errMess = "Wrong Info";
                                } else if ($rootScope.curLogin[0].role == "admin") {
                                    if ($cookieStore.get('curUser') === undefined ||
                                        $cookieStore.get('curPass') === undefined) {
                                        $cookieStore.put('curUser', user);
                                        $cookieStore.put('curPass', pass);
                                        $window.location.href = "#/admin/home";
                                    } else {
                                        $cookieStore.put('curUser', user);
                                        $cookieStore.put('curPass', pass);
                                    }
                                } else if ($rootScope.curLogin[0].role == "user") {
                                    if ($cookieStore.get('curUser') === undefined ||
                                        $cookieStore.get('curPass') === undefined) {
                                        $cookieStore.put('curUser', user);
                                        $cookieStore.put('curPass', pass);
                                        $window.location.href = "#/home";
                                    } else {
                                        $cookieStore.put('curUser', user);
                                        $cookieStore.put('curPass', pass);
                                    }
                                }
                            },
                            function(err) {
                                console.log(err);
                            });
                }

                $rootScope.logoutAction = function() {
                    $rootScope.curLogin = null;
                    $cookieStore.remove('curUser');
                    $cookieStore.remove('curPass');
                    $window.location.href = "#/home";
                }

                // $cookies.curLogin = someSessionObj;
                // $scope.usingCookies = { 'cookies.dotobject': $cookies.dotobject, "cookieStore.get": $cookieStore.get('dotobject') };

                if ($cookieStore.get('curUser') !== undefined ||
                    $cookieStore.get('curPass') !== undefined) {
                    console.log($cookieStore.get('curUser'));
                    console.log($cookieStore.get('curPass'));
                    $rootScope.loginStep($cookieStore.get('curUser'), $cookieStore.get('curPass'));
                } else {
                    $window.location.href = "#/home";
                }
                // $cookieStore.put('curLogin', someSessionObj);
                // $scope.usingCookieStore = { "cookieStore.get": $cookieStore.get('obj'), 'cookies.dotobject': $cookies.obj, };
            }
        ]);
})();