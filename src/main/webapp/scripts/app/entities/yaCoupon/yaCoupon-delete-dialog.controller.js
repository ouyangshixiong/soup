'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaCouponDeleteController', function($scope, $uibModalInstance, entity, YaCoupon) {

        $scope.yaCoupon = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaCoupon.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
