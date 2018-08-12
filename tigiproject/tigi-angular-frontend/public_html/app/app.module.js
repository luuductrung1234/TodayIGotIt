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
            "app.course.learn",
            "app.cart",
            "app.search",
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
                $rootScope.learning = false;
                $rootScope.isSubcribed = false;

                $rootScope.getCartCount = function() {
                    if ($rootScope.curLogin != null && $rootScope.curLogin.userRoles[0].type !== 'ADMIN') {
                        return $rootScope.curLogin.userCart.cartDetails.length;
                    } else {
                        return 0;
                    }
                }

                $rootScope.$on('CheckSubscribed', function(event, curId) {
                    checkSubcribed(curId);
                })

                function checkSubcribed(curId) {
                    $rootScope.isSubcribed = false;

                    if ($rootScope.curLogin != null) {
                        $rootScope.curLogin.userCourseOwners.forEach(function(i) {
                            if (i.id == curId) {
                                if (i.owerType == 'BUY') {
                                    $rootScope.isSubcribed = true;
                                }
                            }
                        })
                    }
                }
            }
        ]);
})();