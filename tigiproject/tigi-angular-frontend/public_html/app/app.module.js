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
            "app.cart",
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
                $rootScope.curLogin = null;
                $rootScope.hasError = false;
                $rootScope.errMess = null;
                $rootScope.isAdminLogged = false;
                $rootScope.isUserLogged = false;

                $rootScope.getCartCount = function() {
                    if ($rootScope.curLogin != null && $rootScope.curLogin.userRoles[0].type !== 'ADMIN') {
                        return $rootScope.curLogin.userCart.cartDetails.length;
                    } else {
                        return 0;
                    }
                }
            }
        ]);
})();