'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaBuildingController', function ($scope, $state, YaBuilding) {

        $scope.yaBuildings = [];
        $scope.loadAll = function() {
            YaBuilding.query(function(result) {
               $scope.yaBuildings = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaBuilding = {
                buildingCode: null,
                buildingName: null,
                id: null
            };
        };
    });
