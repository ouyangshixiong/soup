'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaSubProductDeleteController', function($scope, $uibModalInstance, entity, YaSubProduct) {

        $scope.yaSubProduct = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaSubProduct.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
