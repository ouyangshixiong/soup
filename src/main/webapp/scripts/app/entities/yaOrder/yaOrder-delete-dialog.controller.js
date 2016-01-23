'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaOrderDeleteController', function($scope, $uibModalInstance, entity, YaOrder) {

        $scope.yaOrder = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
