'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaDistrictController', function ($scope, $state, YaDistrict) {

        $scope.yaDistricts = [];
        $scope.loadAll = function() {
            YaDistrict.query(function(result) {
               $scope.yaDistricts = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaDistrict = {
                districtCode: null,
                districtName: null,
                id: null
            };
        };
    });
