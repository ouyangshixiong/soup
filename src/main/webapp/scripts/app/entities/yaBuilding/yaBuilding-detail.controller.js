'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaBuildingDetailController', function ($scope, $rootScope, $stateParams, entity, YaBuilding, YaDistrict) {
        $scope.yaBuilding = entity;
        $scope.load = function (id) {
            YaBuilding.get({id: id}, function(result) {
                $scope.yaBuilding = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaBuildingUpdate', function(event, result) {
            $scope.yaBuilding = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
