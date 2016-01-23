'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaQuestionController', function ($scope, $state, YaQuestion) {

        $scope.yaQuestions = [];
        $scope.loadAll = function() {
            YaQuestion.query(function(result) {
               $scope.yaQuestions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaQuestion = {
                yaQ: null,
                yaA: null,
                priority: null,
                status: null,
                id: null
            };
        };
    });
