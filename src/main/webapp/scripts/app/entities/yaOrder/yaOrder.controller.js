'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaOrderController', function ($scope, $state, YaOrder) {

        $scope.yaOrders = [];
        $scope.loadAll = function() {
            YaOrder.query(function(result) {
               $scope.yaOrders = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaOrder = {
                orderId: null,
                useCoupon: null,
                orderDate: null,
                productAmount: null,
                orderTotalPrice: null,
                orderStatus: null,
                orderStatusChangeTime: null,
                deliveryDate: null,
                deliveryPeriod: null,
                deliveryManName: null,
                deliveryPhone: null,
                potReturnMethod: null,
                hasPrivatePot: null,
                usePrivatePot: null,
                parentOrderId: null,
                isParentOrder: false,
                orderRealPrice: null,
                id: null
            };
        };
    });
