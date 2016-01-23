'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaOrderSequenceDetailController', function ($scope, $rootScope, $stateParams, entity, YaOrderSequence) {
        $scope.yaOrderSequence = entity;
        $scope.load = function (id) {
            YaOrderSequence.get({id: id}, function(result) {
                $scope.yaOrderSequence = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaOrderSequenceUpdate', function(event, result) {
            $scope.yaOrderSequence = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
