(function() {
    angular.module("app.course.details")
        .controller("CourseDetails", function($scope, $rootScope, $cookieStore, $routeParams, CourseSvc) {
            $scope.current = null;
            $scope.relatedcourses = [];
            $scope.isSubcribed = false;
            $scope.introVideo = null;
            $scope.introVideoLoaded = false;
            $scope.currentReview = [];
            $scope.allresources = [];
            $scope.rate = 0;
            $scope.norate = 0;
            $scope.ratefull = [];

            if ($routeParams.id !== undefined) {
                $scope.introVideoLoaded = false;
                getByCourseId($routeParams.id);
            }

            $scope.$on('$viewContentLoaded', function() {
                var initInterval = setInterval(function() {
                    if ($rootScope.curLogin != null) {
                        checkSubcribed($routeParams.id);
                        clearInterval(initInterval);
                    }
                }, 100);
            });

            $scope.watchIntro = function() {
                $scope.introVideoLoaded = true;

                CourseSvc.getIntroVideo($scope.current.courseId, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.introVideo = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }

            function getByCourseId(id) {
                CourseSvc.findByCourseId(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                    .then(function(response) {
                        $scope.current = response;
                        loadRate($scope.current.courseId);
                        loadReview($scope.current.courseId);
                        var init = setInterval(() => {
                            if ($rootScope.curLogin != null) {
                                CourseSvc.getAllResources(id, $cookieStore.get('curUser'), $cookieStore.get('curPass'))
                                    .then(function(response) {
                                        $scope.allresources = response;
                                        loadInstructor(id);
                                    }, function(err) {
                                        console.log("Error: " + err);
                                    });
                                clearInterval(init);
                            }
                        }, 100);
                        getAllRelatedCourse();
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }

            function getAllRelatedCourse() {
                CourseSvc.findAllCourse()
                    .then(function(response) {
                        $scope.relatedcourses = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }

            function checkSubcribed(curId) {
                $scope.isSubcribed = false;

                if ($rootScope.curLogin != null) {
                    $rootScope.curLogin.userCourseOwners.forEach(function(i) {
                        if (i.id == curId) {
                            if (i.owerType == 'BUY') {
                                $scope.isSubcribed = true;
                            }
                        }
                    })
                }
            }

            function loadRate(id) {
                CourseSvc.getAllRate(id)
                    .then(function(response) {
                        $scope.rate = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });

                CourseSvc.getRateFull(id)
                    .then(function(response) {
                        var bar5 = response.userRate4_5 + response.userRate5;
                        var bar4 = response.userRate3_5 + response.userRate4;
                        var bar3 = response.userRate2_5 + response.userRate3;
                        var bar2 = response.userRate1_5 + response.userRate2;
                        var bar1 = response.userRate0 + response.userRate0_5 + response.userRate1;

                        $scope.norate = bar5 + bar4 + bar3 + bar2 + bar1;

                        var bar5per = ((response.userRate4_5 + response.userRate5) / $scope.norate) * 100;
                        var bar4per = ((response.userRate3_5 + response.userRate4) / $scope.norate) * 100;
                        var bar3per = ((response.userRate2_5 + response.userRate3) / $scope.norate) * 100;
                        var bar2per = ((response.userRate1_5 + response.userRate2) / $scope.norate) * 100;
                        var bar1per = ((response.userRate0 + response.userRate0_5 + response.userRate1) / $scope.norate) * 100;

                        $scope.ratefull.push([
                            bar1,
                            bar1per
                        ]);
                        $scope.ratefull.push([
                            bar2,
                            bar2per
                        ]);
                        $scope.ratefull.push([
                            bar3,
                            bar3per
                        ]);
                        $scope.ratefull.push([
                            bar4,
                            bar4per
                        ]);
                        $scope.ratefull.push([
                            bar5,
                            bar5per
                        ]);
                    }, function(err) {
                        console.log("Error: " + err);
                    })
            }

            function loadReview(id) {
                CourseSvc.getAllReview(id)
                    .then(function(response) {
                        $scope.currentReview = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    })
            }

            $scope.getUserFeedbackInfo = function(rate) {

            }

            $scope.getPersonalRate = function(rate) {
                var cnt = 0;
                // var htmlrate = '';

                // for (let i = 0; i < parseInt(rate); i++) {
                //     htmlrate += '<span class="fa fa-star checked"></span>';
                //     cnt++;
                // }

                // for (let i = 0; i < (5 - cnt); i++) {
                //     htmlrate += '<span class="fa fa-star"></span>';
                // }

                var personalRate = [];

                for (let i = 0; i < parseInt(rate); i++) {
                    personalRate.push('checked');
                    cnt++;
                }

                for (let i = 0; i < (5 - cnt); i++) {
                    personalRate.push('');
                }

                return personalRate;
            }

            function loadInstructor(id) {
                angular.element('#ins-in-course-details').empty();
                angular.element('#ins-in-course-details').append('Instructor: <span ng-class="' + id + '" current-instructor-in-course-details></span>');
            }
        })
        .directive("toggleChapter", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).click(function() {
                        $(elem).siblings().slideToggle();
                    });
                }
            }
        })
        .directive("rateBar", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    var str = attrs.ngClass;
                    var len = str.length
                    var ratestr = attrs.ngClass.substring(1, (len - 1));
                    var rate = ratestr.split(',');
                    $(elem).empty();
                    $(elem).append('<div class="bar-5" style="width: ' + rate[1] + '%"></div>');
                }
            }
        })
        .directive("currentInstructorInCourseDetails", ['InstructorSvc', function(InstructorSvc) {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {

                }
            }
        }])
        // .directive("courseContent", ['$cookies', '$cookieStore', 'CourseSvc', function($cookies, $cookieStore, CourseSvc) {
        //     return {
        //         restrict: "A",
        //         link: function(scope, elem, attrs) {
        //         }
        //     }
        // }]);
})();