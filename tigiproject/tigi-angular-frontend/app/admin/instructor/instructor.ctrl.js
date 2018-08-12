(function() {
    angular.module("app.admin.instructor")
        .controller("AdminInstructor", function($window, $scope, $rootScope, $cookies, $cookieStore, InstructorSvc) {
            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                    $window.location.href = "#/home";
                }
            }

            $scope.instructors = [];
            $scope.startAdminInstructorFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                InstructorSvc.findAllInstructor($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.instructors = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });
        });
    // .directive("navPageLink", function() {
    //     return {
    //         restrict: "A",
    //         link: function(scope, elem, attrs) {
    //         }
    //     }
    // });
})();