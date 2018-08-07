(function() {
    angular.module("app.admin.course.details")
        .controller("AdminCourseDetails", function($window, $scope, $rootScope, $routeParams, CourseSvc) {
            if ($rootScope.curLogin == null || $rootScope.curLogin.userName === undefined || $rootScope.curLogin.userRoles[0].type != 'ADMIN') {
                $window.location.href = "#/home";
            }

            $scope.current = {};
            $scope.addChapter = function(name, subchap) {
                $scope.current.chapter.push(name);
            }

            if ($routeParams.id !== undefined) {
                getByCourseId($routeParams.id);
            }

            function getByCourseId(id) {
                CourseSvc.findByCourseId(id)
                    .then(function(response) {
                        $scope.current = response;
                    }, function(err) {
                        console.log("Error: " + err);
                    });
            }
        })
        .directive("btnAddChapterItem", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).click(function() {
                        $(elem).parent().parent().children().eq(0)
                            .children().eq(1).children().eq(0)
                            .append('<div>' +
                                '<input name="chapter-item" class="chapter-item input-text list-group-item list-group-item-action mr-pd-input-chapter">' +
                                '<i delete-chapter-item class="fas fa-times"></i>' +
                                '</div>');
                    });
                }
            }
        })
        .directive("deleteChapterItem", function() {
            return {
                restrict: "A",
                link: function(scope, elem, attrs) {
                    $(elem).css('right', '10');

                    $(elem).on('click', function() {
                        $(elem).parent().remove();
                    });
                }
            }
        });
})();