'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaCouponOrderDetailController', function ($scope, $rootScope, $stateParams, entity, YaCouponOrder, YaUser, YaCoupon) {
        $scope.yaCouponOrder = entity;
        $scope.load = function (id) {
            YaCouponOrder.get({id: id}, function(result) {
                $scope.yaCouponOrder = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaCouponOrderUpdate', function(event, result) {
            $scope.yaCouponOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
