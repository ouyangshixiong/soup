'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaAddressController', function ($scope, $state, YaAddress) {

        $scope.yaAddresss = [];
        $scope.loadAll = function() {
            YaAddress.query(function(result) {
               $scope.yaAddresss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaAddress = {
                receiverName: null,
                receiverMobile: null,
                receiverDistrict: null,
                receiverBuilding: null,
                receiverFloor: null,
                receiverCompany: null,
                isDefault: false,
                id: null
            };
        };
    });
