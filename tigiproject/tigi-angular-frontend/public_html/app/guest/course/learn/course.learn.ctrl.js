(function() {
    angular.module("app.course.learn")
        .controller("CourseLearn", function($window, $scope, $rootScope, $cookies, $cookieStore, $routeParams, CourseSvc) {
            $scope.current = null;
            $scope.resources = [];
            $scope.sourceVideo = null;

            if ($cookieStore.get('curUser') == undefined ||
                $rootScope.curLogin == null ||
                $rootScope.curLogin.userName === undefined) {
                $window.location.href = "#/home";
            } else {
                if ($rootScope.curLogin.userRoles[0].type != 'STUDENT') {
                    $window.location.href = "#/home";
                }
            }

            if ($routeParams.id !== undefined) {
                getByCourseId($routeParams.id);
            }

            $scope.$on('$viewContentLoaded', function() {
                $rootScope.learning = true;
                // $scope.$apply();
            });

            $scope.outCourse = function() {
                $rootScope.learning = false;
                // $scope.$apply();
                $window.location.href = "#/course/" + $scope.current.courseId;
            }

            function getByCourseId(id) {
                CourseSvc.findByCourseId(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.current = response;
                        getResources(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'));
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }

            function getResources(id, username, password) {
                CourseSvc.getAllResources(id, username, password)
                    .then(function(response) {
                        $scope.resources = response;
                        CourseSvc.getResourceVideo($scope.resources[0].courseResources[0].id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                            .then(function(response) {
                                $scope.sourceVideo = response;
                                angular.element('video').empty();
                                angular.element('video').append('<source src="' + $scope.sourceVideo + '" type="video/mp4" />');
                                angular.element('#btn-init-video').click();
                            }, function(err) {
                                console.log("Error: " + err);
                            });
                    }, function(err) {
                        console.log("Error: " + err);
                    })
            }
        })
        .directive("navigateChapter", ['$cookies', '$cookieStore', 'CourseSvc', function($cookies, $cookieStore, CourseSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).on('click', function() {
                        var subchap = JSON.parse(attrs.ngClass);

                        if (subchap.resourceType == 'VIDEO') {
                            CourseSvc.getResourceVideo(subchap.id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    scope.$parent.$parent.sourceVideo = response;
                                    angular.element('video').empty();
                                    angular.element('video').append('<source src="' + scope.$parent.$parent.sourceVideo + '" type="video/mp4" />');
                                    angular.element('#btn-init-video').click();
                                }, function(err) {
                                    console.log("Error: " + err);
                                });
                        } else {
                            CourseSvc.getResourceFile(subchap.id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                .then(function(response) {
                                    var parser = new DOMParser();
                                    var doc = parser.parseFromString(response, 'text/html');
                                    angular.element('#main-file').empty();
                                    angular.element('#main-file').append(doc.firstChild);
                                }, function(err) {
                                    console.log("Error: " + err);
                                });
                        }

                        return false;
                    })
                }
            }
        }])
        .filter('trusted', ['$sce', function($sce) {
            return function(url) {
                return $sce.trustAsResourceUrl(url);
            };
        }]);
})();