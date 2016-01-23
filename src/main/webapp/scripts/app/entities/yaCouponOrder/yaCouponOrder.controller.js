'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaCouponOrderController', function ($scope, $state, YaCouponOrder) {

        $scope.yaCouponOrders = [];
        $scope.loadAll = function() {
            YaCouponOrder.query(function(result) {
               $scope.yaCouponOrders = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaCouponOrder = {
                couponOrderId: null,
                couponOrderDate: null,
                couponExpireDate: null,
                couponNeedPay: false,
                couponOrderPrice: null,
                couponOrderStatus: null,
                orderStatusChangeTime: null,
                parentcouponOrderId: null,
                isParentCouponOrder: false,
                id: null
            };
        };
    });
