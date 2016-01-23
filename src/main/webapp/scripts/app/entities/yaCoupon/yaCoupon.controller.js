'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaCouponController', function ($scope, $state, YaCoupon) {

        $scope.yaCoupons = [];
        $scope.loadAll = function() {
            YaCoupon.query(function(result) {
               $scope.yaCoupons = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.yaCoupon = {
                couponName: null,
                couponType: null,
                couponPictures: null,
                price: null,
                keywords: null,
                couponDiscription: null,
                couponAmount: null,
                couponInventory: null,
                onShelveDate: null,
                offShelveDate: null,
                couponStatus: null,
                id: null
            };
        };
    });
