(function() {
    angular.module("app.admin.user")
        .controller("AdminUser", function($window, $scope, $rootScope, $cookies, $cookieStore, UserSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            }

            $scope.users = [];
            $scope.startAdminUserFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                UserSvc.findAllUser($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.users = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });

            $scope.filterCoursesOrder = function(field, minValue, maxValue) {
                if (minValue === undefined || minValue === null) minValue = Number.MIN_VALUE;
                if (maxValue === undefined || maxValue === null) maxValue = Number.MAX_VALUE;

                return function predicateFunc(item) {
                    return minValue <= item[field].length && item[field].length <= maxValue;
                };
            };
        });
    // .directive("adminUserNavPage", function() {
    //     return {
    //         restrict: "A",
    //         link: function(scope, elem, attrs) {
    //             $(elem).empty();
    //             $(elem).append((scope.startAdminUserFrom + 1) + ' - ' + (scope.users.length) + ' of ' + scope.users.length);
    //         }
    //     }
    // });
})();