(function() {
    angular.module("app.admin.instructor")
        .controller("AdminInstructor", function($window, $scope, $rootScope, InstructorSvc) {
            if ($rootScope.curLogin == null || $rootScope.curLogin.userName === undefined || $rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                $window.location.href = "#/home";
            }

            $scope.instructors = [];
            $scope.startAdminInstructorFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                InstructorSvc.findAllInstructor()
                    .then(function(response) {
                        $scope.instructors = response;
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