'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaProductController', function ($scope, $state, YaProduct) {

        $scope.yaProducts = [];
        $scope.loadAll = function() {
            YaProduct.query(function(result) {
               $scope.yaProducts = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaProduct = {
                productName: null,
                productPictures: null,
                orginalPrice: null,
                discountPrice: null,
                membershipPrice: null,
                isSerial: null,
                keywords: null,
                productDiscription: null,
                productAmount: null,
                productInventory: null,
                onShelveDate: null,
                offShelveDate: null,
                productStatus: null,
                id: null
            };
        };
    });
