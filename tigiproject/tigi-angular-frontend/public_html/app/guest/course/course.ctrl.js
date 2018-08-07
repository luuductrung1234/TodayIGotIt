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
        .directive("addToCart", ['$cookies', '$cookieStore', 'CourseSvc', 'CartSvc', function($cookies, $cookieStore, CourseSvc, CartSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {
                        if (scope.$root.curLogin == null) {
                            alert();
                            return false;
                        }

                        CartSvc.addToCart(attrs.id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            .then(function(response) {
                                console.log(response);
                                scope.$root.$emit('GetUserInfo', $cookieStore.get('curUser'), $cookieStore.get('curPass'));
                            }, function(err) {
                                console.log("Error: " + err);
                            });

                        scope.$apply();

                        return false;
                    })
                }
            }
        }]);
})();