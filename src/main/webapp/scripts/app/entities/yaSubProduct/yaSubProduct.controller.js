'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaSubProductController', function ($scope, $state, YaSubProduct) {

        $scope.yaSubProducts = [];
        $scope.loadAll = function() {
            YaSubProduct.query(function(result) {
               $scope.yaSubProducts = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaSubProduct = {
                serialProductId: null,
                capacity: null,
                price: null,
                id: null
            };
        };
    });
