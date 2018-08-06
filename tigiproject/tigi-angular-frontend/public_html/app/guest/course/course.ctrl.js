(function() {
    angular.module("app.course")
        .controller("Course", function($scope, CourseSvc) {
            $scope.courses = [];

            $scope.$on('$viewContentLoaded', function() {
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.courses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            });
        })
        .directive("addToCart", ['CourseSvc', function(CourseSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {
                        if (scope.$root.curLogin == null) {
                            alert();
                            return false;
                        }

                        var curCourse = JSON.parse(attrs.id)
                        console.log(curCourse);

                        var dateCreated = Date.parse(new Date());
                        var lastUpdated = Date.parse(new Date());
                        var version = 0;
                        var quantity = 1;

                        var course = {
                            dateCreated: Date.parse(new Date()),
                            description: curCourse.description,
                            id: curCourse.courseId,
                            imageUrl: curCourse.imageUrl,
                            lastUpdated: Date.parse(new Date()),
                            price: parseFloat(curCourse.price),
                            version: curCourse.courseVersion
                        }

                        var data = {
                            id: scope.$root.getCartCount() + 1,
                            course,
                            dateCreated,
                            lastUpdated,
                            quantity,
                            version
                        }

                        scope.$root.curLogin.userCart.cartDetails.push(data);
                        scope.$apply();
                        return false;
                    })
                }
            }
        }]);
})();