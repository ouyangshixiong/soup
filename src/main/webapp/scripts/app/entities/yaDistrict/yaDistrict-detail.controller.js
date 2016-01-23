'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaDistrictDetailController', function ($scope, $rootScope, $stateParams, entity, YaDistrict, YaBuilding) {
        $scope.yaDistrict = entity;
        $scope.load = function (id) {
            YaDistrict.get({id: id}, function(result) {
                $scope.yaDistrict = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaDistrictUpdate', function(event, result) {
            $scope.yaDistrict = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
