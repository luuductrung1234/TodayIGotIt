(function() {
    angular.module("app.home")
        .controller("Home", function($scope, CourseSvc) {
            $scope.courses = [];
            $scope.courseImage = null;

            $scope.$on('$viewContentLoaded', function() {
                // CourseSvc.findAllCourse()
                //     .then(function(response) {
                //         $scope.courses = response;
                //     }, function(err) {
                //         console.log("Error: " + err);
                //     });
            });

            $scope.getCourseImage = function(curId) {
                return CourseSvc.getImage(curId);

                $scope.$apply();
            }
        })
        .directive("addToFavorite", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {
                        scope.$root.curLogin.userCart.cartDetails.splice(attrs.ngClass, 1);
                        scope.$apply();
                        return false;
                    })
                }
            }
        })
        .directive("currentInstructor", ['InstructorSvc', function(InstructorSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    var curIns = null;

                    InstructorSvc.findCourseOwner(attrs.ngClass)
                        .then(function(response) {
                            curIns = response;
                            $(elem).append('<a href="#" class="intro-link">' + curIns.firstName + " " + curIns.lastName + '</a>');
                        }, function(err) {
                            console.log("Error: " + err);
                        });
                }
            }
        }])
})();