(function() {
    angular.module("app.admin.course")
        .controller("AdminCourse", function($window, $scope, $rootScope, CourseSvc) {
            // if ($rootScope.curLogin.length == 0 || $rootScope.curLogin[0].username == undefined || $rootScope.curLogin[0].username == null || $rootScope.curLogin[0].role != "admin") {
            //     $window.location.href = "#/home";
            // }

            $scope.allcourses = [];
            $scope.startAdminCourseFrom = 0;

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse('')
                    .then(function(response) {
                        $scope.allcourses = response;
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
                        // $(elem).scope().startAdminCourseFrom = +aa;
                        // $(elem).scope().$apply();

                        // $('.startAdminCourseFromIndex').removeClass('active');
                        // $('#startAdminCourseFromIndex' + $(this).scope().startAdminCourseFrom).addClass('active');

                        // console.log($(this));
                        // console.log(aa);

                        return false;
                    })
                }
            }
        });
})();