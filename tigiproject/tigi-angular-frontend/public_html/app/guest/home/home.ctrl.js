(function() {
    angular.module("app.home")
        .controller("Home", function($scope, CourseSvc) {
            $scope.javaavacourses = [];

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.javaavacourses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });

                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.csharpavacourses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });
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
        });
})();