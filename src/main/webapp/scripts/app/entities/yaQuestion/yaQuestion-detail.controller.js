'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaQuestionDetailController', function ($scope, $rootScope, $stateParams, entity, YaQuestion) {
        $scope.yaQuestion = entity;
        $scope.load = function (id) {
            YaQuestion.get({id: id}, function(result) {
                $scope.yaQuestion = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaQuestionUpdate', function(event, result) {
            $scope.yaQuestion = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
