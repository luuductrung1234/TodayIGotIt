(function() {
    angular.module("app.search")
        .controller("Search", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams, SearchSvc) {
            $scope.startSearchCourseFrom = 0;
            $scope.startSearchInstructorFrom = 0;
            $scope.courseList = [];
            $scope.instructorList = [];
            $scope.filter = '';

            if ($routeParams.filter !== undefined) {
                $scope.filter = $routeParams.filter;
                searchAction($routeParams.filter);
            }

            // $rootScope.$on('SearchAction', function(event, filter) {
            //     searchAction(filter);
            // })

            $scope.filterPrice = function(field, minValue, maxValue) {
                if (minValue === undefined || minValue === null) minValue = Number.MIN_VALUE;
                if (maxValue === undefined || maxValue === null) maxValue = Number.MAX_VALUE;

                return function predicateFunc(item) {
                    return minValue <= item[field] && item[field] <= maxValue;
                };
            };

            $scope.$on('$viewContentLoaded', function() {

            });

            function searchAction(filter) {
                angular.element("#default").click();
                SearchSvc.searchSth(filter)
                    .then(function(response) {
                        $scope.courseList = response.courseList;
                        $scope.instructorList = response.instructorList;
                        angular.element("#default").click();
                    }, function(error) {
                        console.log("Error: " + error);
                    }, function(progress) {
                        console.log("Running");
                        console.log(progress);
                    })
            }
        });
})();