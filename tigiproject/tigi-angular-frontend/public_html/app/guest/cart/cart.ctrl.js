(function() {
    angular.module("app.cart")
        .controller("Cart", function($window, $scope, $rootScope) {
            $scope.cartcourses = [];

            if ($rootScope.curLogin === null || $rootScope.curLogin.userName === undefined) {
                $window.location.href = "#/home";
            }

            $scope.getCartDetails = function() {
                return $rootScope.curLogin.userCart.cartDetails;
            }
        })
        .directive("moreCourseInfo", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {

                }
            }
        })
        .directive("deleteCourse", function() {
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