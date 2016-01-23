'use strict';

angular.module('jhipsterLiquibaseBugApp')
    .controller('YaOrderDetailController', function ($scope, $rootScope, $stateParams, entity, YaOrder, YaUser, YaAddress, YaPot, YaCouponOrder, YaProduct) {
        $scope.yaOrder = entity;
        $scope.load = function (id) {
            YaOrder.get({id: id}, function(result) {
                $scope.yaOrder = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterLiquibaseBugApp:yaOrderUpdate', function(event, result) {
            $scope.yaOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
