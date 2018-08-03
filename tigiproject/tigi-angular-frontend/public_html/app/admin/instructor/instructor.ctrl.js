(function() {
    angular.module("app.admin.instructor")
        .controller("AdminInstructor", function($window, $scope, $rootScope, UserSvc) {
            if ($rootScope.curLogin == null || $rootScope.curLogin[0].username == undefined || $rootScope.curLogin[0].username == null || $rootScope.curLogin[0].role != "admin") {
                $window.location.href = "#/home";
            }

            $scope.users = [];
            $scope.startAdminInstructorFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                UserSvc.findAllUser()
                    .then(function(response) {
                        $scope.users = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });
        })
        .directive("navPageLink", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {
                        // var aa = $(this).attr('id').substr(14);
                        // $(elem).scope().startAdminInstructorFrom = +aa;
                        // $(elem).scope().$apply();

                        // $('.startAdminInstructorFromIndex').removeClass('active');
                        // $('#startAdminInstructorFromIndex' + $(this).scope().startAdminInstructorFrom).addClass('active');

                        // console.log($(this));
                        // console.log(aa);

                        return false;
                    })
                }
            }
        });
})();