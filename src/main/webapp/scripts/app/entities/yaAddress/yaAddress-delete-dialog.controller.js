'use strict';

angular.module('jhipsterLiquibaseBugApp')
	.controller('YaAddressDeleteController', function($scope, $uibModalInstance, entity, YaAddress) {

        $scope.yaAddress = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            YaAddress.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
