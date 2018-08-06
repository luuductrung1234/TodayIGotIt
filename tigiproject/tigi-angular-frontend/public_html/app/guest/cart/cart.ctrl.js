(function() {
    angular.module("app.cart")
        .controller("Cart", function($window, $scope, $rootScope, $cookies, $cookieStore, CartSvc) {
            $scope.cartcourses = [];

            if ($rootScope.curLogin === null || $rootScope.curLogin.userName === undefined) {
                $window.location.href = "#/home";
            }

            $scope.getCartDetails = function() {
                return $rootScope.curLogin.userCart.cartDetails;
            }

            $scope.buyAllCart = function() {
                CartSvc.checkoutOrder($cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {

                    }, function(err) {

                    });
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