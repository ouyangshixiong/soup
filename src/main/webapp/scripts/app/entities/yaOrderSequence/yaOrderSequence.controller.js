'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaOrderSequenceController', function ($scope, $state, YaOrderSequence) {

        $scope.yaOrderSequences = [];
        $scope.loadAll = function() {
            YaOrderSequence.query(function(result) {
               $scope.yaOrderSequences = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaOrderSequence = {
                id: null
            };
        };
    });
