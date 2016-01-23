'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaPotDetailController', function ($scope, $rootScope, $stateParams, entity, YaPot, YaUser, YaOrder) {
        $scope.yaPot = entity;
        $scope.load = function (id) {
            YaPot.get({id: id}, function(result) {
                $scope.yaPot = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaPotUpdate', function(event, result) {
            $scope.yaPot = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
