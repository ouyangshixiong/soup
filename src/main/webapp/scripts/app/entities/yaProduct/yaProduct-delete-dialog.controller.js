'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaProductDeleteController', function($scope, $uibModalInstance, entity, YaProduct) {

        $scope.yaProduct = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaProduct.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
