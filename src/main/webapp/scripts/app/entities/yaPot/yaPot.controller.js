'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaPotController', function ($scope, $state, YaPot) {

        $scope.yaPots = [];
        $scope.loadAll = function() {
            YaPot.query(function(result) {
               $scope.yaPots = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaPot = {
                isPrivatePot: false,
                potQrcode: null,
                potLabel: null,
                potStatus: null,
                potColor: null,
                potCapacity: null,
                potBuyDate: null,
                id: null
            };
        };
    });
