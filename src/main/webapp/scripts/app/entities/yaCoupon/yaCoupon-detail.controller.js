'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaCouponDetailController', function ($scope, $rootScope, $stateParams, entity, YaCoupon, YaCouponOrder, YaBanner) {
        $scope.yaCoupon = entity;
        $scope.load = function (id) {
            YaCoupon.get({id: id}, function(result) {
                $scope.yaCoupon = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaCouponUpdate', function(event, result) {
            $scope.yaCoupon = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
