'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaCouponOrderDeleteController', function($scope, $uibModalInstance, entity, YaCouponOrder) {

        $scope.yaCouponOrder = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaCouponOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
